package com.example.member.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckInterceptor implements HandlerInterceptor {
    // extends는 클래스; implements는 인터페이스

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //부모 클래스(HandlerInterceptor)의 매개변수는 바꿀 수 없다

        // 1. 사용자가 요청한 주소 확인
        String redirectURI = request.getRequestURI();
        System.out.println("requestURI = " + redirectURI);

        // 2. 세션객체 생성
        HttpSession session = request.getSession();

        // 3. 세션에 저장된 로그인 정보 확인
        if (session.getAttribute("loginEmail") == null) {

            // 4-1. 로그인되지 않았을 경우 로그인 페이지로 보내면서 요청 주소 보내기
            response.sendRedirect("/member/login?redirectURI="+redirectURI); // sendRedirect에서 예외던지기 필요

            // redirect:/주소도 원래는 위에처럼 써야 하는건데, Spring에서는 자체
            return false;
        } else {
            // 4-2. 로그인 상태라면 요청한 페이지로 보내기
            return true;
        }

    }


}
