package com.example.springsecurityjwt.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.springsecurityjwt.domain.dto.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.KeyGenerator;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmConstraints;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * JWT 工具类
 *
 * @author LiKe
 * @version 1.0.0
 * @date 2020-04-21 16:11
 */
public final class JWTUtils {

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "5ae95dd3c5f811b9b819434910c52820ae7cfb3d9f7961e7117b24a7012873767d79f61f81fc2e06ebb6fd4f09ab47764d6e20607f843c22a0a2a6a6ed829680";

    /**
     * 签发人
     */
    private static final String ISSUER = "caplike";

    // /**
    //  * 默认过期时间 3600s
    //  */
    // private static final long EXPIRATION = 3600L;

    // /**
    //  * 选择了记住我之后的过期时间 3600s * 7
    //  */
    // private static final long EXPIRATION_REMEMBER = 3600L * 7;

    private JWTUtils() {
    }

    /**
     * Description: 创建 Json Web Token
     *
     * @param username    {String} 用户名
     * @param rememberMe  {boolean} 是否记住我
     * @param userDetails {@link CustomUserDetails} 的实现类
     * @return java.lang.String Json Web Token
     * @author LiKe
     * @date 2020-04-21 16:18:10
     */
    public static String create(String username, boolean rememberMe, CustomUserDetails userDetails) {
        JwtBuilder builder = Jwts.builder();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(userDetails));
        builder.setClaims(jsonObject);
        builder.setSubject(username);
        builder.setIssuer(ISSUER);
        builder.setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        byte[] secBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        System.err.println(secBytes.length);
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        builder.signWith(key,SignatureAlgorithm.HS256);
        builder.serializeToJsonWith(map -> JSON.toJSONBytes(map));
        String jwtToken = builder.compact();
        return jwtToken;
    }

    // /**
    //  * Description: 判断 Json Web Token 是否已经过期
    //  *
    //  * @param jwt Json Web Token
    //  * @return boolean
    //  * @author LiKe
    //  * @date 2020-04-22 08:49:32
    //  */
    // public static boolean hasExpired(String jwt) {
    //     return claims(jwt).getExpiration().before(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    // }

    /**
     * Description: 获得 subject
     *
     * @param jwt {String} Json Web Token
     * @return java.lang.String subject
     * @author LiKe
     * @date 2020-04-21 18:09:26
     */
    public static String subject(String jwt) {
        return claims(jwt).getSubject();
    }

    public static CustomUserDetails userDetails(String jwt) {
        Claims claims = claims(jwt);
        String claimsString = JSON.toJSONString(claims);
        JSONObject jsonObject = JSONObject.parseObject(claimsString);

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setName(jsonObject.getString("username"));
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        JSONArray jsonArray = jsonObject.getJSONArray("authorities");
        for (Object o : jsonArray) {
            JSONObject authorityObject = JSONObject.parseObject(o.toString());
            authorities.add(new SimpleGrantedAuthority(authorityObject.getString("authority")));
        }
        customUserDetails.setAuthorities(authorities);
        return customUserDetails;
    }

    private static Claims claims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .deserializeJsonWith(bytes -> JSONObject.parseObject(new String(bytes), new TypeReference<Map<String, Object>>() {
                }))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}

