package top.flapypan.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.flapypan.blog.vo.response.RestResult;

/**
 * 认证相关接口
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * 检查登录状态
     */
    @GetMapping
    public RestResult<Boolean> check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return RestResult.ok(authentication != null && authentication.isAuthenticated());
    }

}
