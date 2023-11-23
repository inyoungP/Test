package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //스프링을 구성하기 위한 클래스
@EnableWebSecurity //스프링 시큐리티를 활성화시키기 위한 어노테이션
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.formLogin()
                .loginPage("/members/login") //로그인 페이지로 설정
                .defaultSuccessUrl("/") //로그인에 성공하면 이동할 url
                .usernameParameter("email") //로그인시 사용할 이름
                .failureUrl("/members/login/error") //로그인에 실패했을 때 보여줄 url
                .and()//한번 끊어주는 것 앞에는 로그인 뒤에는 로그아웃
                .logout() //로그아웃처리
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))//로그아웃 url 처리
                .logoutSuccessUrl("/"); //로그아웃 성공하면 이동할 url

        http.authorizeRequests()
                //모든 사용자가 인증없이 사용가능
                .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .mvcMatchers("/","members/**", "/item/**", "images/**").permitAll()
                //admin으로 시작하는 경로는 ADMIN만 가능
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                //나머지는 모두 인증을 요청
                .anyRequest().authenticated();

        //인증받지 않은 사용자가 접근하면 수행
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        //HttpSecurity 객체를 매개변수로 받아서 해당 객체를 사용하여
        // 필터 체인을 구성하고 반환
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        //비밀번호 암호화
        return new BCryptPasswordEncoder();
    }
}
