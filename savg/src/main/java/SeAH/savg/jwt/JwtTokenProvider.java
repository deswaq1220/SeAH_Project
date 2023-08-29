package SeAH.savg.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenProvider {

    // jwt 토큰 생성
//    public String createToken(String userPk) {
//        Claims claims = Jwts.claims().setSubject(userPk);
//        Date now = new Date();
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//
//    // jwt refresh 토큰 생성
//    public String createRefreshToken() {
//        Date now = new Date();
//        return Jwts.builder()
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
}
