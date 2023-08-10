package top.flapypan.blog.vo.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.flapypan.blog.entity.Article;

import java.util.List;

/**
 * 通过年份分组的文章列表
 */
@Getter
@Setter
@NoArgsConstructor
public class ArticleGroupByYear {

    private String year;

    private List<ArticleInfo> list;

    public ArticleGroupByYear(Integer year, List<Article> articleList) {
        this.year = year.toString();
        this.list = articleList.stream().map(ArticleInfo::new).toList();
    }

}
