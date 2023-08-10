package top.flapypan.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.flapypan.blog.entity.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
}
