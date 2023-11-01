package io.goji.team.utils.ip;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import io.goji.team.utils.JSON;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

/**
 * query geography address from ip
 */
@Slf4j
public class OnlineIpRegionUtil {

    private OnlineIpRegionUtil() {
    }

    /**
     * website for query geography address from ip
     */
    public static final String ADDRESS_QUERY_SITE = "http://whois.pconline.com.cn/ipJson.jsp";


    public static IpRegion getIpRegion(String ip) {
        if (StrUtil.isBlank(ip) || IpUtil.isValidIpv6(ip) || !IpUtil.isValidIpv4(ip)) {
            return null;
        }

            try {
                String rspStr = HttpUtil.get(ADDRESS_QUERY_SITE + "?ip=" + ip + "&json=true",
                    StandardCharsets.UTF_8);

                if (StrUtil.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return null;
                }

                String province = JSON.get(rspStr, "pro");;
                String city = JSON.get(rspStr, "city");
                return new IpRegion(province, city);
            } catch (Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        return null;
    }

}
