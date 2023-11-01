package io.goji.team.interceptor;

import io.goji.team.common.result.ResultCode;
import io.goji.team.exception.ApiException;
import io.goji.team.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("token");
        if(StringUtils.isNotEmpty(token)) {
            log.info("token is {}", token);
            try {
                Claims claims = JwtUtils.getToken(token);
                String userid = claims.get("id").toString();
                // TODO: 1/11/2023 通过id查询用户是否为null,为null返回false
                return true;
            } catch (Exception e) {
                log.error("密钥解析失败", (JwtException)e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new ApiException(e, ResultCode.INVALID_TOKEN);
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
