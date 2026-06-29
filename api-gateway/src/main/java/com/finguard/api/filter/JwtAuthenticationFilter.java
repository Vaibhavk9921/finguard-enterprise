package com.finguard.api.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.finguard.api.security.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		String path = exchange.getRequest().getURI().getPath();

		if (path.startsWith("/api/auth/")) {
			return chain.filter(exchange);
		}
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {

			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

			return exchange.getResponse().setComplete();
		}

		String token = authHeader.substring(7);
		String role = jwtUtil.extractRole(token);

		if (!jwtUtil.validateToken(token)) {

			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

			return exchange.getResponse().setComplete();
		}
		if (path.contains("/api/loans/") && path.endsWith("/approve") && !"ADMIN".equals(role)) {

			exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);

			return exchange.getResponse().setComplete();
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -1;
	}
}