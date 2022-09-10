package com.example.twitterapi.security.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AuthMiddlware extends BasicAuthenticationFilter {
    private  final String prefix = "Bearer ";
    private UserDetailsImpl userDetails;
    private String jwt_secret;

    public AuthMiddlware(AuthenticationManager authenticationManager, UserDetailsImpl userDetails, String jwt_secret){
        super(authenticationManager);
        this.userDetails=userDetails;
        this.jwt_secret=jwt_secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken auth = getAuth(request);
        if(auth == null){
            chain.doFilter(request,response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuth(HttpServletRequest req){
        String token = req.getHeader(HttpHeaders.AUTHORIZATION);
        if(token == null || !token.startsWith(prefix)){
            return null;
        }
        Map payload = JWT.require(Algorithm.HMAC256(jwt_secret))
                .build()
                .verify(token.replace(prefix,""))
                .getClaims();
        if(payload == null){
            return null;
        }

        String email = payload.get("email").toString().substring(1, payload.get("email").toString().length() - 1);
        String uid = payload.get("uid").toString().substring(1, payload.get("uid").toString().length() - 1);



        UserDetails ud = userDetails.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(uid,null,ud.getAuthorities());
    }

}
