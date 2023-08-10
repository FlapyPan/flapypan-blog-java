package top.flapypan.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.flapypan.blog.vo.response.RestResult;
import top.flapypan.blog.service.SettingService;

import java.util.Map;

/**
 * 设置相关接口
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/setting")
public class SettingController {

    private final SettingService settingService;

    /**
     * 获取所有设置
     */
    @GetMapping
    public RestResult<Map<String, String>> getSettings() {
        return RestResult.ok(settingService.getSettingsMap());
    }

    /**
     * 保存设置
     */
    @PostMapping
    public RestResult<Void> updateSettings(@RequestBody Map<String, String> settingsMap) {
        settingService.saveSettingsMap(settingsMap);
        return RestResult.ok();
    }

}
