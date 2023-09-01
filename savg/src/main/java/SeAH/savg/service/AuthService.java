package SeAH.savg.service;

import SeAH.savg.dto.MemberRequestDto;
import SeAH.savg.dto.MemberResponseDto;
import SeAH.savg.dto.TokenDto;
import SeAH.savg.dto.TokenRequestDto;
import SeAH.savg.entity.Member;
import SeAH.savg.entity.RefreshToken;
import SeAH.savg.jwt.TokenProvider;
import SeAH.savg.jwt.TokenStatus;
import SeAH.savg.repository.MemberRepository;
import SeAH.savg.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    public MemberResponseDto signup(MemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    public TokenDto login(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        System.out.println( "몰라 유저네임페스워드 어쩌고 토큰"+authenticationToken);
        System.out.println( "몰라 두번때"+authentication);
        return tokenProvider.generateTokenDto(authentication);
    }

    public TokenDto refresh(TokenRequestDto tokenRequestDto) {

        //Refresh Token 검증
        TokenStatus.StatusCode tokenStatusCode = tokenProvider.validateToken(tokenRequestDto.getRefreshToken());
        if (tokenStatusCode != TokenStatus.StatusCode.OK) {
            throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
        }

        //  Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 새로운 토큰 생성
        TokenDto tokenDTO = tokenProvider.generateTokenDto(authentication);

        //  저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDTO.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDTO;
    }



}