package com.Tasker.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.logging.Logger;

/**
 * Configuration class for Spring Security.
 * <p>
 * This class configures security settings, authentication providers, and filters for the application.
 * </p>
 * <p>
 * It disables CSRF, form login, and basic authentication, and sets session management to stateless.
 * </p>
 * <p>
 * It also sets up role-based authorization and adds a JWT authentication filter.
 * </p>
 * <p>
 * Public endpoints include those related to authentication and static resources.
 * </p>
 * <p>
 * Restricted endpoints are secured based on user roles.
 * </p>
 * <p>
 * An instance of BCryptPasswordEncoder is used for password encoding.
 * </p>
 * <p>
 * An instance of DaoAuthenticationProvider is used for authentication.
 * </p>
 * <p>
 * The authentication manager is configured with a provider manager.
 * </p>
 *
 * @author Yons Said
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final Logger logger = Logger.getLogger(SecurityConfig.class.getName());

    /**
     * Constructs a SecurityConfig with the specified user details service and JWT authentication filter.
     *
     * @param userDetailsService the service to load user-specific data for authentication.
     * @param jwtAuthenticationFilter the filter to handle JWT authentication.
     */
    @Autowired
    public SecurityConfig(MyUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    /**
     * Configures the security filter chain.
     *
     * @param httpSecurity the HttpSecurity object used to configure security settings
     * @return the configured DefaultSecurityFilterChain object
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("Configuring security filter chain");

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry -> {
                    authenticationProvider();

                    registry.requestMatchers(
                            "/api/auth/**",
                            "/register",
                            "/",
                            "/img/**",
                            "/vid/**",
                            "/aboutus",
                            "/login",
                            "/favicon.ico",
                            "/error",
                            "/features",
                            "/contact"
                    ).permitAll();

                    registry.requestMatchers(
                            "/admin/**",
                            "/admin/home"
                    ).hasRole("ADMIN");

                    registry.requestMatchers(
                            "/api/users/**",
                            "/api/tasks/**",
                            "/api/categories/**",
                            "/user/home",
                            "/api/taskcategories/**",
                            "/css/**"
                    ).hasAnyRole("USER", "ADMIN");

                    registry.anyRequest().authenticated();
                })
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Bean for UserDetailsService.
     *
     * @return an instance of MyUserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    /**
     * Bean for PasswordEncoder.
     *
     * @return an instance of BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for AuthenticationProvider.
     *
     * @return an instance of DaoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Bean for AuthenticationManager.
     *
     * @param authenticationConfiguration the AuthenticationConfiguration object used to configure the authentication manager
     * @return the configured AuthenticationManager object
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return new ProviderManager(authenticationProvider());
    }
}
