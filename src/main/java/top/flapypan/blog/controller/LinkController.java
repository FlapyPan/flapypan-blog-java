package top.flapypan.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.flapypan.blog.vo.response.RestResult;
import top.flapypan.blog.entity.Link;
import top.flapypan.blog.service.LinkService;

import java.util.List;

/**
 * 固定链接相关接口
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/link")
public class LinkController {

    private final LinkService linkService;

    /**
     * 获取所有固定链接
     */
    @GetMapping
    public RestResult<List<Link>> getAll() {
        return RestResult.ok(linkService.getAll());
    }

    /**
     * 修改固定链接
     */
    @PostMapping
    public RestResult<List<Link>> save(@RequestBody List<Link> links) {
        return RestResult.ok(linkService.save(links));
    }

}
