package top.flapypan.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.flapypan.blog.config.ClientInfoContext;
import top.flapypan.blog.entity.Access;
import top.flapypan.blog.entity.Article;
import top.flapypan.blog.repository.AccessRepository;
import top.flapypan.blog.vo.response.AccessBaseInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccessService {

    private final AccessRepository repository;

    /**
     * 添加一条新的访问数据
     */
    public void access(Article accessArticle) {
        var clientInfo = ClientInfoContext.get();
        Access access = new Access();
        access.setArticle(accessArticle);
        access.setReferrer(clientInfo.getReferrer());
        access.setUa(clientInfo.getUa());
        access.setIp(clientInfo.getIp());
        repository.save(access);
    }

    public AccessBaseInfo baseInfo() {
        // 获取总访问量
        long all = repository.count();
        // 获取今日访问量
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime now = LocalDateTime.now();
        long today = repository.countByCreateDateBetween(todayStart, now);
        return new AccessBaseInfo(all, today);
    }

    public long countByArticleId(Long id) {
        return repository.countByArticleId(id);
    }
}
