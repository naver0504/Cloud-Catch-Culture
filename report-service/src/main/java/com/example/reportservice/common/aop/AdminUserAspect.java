package com.example.reportservice.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AdminUserAspect {

    private final String ADMIN_ROLE = "ADMIN";


    @Before("@annotation(com.example.reportservice.common.aop.AdminUser)")
    public void checkAdminUser() {
        // Check if the user is an admin
        if (!isUserAdmin()) {
            throw new IllegalStateException("User is not an admin");
        }
    }

    private boolean isUserAdmin() {
        final String userRole = getUserRole(getHttpServletRequest());
        return userRole.equals(ADMIN_ROLE);
    }

    private String getUserRole(HttpServletRequest request) {
        return request.getHeader("role");
    }

    private HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}

