package org.soneech.filter;

import org.soneech.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouteValidator routeValidator;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(RouteValidator routeValidator, JWTUtil jwtUtil) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                    throw new RuntimeException("Missing Authorization header");
                String jwt = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (jwt != null && jwt.startsWith("Bearer ")) {
                    jwt = jwt.substring(7);

                    try {
                        jwtUtil.validateToken(jwt);
                    } catch (Exception exception) {
                        throw new RuntimeException("Unauthorized access to application!");
                    }
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config { }
}
