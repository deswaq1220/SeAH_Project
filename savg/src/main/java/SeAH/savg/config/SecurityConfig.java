package SeAH.savg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()//인증이 필요한 url 설정
                .antMatchers("/edureg").authenticated()
                .anyRequest().permitAll()   //나머지는 다 접속 가능
                .and()
                .formLogin()
                .passwordParameter("seah1234")
                .loginPage("/localhost:3000")
                .defaultSuccessUrl("/manager") //로그인성공하면 이동
                .failureUrl("//localhost:3000") //실패하면
                .and()
                .logout()
                .invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }
}
