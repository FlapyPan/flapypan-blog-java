package top.flapypan.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.flapypan.blog.entity.Setting;
import top.flapypan.blog.repository.SettingRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 设置相关服务
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SettingService {

    // 默认设置
    public static final Map<String, String> DEFAULT_SETTINGS = new HashMap<>() {{
        put("siteTitle", "FlapyPan's Blog");
        put("favicon", "/avatar.webp");
        put("avatar", "/avatar.webp");
        put("banner", "/banner.webp");
        put("name", "FlapyPan");
        put("email", "flapypan@gmail.com");
        put("info", "个人博客");
        put("pageSize", "12");
        put("footer", "Copyright");
        put("giscusRepo", "");
        put("giscusRepoId", "");
        put("giscusCategory", "");
        put("giscusCategoryId", "");
    }};

    private final SettingRepository repository;

    /**
     * 初始化默认设置
     */
    public void initDefaultSettings() {
        if (repository.count() >= DEFAULT_SETTINGS.size()) return;
        synchronized (SettingService.class) {
            // 获取已经有的key
            Set<String> allKey = repository.findAllKey();
            // 求差集
            Set<String> keysToAdd = new HashSet<>(DEFAULT_SETTINGS.keySet());
            keysToAdd.removeAll(allKey);
            // 生成需要插入的值
            List<Setting> settingsToAdd = keysToAdd.stream()
                    .map(key -> new Setting(key, DEFAULT_SETTINGS.get(key)))
                    .toList();
            int size = repository.saveAllAndFlush(settingsToAdd).size();
            log.info("添加了 {} 项默认设置", size);
        }
    }

    /**
     * 获取设置
     */
    public Map<String, String> getSettingsMap() {
        initDefaultSettings();
        return repository
                .findAll()
                .stream()
                .collect(Collectors.toMap(Setting::getKey, Setting::getValue));
    }

    /**
     * 保存设置
     */
    @Transactional
    public void saveSettingsMap(Map<String, String> settingsMap) {
        List<Setting> settingsWillSaved = repository.findAll().stream() // 获取所有设置
                .filter(s -> settingsMap.containsKey(s.getKey())) // 过滤掉无需存储的
                .filter(s -> !Objects.equals(settingsMap.get(s.getKey()), s.getValue())) // 过滤掉值相等的
                .peek(s -> s.setValue(settingsMap.get(s.getKey()))) // 设置新值
                .toList();
        repository.saveAllAndFlush(settingsWillSaved);
    }

}
