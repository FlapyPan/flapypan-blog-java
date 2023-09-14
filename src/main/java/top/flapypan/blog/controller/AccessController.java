package top.flapypan.blog.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.flapypan.blog.service.AccessService;
import top.flapypan.blog.vo.response.AccessBaseInfo;
import top.flapypan.blog.vo.response.RestResult;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/access")
public class AccessController {

    private final AccessService accessService;

    /**
     * 获取基本访问量信息
     */
    @GetMapping
    public RestResult<AccessBaseInfo> getBaseInfo() {
        return RestResult.ok(accessService.baseInfo());
    }

    /**
     * 获取指定文章的访问量
     */
    @GetMapping("/article/{id}")
    public RestResult<Long> getByArticleId(@PathVariable("id") @Positive Long id) {
        return RestResult.ok(accessService.countByArticleId(id));
    }
}
