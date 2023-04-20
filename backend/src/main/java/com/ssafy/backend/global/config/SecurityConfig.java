package com.ssafy.backend.global.config;


import com.ssafy.backend.global.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        /*시큐리티 기본 설정.*/
        http
                .cors()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .formLogin().disable() //로그인 폼 사용안하는 rest 방식이므로 제외.
                .httpBasic().disable(); //기본인증 로그인창사용 안하기 때문에 제외.


        /*시큐리티 허용 url*/
        http
                .authorizeRequests()
//                .antMatchers("/auth/**","/oauth2/**", "/token/**").permitAll();
                .antMatchers("/**").permitAll()//테스트를 위해서 모든 경로 열어두기.
                .anyRequest().authenticated();

        /*oauth 인증 후 처리*/
        http
                .oauth2Login()
                .userInfoEndpoint().userService(customOAuth2UserService); //소셜 인증후에 받아온 회원정보를 처리할 서비스 지    .
//            .and()
//                .successHandler() //인증 성공했을때 동작할 핸들러
//                .failureHandler() //인증 실패했을때 동작할 핸들러.
//                .permitAll();
//        /*필터*/
//        http
//                .addFilterBefore(, UsernamePasswordAuthenticationFilter.class); //필터 앞에 필터 등록 - 두번째 인자로 준 필터보다 첫번째 인자로 준 필터가 먼저 실행되도록 함.
//
//        /*예외 등록*/
//        http
//                .exceptionHandling()
//                .authenticationEntryPoint(tokenAuthenticationEntryPoint) //인증실패시
//                .accessDeniedHandler(tokenAccessDeniedHandler); //인가 실패시.



        return http.build();
    }
}
