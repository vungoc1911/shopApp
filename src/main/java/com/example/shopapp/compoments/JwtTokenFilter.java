package com.example.shopapp.compoments;

import com.example.shopapp.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(isByPassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authorization = request.getHeader("Authorization");
            if (StringUtils.isNotBlank(authorization) && authorization.startsWith("Bearer ")) {
                final String token = authorization.substring(7);
                String phoneNumber = jwtTokenUtils.extractPhoneNumber(token);
                if (StringUtils.isNotBlank(phoneNumber) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails user = userDetailsService.loadUserByUsername(phoneNumber); //load dưới db
                    if (jwtTokenUtils.validateToken(token, user)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); //tạo authen
                        authentication.setDetails(new WebAuthenticationDetails(request)); //gán thêm các thuộc tính cho request
                        SecurityContextHolder.getContext().setAuthentication(authentication); //lưu thông tin cho spring biêt
                    }
                }
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

    }

    private boolean isByPassToken(HttpServletRequest request) {
        final List<Pair<String, String>> byPassTokens = Arrays.asList(
                Pair.of("/api/v1/users/register", "POST"),
                Pair.of("/api/v1/users/login", "POST")
        );

        for (Pair<String, String> token : byPassTokens) {
            if (request.getServletPath().contains(token.getFirst())
                    && request.getMethod().contains(token.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
