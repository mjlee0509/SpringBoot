package com.example.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 이 클래스에 정의한 설정정보를 Spring 컨데이너에 등록
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry regisrtty) {
        regisrtty.addInterceptor(new LoginCheckInterceptor()) // 인터셉트로 등록할 클래스
                .order(1) // 해당 인터셉터의 우선순의
                .addPathPatterns("/**") // 인터셉터로 체크할 주소
                .excludePathPatterns("/", "/member/save", "/member/login",
                        "/js/**", "/css/**", "/images/**",
                        "/*.ico", "/favicon/**"); // 인터셉터 검증을 하지 않는 주소 (주로 회원가입 페이지, 정적 정보 등)
    }

}
