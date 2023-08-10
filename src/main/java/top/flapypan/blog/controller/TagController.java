package top.flapypan.blog.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.flapypan.blog.vo.response.RestResult;
import top.flapypan.blog.entity.Tag;
import top.flapypan.blog.service.TagService;
import top.flapypan.blog.vo.request.TagSaveRequest;
import top.flapypan.blog.vo.response.ArticleInfo;

import java.util.List;

/**
 * 标签相关接口
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    /**
     * 获取所有标签
     */
    @GetMapping
    public RestResult<List<Tag>> getAll() {
        return RestResult.ok(tagService.getAll());
    }

    /**
     * 通过标签名获取标签信息
     */
    @Validated
    @GetMapping("/{name}")
    public RestResult<Tag> getByName(@PathVariable @NotBlank String name) {
        Tag tag = tagService.findByName(name);
        return RestResult.ok(tag);
    }

    /**
     * 添加标签
     */
    @PostMapping
    public RestResult<String> add(@RequestBody @Validated TagSaveRequest request) {
        return RestResult.ok(tagService.add(request));
    }

    /**
     * 修改标签
     */
    @PutMapping
    public RestResult<String> update(@RequestBody @Validated TagSaveRequest request) {
        return RestResult.ok(tagService.update(request));
    }

    /**
     * 删除标签
     */
    @Validated
    @DeleteMapping("/{id}")
    public RestResult<Void> delete(@PathVariable @Positive Long id) {
        tagService.delete(id);
        return RestResult.ok();
    }

    /**
     * 获取标签下的文章列表
     */
    @GetMapping("/{tag}/article")
    public RestResult<Page<ArticleInfo>> getByCategory(@PathVariable String tag, Pageable pageable) {
        return RestResult.ok(tagService.getArticleByTag(tag, pageable));
    }

}
