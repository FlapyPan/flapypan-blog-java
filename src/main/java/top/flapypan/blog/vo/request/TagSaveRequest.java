package top.flapypan.blog.vo.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.flapypan.blog.entity.Tag;

/**
 * 标签保存请求
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagSaveRequest extends SaveRequest<Tag> {

    private Long id;

    @Size(min = 2, max = 16, message = "标签名称应在2-16之间")
    private String name;

    @Override
    protected Tag newEntity() {
        return new Tag();
    }
}
