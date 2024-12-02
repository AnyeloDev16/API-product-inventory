package com.skydev.product_inventory_management.configuration.security;

import com.skydev.product_inventory_management.configuration.security.filter.JwtFilter;
import com.skydev.product_inventory_management.presentation.advice.CustomAccessDeniedHandler;
import com.skydev.product_inventory_management.presentation.advice.CustomAuthenticationEntryPointHandler;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity  //Habilita la configuracion de seguridad
//@EnableMethodSecurity //Habilita las annotaciones para metodos como @Secured @PreAuthorize
public class SecurityConfig{

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_MANAGER = "MANAGER";
    private static final String ROLE_USER = "USER";

    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                     // PUBLIC
                    .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/product/user/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/payment/**").permitAll()
                    // PROTECTED
                    //User
                    .requestMatchers(HttpMethod.GET, "/api/user").hasRole(ROLE_ADMIN)
                    .requestMatchers(HttpMethod.PUT, "/api/user/{idUser}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    .requestMatchers(HttpMethod.PUT, "/api/user/{idUser}/active").hasAnyRole(ROLE_ADMIN)
                    .requestMatchers(HttpMethod.PUT, "/api/user/{idUser}/password").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    //Category
                    .requestMatchers(HttpMethod.GET, "/api/category").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    .requestMatchers(HttpMethod.GET, "/api/category/{idCategory}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    .requestMatchers(HttpMethod.POST, "/api/category").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    .requestMatchers(HttpMethod.PUT, "/api/category/{idCategory}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    //Product
                    .requestMatchers(HttpMethod.GET, "/api/product/staff/**").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    .requestMatchers(HttpMethod.POST, "/api/product/staff").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    .requestMatchers(HttpMethod.PUT, "/api/product/staff/**").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    //Address
                    .requestMatchers(HttpMethod.POST, "/api/address").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    .requestMatchers(HttpMethod.PUT, "/api/address/{idAddress}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    .requestMatchers(HttpMethod.GET, "/api/address/**").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    //Order
                    .requestMatchers(HttpMethod.GET, "/api/order/{orderId}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    .requestMatchers(HttpMethod.GET, "/api/order/details/{orderId}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    .requestMatchers(HttpMethod.GET, "/api/order/user/{userId}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    .requestMatchers(HttpMethod.GET, "/api/order").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    .requestMatchers(HttpMethod.PUT, "/api/order/cancel/{orderId}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    .requestMatchers(HttpMethod.PUT, "/api/order/changeStatus/{orderId}").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)
                    .requestMatchers(HttpMethod.POST, "api/order").hasAnyRole(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    // REMAINING
                    .anyRequest().denyAll())
                .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(customAuthenticationEntryPointHandler)
                    .accessDeniedHandler(customAccessDeniedHandler)) //Authorization for All
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
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
