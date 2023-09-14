package top.flapypan.blog.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class ClientInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        // 请求开始前，记录客户端信息
        String referrer = request.getHeader("Referer");
        if (referrer == null) referrer = "";
        String ua = request.getHeader("User-Agent");
        if (ua == null) ua = "";
        String ip = getClientIp(request);
        var info = new ClientInfoContext.ClientInfo(referrer, ua, ip);
        ClientInfoContext.set(info);
    }

    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
    };

    private static String getClientIp(HttpServletRequest request) {
        for (String header : IP_HEADERS) {
            String ip = request.getHeader(header);
            if (StringUtils.hasLength(ip) && "unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        String ip = request.getRemoteAddr();
        return ip == null ? "" : ip;
    }
}
