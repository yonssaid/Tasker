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

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Autowired
        private MyUserDetailsService userDetailsService;
        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        private static final Logger logger = Logger.getLogger(SecurityConfig.class.getName());

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
                        registry.requestMatchers("/api/auth/**", "/register", "/", "/img/**", "/vid/**", "/aboutus", "/login", "/favicon.ico").permitAll();
                        registry.requestMatchers("/admin/**", "/admin/home").hasRole("ADMIN");
                        registry.requestMatchers("/api/users/**", "/api/tasks/**", "/user/home").hasAnyRole("USER","ADMIN");
                        registry.anyRequest().authenticated();
                    })
                    .logout(logout -> {
                        logout
                                .logoutUrl("/logout")
                                .clearAuthentication(true);
                    })
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return userDetailsService;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return new ProviderManager(authenticationProvider());
        }
    }
