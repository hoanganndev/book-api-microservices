package com.marcus.apigateway.service;

import com.marcus.apigateway.dto.ApiResponse;
import com.marcus.apigateway.dto.request.IntrospectRequest;
import com.marcus.apigateway.dto.response.IntrospectResponse;
import com.marcus.apigateway.reposirory.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;
    
    public Mono<ApiResponse<IntrospectResponse>> isIntrospect(String token) {
        return identityClient.introspect(
                IntrospectRequest.builder()
                        .token(token)
                        .build()
        );
    }
}
