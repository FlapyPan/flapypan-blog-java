package top.flapypan.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FlapypanBlogApiApplication {

    public static void main(String[] args) {
        // 启动参数为 hash <pwd> 时生成输出加密过的密码
        if (args.length > 1 && "hash".equals(args[0])) {
            System.out.println(new BCryptPasswordEncoder().encode(args[1]));
            return;
        }
        SpringApplication.run(FlapypanBlogApiApplication.class, args);
    }

}
