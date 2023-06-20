package com.fastcampus.sns.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/api/*/users/join", "/api/*/users/login").permitAll() // * 버전 정보는 어떤 버전이든 상관없이 users/join은 항상 허용하도록 설정한다.
                                                                                        // .permitAll() 어떤 사용자든지 접근할 수 있다.
                .antMatchers("/api/**").authenticated() // 인증된 사용자만이 접근할 수 있다.
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // session 사용 x

                // TODO
                //.exceptionHandling()
                //.authenticationEntryPoint()

    }
}
