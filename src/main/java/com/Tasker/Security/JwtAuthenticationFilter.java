package com.Tasker.Security;

import com.Tasker.Services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Filter that processes JWT authentication on each request.
 * <p>
 * This filter extracts the JWT token from cookies, validates it, and sets the security context if the token is valid.
 * </p>
 *
 * @author Yons Said
 */
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    private final JWTService jwtService;

    private final MyUserDetailsService myUserDetailsService;

    /**
     * Constructor for JwtAuthenticationFilter.
     *
     * @param jwtService the service to generate and return JWT information.
     * @param myUserDetailsService the user details service class to retrieve UserDetails information.
     */
    @Autowired
    public JwtAuthenticationFilter(JWTService jwtService, MyUserDetailsService myUserDetailsService) {
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
    }

    /**
     * Filters the request to perform JWT authentication.
     *
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param filterChain the FilterChain object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    String jwt = cookie.getValue();
                    String username = jwtService.extractUsername(jwt);

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

                        if (userDetails != null && jwtService.isTokenValid(jwt)) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                            logger.info("Authorities are " + userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            logger.info("Auth token is " + authToken);
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                    break;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
