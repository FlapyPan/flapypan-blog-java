package top.flapypan.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.flapypan.blog.common.RestException;
import top.flapypan.blog.entity.Article;
import top.flapypan.blog.repository.ArticleRepository;
import top.flapypan.blog.repository.TagRepository;
import top.flapypan.blog.vo.request.ArticleSaveRequest;
import top.flapypan.blog.vo.response.ArticleGroupByYear;

import java.util.List;

/**
 * 文章相关服务
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository repository;
    private final TagRepository tagRepository;

    /**
     * 获取文章分页
     */
    public Page<Article> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 根据年份获取文章，按照创建日期倒序
     */
    public List<ArticleGroupByYear> groupByYear() {
        return repository.findYears()
                .stream()
                .map(year -> new ArticleGroupByYear(
                        year,
                        repository.findByYear(year)
                ))
                .toList();
    }

    /**
     * 模糊查询文章
     */
    public Page<Article> searchByKeyword(String keyword, Pageable pageable) {
        return repository.findAllByTitleContainingIgnoreCaseOrTagsNameContainingIgnoreCase(keyword, keyword, pageable);
    }

    /**
     * 通过路径查询文章
     */
    public Article getByPath(String path) {
        return repository.findFirstByPath(path)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND.value(), "不存在的文章"));
    }

    /**
     * 添加文章
     */
    @Transactional
    public String add(ArticleSaveRequest addRequest) {
        // 转换为实体
        Article article = addRequest.createEntity(it -> {
            // 获取 tag
            List<String> names = addRequest.getTagNames();
            it.setTags(tagRepository.findAllByNameIn(names));
        });
        // 保存，返回路径
        return repository.save(article).getPath();
    }

    /**
     * 修改文章
     */
    @Transactional
    public String update(ArticleSaveRequest request) {
        // 查询已有的实体
        Article article = repository.findById(request.getId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND.value(), "不存在的文章"));
        // 复制属性到实体
        request.copyToEntity(article, it -> {
            // 获取 tag
            List<String> names = request.getTagNames();
            it.setTags(tagRepository.findAllByNameIn(names));
        });

        // 保存，返回路径
        return repository.save(article).getPath();
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
