package com.skydev.product_inventory_management.configuration.security;

import com.skydev.product_inventory_management.presentation.advice.CustomAccessDeniedHandler;
import com.skydev.product_inventory_management.presentation.advice.CustomAuthenticationEntryPointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.skydev.product_inventory_management.service.implementation.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity  //Habilita la configuracion de seguridad
//@EnableMethodSecurity //Habilita las annotaciones para metodos como @Secured @PreAuthorize
public class SecurityConfig{

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_MANAGER = "MANAGER";
    private static final String ROLE_USER = "USER";

    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAuthenticationEntryPointHandler = customAuthenticationEntryPointHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                     // PUBLIC
                    .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                    // PROTECTED
                    .requestMatchers(HttpMethod.GET, "/api/user").hasRole(ROLE_ADMIN)
                    .requestMatchers(HttpMethod.PUT, "/api/user/{idUser}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    .requestMatchers(HttpMethod.PUT, "/api/user/{idUser}/active").hasAnyRole(ROLE_ADMIN)
                    .requestMatchers(HttpMethod.PUT, "/api/user/{idUser}/password").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    // REMAINING
                    .anyRequest().denyAll())
                .httpBasic(httpB -> httpB
                    .authenticationEntryPoint(customAuthenticationEntryPointHandler)) //Validation for AUTH BASIC
                .exceptionHandling(exception -> exception
                    .accessDeniedHandler(customAccessDeniedHandler)) //Authorization for All
                .build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authProvider(UserDetailsServiceImpl userDetailsService){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return daoProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}