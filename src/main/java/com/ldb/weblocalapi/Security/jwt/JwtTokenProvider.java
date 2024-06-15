package com.ldb.weblocalapi.Security.jwt;

import com.ldb.weblocalapi.Security.service.UserPrincipal;
import com.ldb.weblocalapi.repositories.UserRepository;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

/**
 * Create at 2019-01-21
 * @author KHAMPHAI
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}") // 3  minus
    private int jwtExpirationInMs;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
//        System.out.println("Secrete KEY : " + jwtSecret);
    }


    public String generateJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return generateTokenFromUsername(userPrincipal.getUsername());
    }
    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//        System.out.println("USER ID : " + userPrincipal.getUser().getUserId());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs );
//        System.out.println("Date expire =" + expiryDate);
        return Jwts.builder()
//                .setSubject(Long.toString(userPrincipal.getUser().getUserId()))
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJWT(String token) {
//        System.out.println("Token ID : " + token);
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
//        System.out.println("Claims = " + claims + " | " + claims.getSubject());
//        return Long.parseLong(claims.getSubject());
        return claims.getSubject();
    }

    public Long getUserIdFromJWT(String token) {
//        System.out.println("Token ID : " + token);
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
//        System.out.println("Claims = " + claims + " | " + claims.getSubject());
//        return Long.parseLong(claims.getSubject());
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
