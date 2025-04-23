package com.example.shopapp.configurations;

import com.example.shopapp.compoments.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(publicEndpoints()).permitAll()
                            .requestMatchers(adminEndpoints()).hasRole("ADMIN")
                            .requestMatchers(String.format("/api/v1/products/**")).hasRole("USER");
                });
        return http.build();
    }

    private String[] adminEndpoints() {
        return new String[] { "/api/v1/categories"};
    }

    private String[] publicEndpoints() {
        return new String[] {
                "/api/v1/users/login",
                "/api/v1/users/register"};
    }
}
