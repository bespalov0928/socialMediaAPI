package com.example.socialmediaapi.jwt.config;

import com.example.socialmediaapi.jwt.model.Permission;
import com.example.socialmediaapi.jwt.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
//                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/user/**").hasAuthority(Permission.ADMIN_WRITE.getPermission())
                                .requestMatchers(HttpMethod.POST, "/api/v1/user/**").hasAnyAuthority(Permission.ADMIN_WRITE.getPermission(), Permission.ADMIN_READ.getPermission())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/user/**").hasAnyAuthority(Permission.ADMIN_WRITE.getPermission(), Permission.ADMIN_READ.getPermission())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/user/**").hasAnyAuthority(Permission.ADMIN_WRITE.getPermission(), Permission.ADMIN_READ.getPermission())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }

}
