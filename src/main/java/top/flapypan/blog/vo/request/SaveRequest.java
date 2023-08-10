package top.flapypan.blog.vo.request;

import org.springframework.beans.BeanUtils;

import java.util.function.Consumer;

/**
 * 保存实体的请求
 */
public abstract class SaveRequest<Entity> {

    /**
     * 默认的属性复制忽略列表
     */
    private static final String[] DEFAULT_COPY_IGNORE_PROPERTIES = {};

    /**
     * 获取属性复制忽略列表，覆盖此方法即可自定义忽略列表
     * @return
     */
    protected String[] getIgnoreProperties() {
        return DEFAULT_COPY_IGNORE_PROPERTIES;
    }

    /**
     * 复制属性
     *
     * @param entity 复制的目标实体
     */
    protected void copyPropertiesTo(Entity entity) {
        // 使用 Spring 自带的复制工具
        BeanUtils.copyProperties(this, entity, getIgnoreProperties());
    }

    /**
     * 获取新实体，由继承者提供具体实现
     */
    protected abstract Entity newEntity();

    /**
     * 构建新的实体
     *
     * @param after 创建之后执行的方法
     * @return 新的实体
     */
    public Entity createEntity(Consumer<Entity> after) {
        // 构建新实体
        Entity entity = newEntity();
        // 复制属性，并执行后续方法
        copyToEntity(entity, after);
        return entity;
    }

    /**
     * 复制值到已有的实体
     *
     * @param entity 需要复制的目标
     * @param after  复制之后执行的方法
     */
    public void copyToEntity(Entity entity, Consumer<Entity> after) {
        // 复制属性
        copyPropertiesTo(entity);
        // 执行后续方法
        after.accept(entity);
    }

}
