package com.flab.quicktogether.common.auth.config.jwt;

import com.flab.quicktogether.member.exception.NotAuthorizedException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtProvider {

    private static final long ACCESS_TOKEN_TIME = 1000 * 60 * 30; // 30분
    private final String secretKey = "secretKey";

    public String createToken(String memberName, Long memberId) {

        Date now = new Date();
        Date accessTokenExpires = new Date(now.getTime() + ACCESS_TOKEN_TIME);

        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(memberName)
                .claim("memberId", String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(accessTokenExpires)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return accessToken;
    }

    public TokenDto getTokenInfo(String authorizationHeader) {
        String token = extractToken(authorizationHeader);

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        TokenDto tokenDto = new TokenDto(claims, claims.getSubject(), claims.get("memberId"));
        return tokenDto;
    }

    public void validateToken(String authorizationHeader) {
        validationAuthorizationHeader(authorizationHeader);
        String token = extractToken(authorizationHeader);

        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.info("getCause={}", e.initCause(e).getMessage());
            throw new NotAuthorizedException();
        }

    }

    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith(JwtTokenType.BEARER)) {
            throw new NotAuthorizedException();
        }
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(JwtTokenType.BEARER.length() + 1);
    }
}
