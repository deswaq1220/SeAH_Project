package SeAH.savg.service;

import SeAH.savg.constant.RoleType;
import SeAH.savg.dto.SignInRequest;
import SeAH.savg.dto.SignInResponse;
import SeAH.savg.entity.Member;
import SeAH.savg.entity.SignUpRequest;
import SeAH.savg.exception.LoginFailureException;
import SeAH.savg.exception.MemberEmailAlreadyExistsException;
import SeAH.savg.exception.MemberNicknameAlreadyExistsException;
import SeAH.savg.exception.RoleNotFoundException;
import SeAH.savg.repository.MemberRepository;
import SeAH.savg.repository.RoleRepository;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.token.TokenService;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public void signUp(SignUpRequest req) {
        validateSignUpInfo(req);
        memberRepository.save(SignUpRequest.toEntity(req,
                roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),
                passwordEncoder));
    }

    public SignInResponse signIn(SignInRequest req) {
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(LoginFailureException::new);
        validatePassword(req, member);
        String subject = createSubject(member);
        String accessToken = tokenService.createAccessToken(subject);
        String refreshToken = tokenService.createRefreshToken(subject);
        return new SignInResponse(accessToken, refreshToken);
    }

    private void validateSignUpInfo(SignUpRequest req) {
        if (memberRepository.existsByEmail(req.getEmail()))
            throw new MemberEmailAlreadyExistsException(req.getEmail());
        if (memberRepository.existsByNickname(req.getNickname()))
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
    }

    private void validatePassword(SignInRequest req, Member member) {
        if (!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }

    private String createSubject(Member member) {
        return String.valueOf(member.getId());
    }

}
