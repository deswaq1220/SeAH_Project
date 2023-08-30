
package SeAH.savg.jwt;
import SeAH.savg.dto.TokenDto;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;


@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";   //사용자 권한(authorities) 식별하는데 사용
    private static final String BEARER_TYPE = "bearer";     // 토큰유형 지정시 사용 Oauth 2.0 인증 프로토콜에서 사용되는 토큰 유형

//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;       // 30분 액세스토큰
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 500 * 10;       // 30초 액세스토큰

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일 리프레쉬토큰

    private final Key key;  //토큰 생성시 사용할 키


    // @Value는 `springframework.beans.factory.annotation.Value` jwt.secret 프로퍼티 값을 주입받기 위해 사용
    //     * @param secretKey
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);        //BASE64 디코딩 수행시 사용
        this.key = Keys.hmacShaKeyFor(keyBytes);        //키를생성
    }   //생성자를 통해 secretKey 값을 받아와 디코딩하고, 그 결과로 HMAC-SHA키를 생성하여 key 에할당하는 역할을 함 토큰의 암호화 및 복호화에 사용


    public TokenDto generateTokenDto(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()   //사용자의 권한정보 추출
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();          //현재시간 가져옴, 현재 시간을 밀리초 단위로 반환


        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);     // 액세스 토큰 만료시간을 더하여 만료시간 설정

        System.out.println(tokenExpiresIn);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       //메서드로 사용자 이름 설정
                .claim(AUTHORITIES_KEY, authorities)        // 권한정보 설정
                .setExpiration(tokenExpiresIn)              // 토큰의 만료시간 설정
                .signWith(key, SignatureAlgorithm.HS512)    // 키오 서명 알고리즘을 지정하여 토큰 서명
                .compact();                                 // 최종적인 토큰 문자열 생성

        //refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .tokenExpiresIn(tokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }       // 주어진 인증정보 기반으로 액세스 토큰 생성하고 TokenDTO 객체로 변환하여 반환

    // 토큰 복호화
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims == null || claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // validateToken 토큰을 검증하기 위한 메소드
    public TokenStatus.StatusCode validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("유효한 JWT 토큰입니다.");
            return TokenStatus.StatusCode.OK;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");

            return TokenStatus.StatusCode.EXPIRED;
        } catch (UnsupportedJwtException | io.jsonwebtoken.security.SecurityException |
                 MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다");
            return TokenStatus.StatusCode.UNAUTHORIZED;
        } catch (IllegalArgumentException e) {
            log.info("확인용 : " + token);
            log.info("JWT 토큰이 잘못되었습니다.");
            return TokenStatus.StatusCode.UNKNOWN;
        }
    }


    //parseClaim 토큰을 claims형태로 만든 메소드 이를 통해 위에서 권한 정보가 있는지 없는지 체크 가능
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}