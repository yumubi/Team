package io.goji.team.config;

import io.goji.team.interceptor.JwtInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String[] ORIGINS = new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"};

    private final JwtInterceptor jwtInterceptor;

//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//            .addPathPatterns("/**")
//            .excludePathPatterns(
//                "/v3/**",
//                "/api-docs/**",
//                "/swagger-ui.html/**",
//                "/swagger-ui/**",
//                "/doc.html/**",
//                "/error",
//                "/favicon.ico",
//                "/actuator/**",
//                "/api-docs.yaml"
//            );
//    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
            //添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
            .addMapping("/**")
            //允许哪些ip或域名可以跨域访问 *表示允许所以,不要写*，否则cookie就无法使用了(这里建议不要用 * ，*表示所以请求都允许跨域，安全部门请喝茶 )
            .allowedOriginPatterns("*")
            //是否发送Cookie信息
            .allowCredentials(true)
            .allowedMethods(ORIGINS)
            //暴露哪些头部信息(因为跨域访问默认不能获取全部头部信息)
            .exposedHeaders(HttpHeaders.CONTENT_DISPOSITION)
            //设置等待时间，默认1800秒
            .maxAge(3600);
    }


}
