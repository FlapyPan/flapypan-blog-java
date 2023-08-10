package top.flapypan.blog.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.flapypan.blog.entity.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加文章的请求
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSaveRequest extends SaveRequest<Article> {

    private Long id;

    @Size(min = 3, max = 32, message = "标题长度应在3-32之间")
    private String title;

    @Size(min = 3, max = 64, message = "路径长度应在3-32之间")
    @Pattern(regexp = "^[a-z0-9:@._-]+$", message = "路径只允许小写字母,数字,冒号,@,英文点,下划线,分隔符")
    private String path;

    private String cover;

    @NotBlank(message = "文章内容不能为空")
    private String content;

    private List<String> tagNames = new ArrayList<>();

    @Override
    protected Article newEntity() {
        return new Article();
    }

    private static final String[] copyIgnoreProperties = {
            "tagNames"
    };

    @Override
    protected String[] getIgnoreProperties() {
        // 覆盖默认的忽略列表
        return copyIgnoreProperties;
    }

}
