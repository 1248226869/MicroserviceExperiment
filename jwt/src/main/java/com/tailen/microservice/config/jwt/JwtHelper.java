package com.tailen.microservice.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tailen.microservice.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhao tailen
 * @description jwt生成、解析工具
 * @date 2019-05-06
 */
public class JwtHelper {
    /**
     * token 密钥
     */
    public static String secret = "secret";

    public static String createToken() {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            //头部信息
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");

            Date nowDate = new Date();
            /*2小过期*/
            Date expireDate = DateUtil.getAfterDate(nowDate, 0, 0, 0, 2, 0, 0);

            String token = JWT.create()
                    /*设置头部信息 Header*/
                    .withHeader(map)
                    /*设置 载荷 Payload*/
                    /*签名是有谁生成 例如 服务器*/
                    .withIssuer("SERVICE")
                    /*签名的主题*/
                    .withSubject("this is test token")
                    /*定义在什么时间之前，该jwt都是不可用的.*/
                    //.withNotBefore(new Date())
                    //签名的观众 也可以理解谁接受签名的
                    .withAudience("APP")
                    //生成签名的时间
                    .withIssuedAt(nowDate)
                    //签名过期的时间
                    .withExpiresAt(expireDate)
                    /*签名 Signature */
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String createTokenWithClaim() {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Map<String, Object> map = new HashMap<String, Object>();
            Date nowDate = new Date();
            //2小过期
            Date expireDate = DateUtil.getAfterDate(nowDate, 0, 0, 0, 2, 0, 0);
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            String token = JWT.create()
                    /*设置头部信息 Header*/
                    .withHeader(map)
                    /*设置 载荷 Payload*/
                    /*设置 Claim */
                    .withClaim("loginName", "lijunkui")
                    //签名是有谁生成 例如 服务器
                    .withIssuer("SERVICE")
                    //签名的主题
                    .withSubject("this is test token")
                    //定义在什么时间之前，该jwt都是不可用的.
                    //.withNotBefore(new Date())
                    //签名的观众 也可以理解谁接受签名的
                    .withAudience("APP")
                    //生成签名的时间
                    .withIssuedAt(nowDate)
                    //签名过期的时间
                    .withExpiresAt(expireDate)
                    /*签名 Signature */
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * 解析jwt
     */
    public static Claim verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //Reusable verifier instance
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("SERVICE")
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            String subject = jwt.getSubject();
            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get("loginName");

            System.out.println(claim.asString());
            List<String> audience = jwt.getAudience();
            System.out.println(subject);
            System.out.println(audience.get(0));
            return claim;
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        String createToken = JwtHelper.createToken();
//        System.out.println("1 jwt is "+createToken);
//        String createTokenWithClaim = JwtHelper.createTokenWithClaim();
//        System.out.println("2 jwt is "+createTokenWithClaim);
//        JwtHelper.verifyToken(createTokenWithClaim);
    }
}
