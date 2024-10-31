package com.seokho.crash.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret-key}") String key){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    //jwt 인증에서 사용할 메서드
    public String generateAccessToken(UserDetails userDetails){
        return generateToken(userDetails.getUsername());
    }

    //생성된 AccessToken으로부터 username을 추출하는 메서드
    public String getUsername(String accessToken){
        return getSubject(accessToken);
    }

    //jwt 생성 메서드
    private String generateToken(String subject){
        var now = new Date();
        var exp = new Date(now.getTime() + 1000 * 60 * 60 * 3); //1000->1초 60->1분 60->1시간 이니깐 3은 3시간
        return Jwts.builder().subject(subject).signWith(key)
                .issuedAt(now)
                .expiration(exp)
                .compact();
    }
    //subject값을 추출하는 메서드
    private String getSubject(String token){

        try {
            return Jwts.parser()
                    .verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        }catch (JwtException exception){
            logger.error("JwtException", exception);
            throw exception;
        }
    }
}
