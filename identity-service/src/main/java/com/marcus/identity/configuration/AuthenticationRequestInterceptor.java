package com.marcus.identity.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authHeader = requestAttributes != null ? requestAttributes.getRequest().getHeader("Authorization") : null;
        log.info("🔥 Header: {}", authHeader);
        if (StringUtils.hasText(authHeader))
            template.header("Authorization", authHeader);
    }
}
