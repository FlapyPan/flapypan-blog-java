package top.flapypan.blog.service;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.flapypan.blog.common.RestException;
import top.flapypan.blog.entity.Tag;
import top.flapypan.blog.repository.ArticleRepository;
import top.flapypan.blog.repository.TagRepository;
import top.flapypan.blog.vo.request.TagSaveRequest;
import top.flapypan.blog.vo.response.ArticleInfo;

import java.util.List;

/**
 * 标签相关服务
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository repository;
    private final ArticleRepository articleRepository;

    /**
     * 获取所有标签
     */
    public List<Tag> getAll() {
        return repository.findAll();
    }

    /**
     * 通过标签名查询
     */
    public Tag findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND.value(), "不存在的标签"));
    }

    /**
     * 添加标签
     */
    @Transactional
    public String add(TagSaveRequest addRequest) {
        Tag tag = addRequest.createEntity(it -> {
        });
        if (repository.existsByNameIgnoreCase(tag.getName())) {
            throw new RestException(HttpStatus.BAD_REQUEST.value(), "标签已经存在");
        }
        return repository.save(tag).getName();
    }

    /**
     * 修改标签
     */
    @Transactional
    public String update(TagSaveRequest updateRequest) {
        Tag tag = repository.findById(updateRequest.getId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND.value(), "不存在的标签"));
        updateRequest.copyToEntity(tag, it -> {
        });
        return repository.save(tag).getName();
    }

    /**
     * 删除标签
     */
    @Transactional
    public void delete(@Positive Long id) {
        if (articleRepository.countByTagsId(id) > 0) {
            throw new RestException(HttpStatus.BAD_REQUEST.value(), "当前标签下存在文章");
        }
        repository.deleteById(id);
    }

    /**
     * 通过标签名获取文章列表
     */
    public Page<ArticleInfo> getArticleByTag(String tagName, Pageable pageable) {
        repository.findByName(tagName)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND.value(), "不存在的标签"));
        return articleRepository
                .findAllByTagsName(tagName, pageable)
                .map(ArticleInfo::new);
    }

}
