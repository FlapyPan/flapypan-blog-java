package top.flapypan.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.flapypan.blog.entity.Article;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * 通过路径获取文章
     */
    Optional<Article> findFirstByPath(String path);

    /**
     * 查询所有文章的年份
     */
    @Query("SELECT DISTINCT YEAR(a.createDate) FROM Article a ORDER BY YEAR(a.createDate) DESC")
    List<Integer> findYears();

    /**
     * 根据年份查询文章
     */
    @Query("""
            SELECT a
                FROM Article a
                WHERE YEAR(a.createDate) = :year
                ORDER BY a.createDate DESC
            """)
    List<Article> findByYear(Integer year);

    /**
     * 模糊查询标题和标签名
     */
    Page<Article> findAllByTitleContainingIgnoreCaseOrTagsNameContainingIgnoreCase(
            String title,
            String tagName,
            Pageable pageable
    );

    /**
     * 通过标签名获取文章分页
     */
    Page<Article> findAllByTagsName(String tagName, Pageable pageable);

    /**
     * 通过标签名获取文章数量
     */
    Long countByTagsId(Long tagId);

}
