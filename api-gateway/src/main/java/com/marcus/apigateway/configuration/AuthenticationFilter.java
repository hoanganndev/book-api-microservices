package com.marcus.apigateway.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcus.apigateway.dto.ApiResponse;
import com.marcus.apigateway.service.IdentityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;
    ObjectMapper objectMapper;
    
    @NonFinal
    private String[] PUBLIC_ENDPOINTS = {
            "/identity/auth/.*",
            "/identity/users/registration"
    };
    
    @Value("${app.api-prefix}")
    @NonFinal
    String appApiPrefix;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("<<< Enter authentication filter >>>");
        
        if (isPublicEndpoint(exchange.getRequest()))
            return chain.filter(exchange);
        
        // get header token
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader))
            return unAuthenticated(exchange.getResponse());
        
        String token = authHeader.getFirst().replace("Bearer ", "");
        log.info("🎯 Token : {}", token);
        
        // verify token
        return identityService.isIntrospect(token).flatMap(res -> {
            if (res.getResult().isValid())
                return chain.filter(exchange);
            else
                return unAuthenticated(exchange.getResponse());
        }).onErrorResume(throwable -> unAuthenticated(exchange.getResponse()));
    }
    
    @Override
    public int getOrder() {
        return -1;
    }
    
    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(PUBLIC_ENDPOINTS).anyMatch(s -> request.getURI().getPath().matches(appApiPrefix + s));
    }
    
    Mono<Void> unAuthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(1401)
                .message("Unauthenticated")
                .build();
        String body;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
    
}
