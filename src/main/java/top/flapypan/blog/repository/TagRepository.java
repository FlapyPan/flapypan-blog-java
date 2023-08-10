package top.flapypan.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.flapypan.blog.entity.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 查询名称是否存在
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * 根据名称列表获取标签
     */
    List<Tag> findAllByNameIn(Collection<String> names);

    /**
     * 通过标签名查询
     */
    Optional<Tag> findByName(String name);

}
