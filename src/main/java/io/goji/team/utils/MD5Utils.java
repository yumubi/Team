package io.goji.team.utils;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.util.DigestUtils;


public class MD5Utils {
    public static String getMD5(String password){

        /**
         * 加密处理第一次
         */
        StringBuilder sb = new StringBuilder();

        String prefix = "ciallo";
        String suffix = "～(∠·ω< )⌒☆";
        sb.append(prefix).append(password).append(suffix);
        String pwd = DigestUtils.md5DigestAsHex(sb.toString().getBytes());
        System.out.println(pwd);

        /**
         * 加密处理第二次
         */
        sb.setLength(0);
        sb.append(prefix).append(pwd).append(suffix);
        System.out.println(sb.toString());
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
    }
}
