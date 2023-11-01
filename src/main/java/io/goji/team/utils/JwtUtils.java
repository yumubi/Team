package io.goji.team.utils;

import cn.hutool.core.util.RandomUtil;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.common.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.io.Decoders;

@Slf4j
@Component
public class JwtUtils {

    private static String secretKey;
    private static int EXPIRE_TIME;

    /**
     * 静态变量注入
     * 从配置文件读取jjwt.key属性
     * 注入key，set方法不能是static
     * @param secretKey
     */
    @Value("${app.jwt_secretKey}")
    public void setSecretKey(String secretKey) {
        JwtUtils.secretKey = secretKey;

    }


    @Value("${app.jwt_expireTime}")
    public void setExpireTime(int expireTime) {
        JwtUtils.EXPIRE_TIME = expireTime;
    }

//    private static final int EXPIRE_TIME = 2;

    private static final String KEY_CLAIMS = "key_claims";
    private static final String SUBJECT = "key_subject";

    private JwtUtils(){

    }



    /**
     * 获取自定义密钥
     * @return
     */
    private static byte[] getSecretKey() throws Exception {
        if(StringUtils.isBlank(secretKey)) {
            throw new Exception("jjwt配置的密钥不能为空");
        }
         //因为我们后面解密的时候，会用到这里生成的密钥，所以我们要把这个密钥对象，变成字符串保存下来，以备后面的使用
        //如果只是单纯的转成二进制保存会出现乱码，而丢失密钥的部分数据，所以我们要使用 Base64 对其进行转换
        return Decoders.BASE64.decode(secretKey);
    }




    /**
     * 生成token
     * @return
     * @throws ParseException
     */
    public static String geneToken() throws Exception {
        return geneToken(null);
    }



    public static String geneToken(Map<String, String> map) throws Exception {
        // 这里使用一个 Keys.secretKeyFor(SignatureAlgorithm.HS256) 生成一个密钥
        // Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        LocalDateTime now = LocalDateTime.now(); //使用LocalDateTime代替Date
        LocalDateTime expirationDate = now.plusMinutes(EXPIRE_TIME); //使用plusMinutes方法增加2分钟的过期时间，用于测试


        // log.warn("now = " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now)); //使用DateTimeFormatter格式化日期和时间
        //log.warn("expirationDate = " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(expirationDate));
        Map<String, Object> claims = new HashMap<String, Object>();

//
//        User user = new User();
//        user.setId(1000L);
//        user.setName("张三");
//        user.setAddress("开封");

        if(ObjectUtils.isEmpty(map)) {
            // TODO: 1/11/2023
            claims.put(KEY_CLAIMS, RandomUtil.randomString(6));
        }
        else {
            claims.putAll(map);
        }

        String token = Jwts.builder()
            .setClaims(claims)//必须放最前面，不然后面设置的东西都会没有：如setExpiration会没有时间
            .setId(UUID.randomUUID().toString())
            .setSubject(SUBJECT) // 主题  可以是JSON数据
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) //使用Date.from方法将LocalDateTime转换为Date
            .setExpiration(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()))//过期时间
            //.signWith(SignatureAlgorithm.HS256, getSecretKey())
            .signWith(Keys.hmacShaKeyFor(getSecretKey()))
            .compact();

        //log.warn("token===" + token);
        return token;
    }



    /**
     * 解析token，并返回User对象
     * @param token
     * @return
     * @throws ParseException
     */
    public static Claims getToken(String token) throws Exception {

        String msg = null;

        try {
//            Jws<Claims> jws = Jwts.parser()
//                    .setSigningKey(getSecretKey())
//                    .requireSubject(SUBJECT)//校验必须有这个属性，可以省略这步
//                    .parseClaimsJws(token);

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(getSecretKey()))
                    .requireSubject(SUBJECT)//校验必须有这个属性，可以省略这步
                    .build().parseClaimsJws(token);

            return claimsJws.getBody();//Claims是一个Map


        }catch (SignatureException se) {
            msg = "密钥错误";
            log.error(msg, se);
            throw new Exception(msg);

        }catch (MalformedJwtException me) {
            msg = "密钥算法或者密钥转换错误";
            log.error(msg, me);
            throw new Exception(msg);

        }catch (MissingClaimException mce) {
            msg = "密钥缺少校验数据";
            log.error(msg, mce);
            throw new Exception(msg);

        }catch (ExpiredJwtException mce) {
            msg = "密钥已过期";
            log.error(msg, mce);
            throw new Exception(msg);

        }
    }



    /**
     * @属性 检验token合法性
     */
    public static boolean checkToken(String token){
        boolean flag = false;
        try{
            getToken(token);
            flag=true;
        }catch (Exception e){
            flag=false;
        }finally {
            return flag;
        }
    }
    /**
     * @属性 通过token获取用户名

     */
    public static String getNameByToken(String token) throws Exception {
        Claims claims = getToken(token);
        return claims.get("username").toString();
    }

    /**
     * @属性 通过token获取用户id
     */
    public static String getIdByToken(String token) throws Exception {
        Claims claims = getToken(token);
        return claims.get("userid").toString();
    }



}
