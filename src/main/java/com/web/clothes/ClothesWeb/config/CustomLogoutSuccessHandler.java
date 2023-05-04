package com.web.clothes.ClothesWeb.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.util.Set;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        if (authentication != null) {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            
            if (roles.contains("ADMIN")) {
                // Nếu đây là một quản trị viên, điều hướng về trang đăng nhập.
                setDefaultTargetUrl("/user/login");
            } else {
                // Nếu đây là một người dùng bình thường, điều hướng về trang chủ.
                setDefaultTargetUrl("/user/sucess");
            }
        }
        
        super.onLogoutSuccess(request, response, authentication);
    }
}
