package SeAH.savg.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor        //TokenProvider 객체를 주입받기 위함
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";      //HTTP 요청 헤더에서 인증정보 전달하기위함
    public static final String BEARER_PREFIX = "Bearer ";       //JWT 토큰을 HTTP 요청 헤더에서 구분하기 위한 접두사

    private final TokenProvider tokenProvider;                  //JWT 토큰의 생성 및 검증 담당

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Request Header 에서 토큰을 꺼냄
        String jwt = resolveToken(request);

        // validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        TokenStatus.StatusCode tokenStatusCode = tokenProvider.validateToken(jwt);
        if (StringUtils.hasText(jwt)) {
            if (tokenStatusCode == TokenStatus.StatusCode.OK) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ObjectMapper objectMapper = new ObjectMapper();
                TokenStatus tokenStatus = TokenStatus.makeTokenStatus(tokenStatusCode);
                objectMapper.writeValue(response.getWriter(), tokenStatus);
                return;
            }
        }

        filterChain.doFilter(request, response);    // 다음 필터로 제어를 넘김
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);  //Authorization 헤더 값을 가져옴 이값은 bearerToken 변수에 저장
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }


}