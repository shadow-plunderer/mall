package org.example.filter;

import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 放行的路径
        if (request.getURI().getPath().startsWith("/api/user/login")) {
            return chain.filter(exchange);
        }

        // 获取Authorization头部
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            String tokenIsNull = "{\"code\": 401, \"message\": \"Token is missing\", \"data\": null}";
            DataBuffer buffer = response.bufferFactory().wrap(tokenIsNull.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }

        // 验证token
        String token = authHeader.substring(7);
        try {
            jwtUtil.validateToken(token);
            return chain.filter(exchange);
        } catch (Exception e) {
            String tokenIsInvalid = "{\"code\": 401, \"message\": \"Invalid token\", \"data\": null}";
            DataBuffer buffer = response.bufferFactory().wrap(tokenIsInvalid.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
    }
}