package top.flapypan.blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

/**
 * 设置实体类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_setting")
public class Setting {

    @Id
    @Column(name = "s_key")
    private String key = "";

    @Column(name = "s_value")
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting setting = (Setting) o;
        return Objects.equals(key, setting.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
