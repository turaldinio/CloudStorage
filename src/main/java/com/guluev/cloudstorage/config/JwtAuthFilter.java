package com.guluev.cloudstorage.config;

import com.guluev.cloudstorage.service.JwtService;
import com.guluev.cloudstorage.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String token = request.getHeader("auth-token") == null ?
                    (request.getHeader("authorization") == null ? null : request.getHeader("authorization"))
                    : request.getHeader("auth-token");

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            token = token.substring("Bearer ".length());

            String userEmail = jwtService.extractUserEmail(token);


            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                var user = userService.findUserByEmail(userEmail);


                if (user.getUserToken().equals(token) &&
                        jwtService.isTokenValid(token, user)) {
                    var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                } else {
                    throw new AuthenticationException("Unauthorized error") {
                        @Override
                        public String getMessage() {
                            return super.getMessage();
                        }
                    };
                }

            }


            filterChain.doFilter(request, response);

        } catch (Exception e) {
            resolver.resolveException(request, response, null, new AuthenticationException("Unauthorized error") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            });
        }
    }
}
