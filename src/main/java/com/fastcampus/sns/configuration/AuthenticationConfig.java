package com.fastcampus.sns.configuration;

import com.fastcampus.sns.configuration.filter.JwtTokenFilter;
import com.fastcampus.sns.exception.CustomAuthenticationEntryPoint;
import com.fastcampus.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    @Value("${jwt.secret-key}")
    private String key;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // ignoring한게 첫번째 우선순위
        web.ignoring().regexMatchers("^(?!/api/).*") // /api로 시작하는 path들만 통과시키고 아닌 것들은 ignore한다
            .antMatchers(HttpMethod.POST, "/api/*/users/join", "/api/*/users/login"); // * 버전 정보는 어떤 버전이든 상관없이 users/join은 항상 허용하도록 설정한다.

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                                                                                        // .permitAll() 어떤 사용자든지 접근할 수 있다.
                .antMatchers("/api/**").authenticated() // 인증된 사용자만이 접근할 수 있다.
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 사용 x
                .and()
                .addFilterBefore(new JwtTokenFilter(key, userService), UsernamePasswordAuthenticationFilter.class) // filter 설정
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());


                // TODO
                //
                //.authenticationEntryPoint()

    }
}
