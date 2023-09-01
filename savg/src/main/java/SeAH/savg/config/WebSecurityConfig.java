package SeAH.savg.config;

import SeAH.savg.jwt.JwtAccessDeniedHandler;
import SeAH.savg.jwt.JwtAuthenticationEntryPoint;
import SeAH.savg.jwt.TokenProvider;
import SeAH.savg.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
@Slf4j
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;                              //토큰생성및 검증담당
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;  // 인증되지않은 요청에대한처리 담당
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;            // 인가 되지 않은 요청에 대한 처리

    //request로부터 받은 비밀번호를 암호화하기 위해 PasswordEncoder 빈 생성
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //filterChain(HttpSecurity http)- HttpSecurity를 구성하는 메서드 메서드에서 HTTP 요청에 대한 보안 설정 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("시큐리티 컨피그 : " + http);
        http

                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 없이 REST API를 통해 토큰을 주고받기위해

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
//
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()

                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/auth/**", "/member/**", "/admin/**").permitAll() //인증되지 않은 사용자도 해당경로에 접근을 허용한다
//                .antMatchers("/admin/**").hasAuthority("ADMIN") //ROLE_ADMIN 계정만 admin에 접근 가능함
                .antMatchers(HttpMethod.POST, "/user/**","/refresh").permitAll() // 로그인 엔드포인트에 대한 post 요청은 보호되지 않는다.
                .antMatchers(HttpMethod.GET, "/user/**","/regularcheck","/regularname").permitAll()
                .antMatchers(HttpMethod.DELETE, "/user/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/**").permitAll()
                .antMatchers("/auth/**", "/member/**", "/admin/**", "/auth/refresh").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider))
                .and()
                .cors()
                .configurationSource(corsConfigurationSource());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Allow credentials 설정을 활성화
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // 허용할 도메인 지정
        config.addAllowedMethod("*"); // 모든 메소드 허용.
        config.addAllowedHeader("*");

        //config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 허용할 헤더 추가
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}