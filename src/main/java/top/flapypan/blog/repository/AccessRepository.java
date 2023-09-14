package top.flapypan.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.flapypan.blog.entity.Access;

import java.time.LocalDateTime;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
    long countByCreateDateBetween(LocalDateTime createDateStart, LocalDateTime createDateEnd);

    long countByArticleId(Long id);
}
