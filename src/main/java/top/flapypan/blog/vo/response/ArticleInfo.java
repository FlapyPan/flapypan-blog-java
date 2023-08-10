package top.flapypan.blog.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.flapypan.blog.entity.Article;
import top.flapypan.blog.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章信息，无文章内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfo {

    private Long id;

    private String title;

    private String path;

    private String cover;

    private List<Tag> tags;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    /**
     * 通过实体类构造
     */
    public ArticleInfo(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.path = article.getPath();
        this.cover = article.getCover();
        this.tags = article.getTags();
        this.createDate = article.getCreateDate();
        this.updateDate = article.getUpdateDate();
    }

}
