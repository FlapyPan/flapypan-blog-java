package top.flapypan.blog.controller;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.flapypan.blog.vo.response.RestResult;
import top.flapypan.blog.entity.Article;
import top.flapypan.blog.service.ArticleService;
import top.flapypan.blog.vo.request.ArticleSaveRequest;
import top.flapypan.blog.vo.response.ArticleGroupByYear;
import top.flapypan.blog.vo.response.ArticleInfo;

import java.util.List;

/**
 * 文章相关接口
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 获取分页文章信息
     */
    @GetMapping
    public RestResult<Page<ArticleInfo>> getPage(String keyword, Pageable pageable) {
        Page<Article> page;
        if (StringUtils.hasText(keyword)) {
            page = articleService.searchByKeyword(keyword, pageable);
        } else {
            page = articleService.getPage(pageable);
        }
        return RestResult.ok(page.map(ArticleInfo::new));
    }

    /**
     * 获取所有文章信息，并根据年份分组
     */
    @GetMapping("/group-by/year")
    public RestResult<List<ArticleGroupByYear>> getGroupByYear() {
        return RestResult.ok(articleService.groupByYear());
    }

    /**
     * 通过路径获取文章内容
     */
    @Validated
    @GetMapping("/{path}")
    public RestResult<Article> getByPath(@PathVariable @Pattern(regexp = "^[a-z0-9:@._-]+$") String path) {
        Article article = articleService.getByPath(path);
        return RestResult.ok(article);
    }

    /**
     * 添加文章
     */
    @PostMapping
    public RestResult<String> add(@RequestBody @Validated ArticleSaveRequest request) {
        return RestResult.ok(articleService.add(request));
    }

    /**
     * 修改文章
     */
    @PutMapping
    public RestResult<String> update(@RequestBody @Validated ArticleSaveRequest request) {
        return RestResult.ok(articleService.update(request));
    }

    /**
     * 删除文章
     */
    @Validated
    @DeleteMapping("/{id}")
    public RestResult<Void> delete(@PathVariable @Positive Long id) {
        articleService.delete(id);
        return RestResult.ok();
    }

}
