package top.flapypan.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.flapypan.blog.vo.response.RestResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * security 配置
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class SecurityConfig {

    /**
     * 密码加解密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 提供认证管理器的 Bean
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 用户信息服务
     */
    @Bean
    public UserDetailsService userDetailsService(
            @Value("${blog.username}")
            String username,
            @Value("${blog.password}")
            String password
    ) {
        return new InMemoryUserDetailsManager(
                // 管理员
                User.builder()
                        .username(username)
                        .password(password)
                        .build()
        );
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 关闭 csrf
        http.csrf(AbstractHttpConfigurer::disable);
        // 使用 form 登录
        http.formLogin(formLogin -> formLogin
                .loginProcessingUrl("/auth/login")
                .successHandler((request, response, authentication) ->
                        writeResponse(response, RestResult.ok("登录成功"))
                )
                .failureHandler((request, response, authentication) ->
                        writeResponse(response, RestResult.err(HttpStatus.UNAUTHORIZED.value(), "登录失败"))
                ));
        // 记住我
        http.rememberMe(rememberMe -> rememberMe.tokenValiditySeconds(3600 * 24 * 7)); // 7 天
        http.logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler((request, response, authentication) ->
                        writeResponse(response, RestResult.ok("退出登录"))
                ));

        // 关闭匿名功能
        http.anonymous(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request
                // 忽略所有 GET 请求
                /// 这里是 undertow 的问题，没办法直接使用请求方法和请求路径，需要指定 matcher 类型
                .requestMatchers(
                        AntPathRequestMatcher.antMatcher(HttpMethod.GET)
                )
                .permitAll()
                // 忽略图片访问、登录接口
                .requestMatchers(
                        AntPathRequestMatcher.antMatcher("/static/**"),
                        AntPathRequestMatcher.antMatcher("/auth/**")
                )
                .permitAll()
                // 其他请求需要认证
                .anyRequest()
                .authenticated()
        );
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint((request, response, authException) ->
                        writeResponse(response, RestResult.err(HttpStatus.UNAUTHORIZED.value(), "请登录"))
                )
                .accessDeniedHandler((request, response, authException) ->
                        writeResponse(response, RestResult.err(HttpStatus.UNAUTHORIZED.value(), "请登录"))
                )
        );

        return http.build();
    }

    /**
     * 将错误信息写入响应体
     */
    public void writeResponse(HttpServletResponse response, RestResult<String> result) throws IOException {
        // 设置 http 状态码
        response.setStatus(HttpStatus.OK.value());
        // 设置 utf8 编码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 类型 json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }

}
