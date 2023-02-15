package com.admin.admin_back.utils;

import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.exception.UnAuthorizedException;
import com.admin.admin_back.pojo.vo.UserRoleVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USER_NO = "userNo";
    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_ROLES = "roles";
    private static final String SECRET = "secret";
    private static final String ISSUER = "sdu-meeting";
    private static final int EXPIRATION = 604800;

    public String getUserNoFromToken(String token) {
        String userNo;
        try {
            final Claims claims = getClaimsFromToken(token);
            userNo = claims.get(CLAIM_KEY_USER_NO, String.class);
        } catch (Exception e) {
            userNo = null;
        }
        return userNo;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.get(CLAIM_KEY_USERNAME, String.class);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public List<?> getUserRoleFromToken(String token) {
        List<?> userRoleVos;
        try {
            final Claims claims = getClaimsFromToken(token);
            userRoleVos = claims.get(CLAIM_KEY_ROLES, List.class);
        } catch (Exception exception) {
            userRoleVos = null;
        }
        return userRoleVos;
    }

    private Claims getClaimsFromToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new UnAuthorizedException();
        }
        Claims claims;
        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDto user, List<UserRoleVo> roles) {
        Map<String, Object> claims = new HashMap<String, Object>(16) {{
            put(CLAIM_KEY_USER_NO, user.getUserNo());
            put(CLAIM_KEY_USERNAME, user.getUsername());
            put(CLAIM_KEY_ROLES, roles);
        }};
        return generateToken(claims);
    }

    public String generateToken(Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        final Date created = new Date(now);
        final Date expired = new Date(now + EXPIRATION * 1000L);
        return Jwts.builder()
                // 头部
                .setHeaderParam("type", "JWT")
                .setHeaderParam("alg", "HS256")
                // 载荷，setClaims必须写在前面，避免覆盖标准申明
                .setClaims(claims)
                .setSubject("user")
                .setIssuer(ISSUER)
                .setIssuedAt(created)
                .setExpiration(expired)
                // 签名
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public String refreshToken(String token, Map<String, Object> info) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            info.forEach((key, value) -> claims.put(key, value));
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public boolean validateToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .requireIssuer(ISSUER)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ignored) {
            return false;
        }
        return !isTokenExpired(token);
    }

}
