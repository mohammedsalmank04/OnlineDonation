package com.practice.onlinedonation.security.jwt;

import com.practice.onlinedonation.security.UserDetails.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            /*System.out.println("********** FALSE STATEMENT **********");
            System.out.println("JWT Filter executed for: " + request.getRequestURI());*/
            filterChain.doFilter(request,response);
            return;
        }

        try{

            final String jwt = authHeader.substring(7);
            final String userName = jwtService.extractUserName(jwt);

            //Check if user was already authenticated before
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(userName != null && authentication == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
               // System.out.println("IS TOKEN VALID: " + jwtService.isTokenValid(jwt, userDetails));
                if(jwtService.isTokenValid(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request,response);


           // System.out.println("JWT Filter executed for: " + request.getRequestURI());


        }catch (Exception e){
           // handlerExceptionResolver.resolveException(request,response,null,e);
            //System.out.println("Printing error here");
            e.printStackTrace();
            throw e;
        }


    }
}
