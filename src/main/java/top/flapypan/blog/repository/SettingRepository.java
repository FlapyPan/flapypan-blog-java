package top.flapypan.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.flapypan.blog.entity.Setting;

import java.util.Set;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {
    /**
     * 获取所有设置的 key
     */
    @Query("SELECT DISTINCT s.key FROM Setting s")
    Set<String> findAllKey();

}
