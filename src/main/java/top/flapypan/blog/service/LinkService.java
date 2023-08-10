package top.flapypan.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.flapypan.blog.entity.Link;
import top.flapypan.blog.repository.LinkRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkService {

    private final LinkRepository repository;

    /**
     * 获取所有固定链接
     */
    public List<Link> getAll() {
        return repository.findAll();
    }

    /**
     * 保存固定链接
     */
    @Transactional
    public List<Link> save(List<Link> links) {
        repository.deleteAll();
        return repository.saveAll(links);
    }
}
