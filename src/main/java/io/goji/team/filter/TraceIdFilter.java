package io.goji.team.filter;


import cn.hutool.core.util.StrUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 日志过滤器
 * 给每一个线程分配一个uuid 以便日志可以可以精准查询一条请求的所有日志
 */
@Order(0)
@Slf4j
@Component
@RequiredArgsConstructor
public class TraceIdFilter implements Filter {


    public static final String requestIdKey = "Trace_ID_";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // 生成UUID并存储在MDC中  然后在日志中打印出来
            String uuid = UUID.randomUUID().toString();
            if (StrUtil.isNotEmpty(requestIdKey)) {
                MDC.put(requestIdKey, uuid);
                if (request instanceof HttpServletRequest) {
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    // 把RequestId放到response的header中去方便追踪
                    httpResponse.setHeader(requestIdKey, uuid);
                }
            } else {
                // 如果requestIdKey为空的话  说明配置有问题
                log.error("traceRequestIdKey 为null, 请检查项目yml中traceRequestIdKey的配置是否正确。");
            }

            // 继续处理请求
            chain.doFilter(request, response);
        } finally {
            // 清除MDC中的UUID
            removeRequestIdSafely(requestIdKey);
        }
    }

    public void removeRequestIdSafely(String requestIdKey) {
        try {
            MDC.remove(requestIdKey);
        } catch (Exception e) {
            log.error("traceRequestIdKey 为null, 请检查项目traceRequestIdKey的配置是否正确。", e);
        }
    }
}
