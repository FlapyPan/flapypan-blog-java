package top.flapypan.blog.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VersionTip implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.warn("本仓库已经停止维护，请迁移至kotlin版本：https://github.com/FlapyPan/flapypan-blog-spring");
    }
}
