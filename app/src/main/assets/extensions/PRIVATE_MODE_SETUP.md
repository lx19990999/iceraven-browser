# 🔒 扩展隐私模式配置

## 📋 功能说明

默认情况下，Firefox扩展在隐私浏览模式下是被禁用的，需要用户手动允许。我们的预装扩展系统可以自动为扩展启用隐私模式权限。

## ⚙️ 配置选项

在 `preinstalled_extensions.json` 中设置：

```json
{
  "install_settings": {
    "allow_private_mode": true
  }
}
```

## 🎯 工作原理

### 自动权限设置流程
1. 扩展安装成功
2. 扩展启用成功（如果配置为自动启用）
3. 自动调用 `setAllowedInPrivateBrowsing(extension, true)`
4. 扩展获得隐私模式访问权限

### 代码实现
```kotlin
private fun setExtensionPrivateModePermission(extension: WebExtension, extensionName: String) {
    components.core.engine.setAllowedInPrivateBrowsing(
        extension,
        allowed = true,
        onSuccess = {
            logger.info("Successfully enabled private mode access for: $extensionName")
        },
        onError = { throwable ->
            logger.warn("Failed to enable private mode access for: $extensionName", throwable)
        }
    )
}
```

## 📱 用户体验

### 启用隐私模式权限后
- ✅ 扩展在普通浏览模式下正常工作
- ✅ 扩展在隐私浏览模式下也能正常工作
- ✅ 用户无需手动设置权限
- ✅ 提供一致的浏览体验

### 不启用隐私模式权限
- ✅ 扩展在普通浏览模式下正常工作
- ❌ 扩展在隐私浏览模式下被禁用
- ⚠️ 用户需要手动到设置中启用

## 🔧 配置示例

### 完全启用（推荐）
```json
{
  "install_settings": {
    "allow_private_mode": true
  },
  "extensions": [
    {
      "id": "uBlock0@raymondhill.net",
      "name": "uBlock Origin",
      "filename": "ublock-origin.xpi",
      "enabled": true
    }
  ]
}
```

### 禁用隐私模式权限
```json
{
  "install_settings": {
    "allow_private_mode": false
  }
}
```

## 🛡️ 隐私考虑

### 为什么要启用？
1. **一致性**: 用户期望扩展在所有模式下都能工作
2. **便利性**: 避免用户手动配置的麻烦
3. **功能完整性**: 特别是广告拦截器等隐私保护扩展

### 安全性
- 扩展在隐私模式下的行为与普通模式相同
- 隐私浏览的核心特性（不保存历史、Cookie等）不受影响
- 扩展只是获得了在隐私模式下运行的权限

## 📊 支持的扩展类型

### 推荐启用隐私模式的扩展
- **广告拦截器** (如 uBlock Origin)
- **隐私保护工具** (如 Privacy Badger)
- **脚本管理器** (如 Tampermonkey)
- **密码管理器**
- **翻译工具**

### 可选启用的扩展
- **开发者工具**
- **主题扩展**
- **书签管理器**

## 🔍 调试和验证

### 检查权限设置
1. 进入隐私浏览模式
2. 查看扩展图标是否显示
3. 测试扩展功能是否正常

### 日志输出
```
I/FenixApplication: Setting private mode permission for extension: uBlock Origin
I/FenixApplication: Successfully enabled private mode access for: uBlock Origin
```

### 手动验证
1. 打开 Firefox 设置
2. 进入 扩展 页面
3. 点击扩展名称
4. 查看"在隐私窗口中运行"选项是否已启用

## ⚠️ 故障排除

### 问题1: 隐私模式权限设置失败
- **症状**: 日志显示权限设置失败
- **解决**: 检查扩展是否支持隐私模式

### 问题2: 扩展在隐私模式下不工作
- **症状**: 扩展图标灰色或功能不可用
- **解决**: 手动到设置中启用隐私模式权限

### 问题3: 权限设置被重置
- **症状**: 重启后权限丢失
- **解决**: 检查扩展是否被重新安装

## 💡 最佳实践

1. **默认启用**: 对于隐私保护类扩展，建议默认启用隐私模式权限
2. **用户选择**: 提供配置选项让用户控制此行为
3. **清晰说明**: 在用户界面中说明扩展的隐私模式状态
4. **测试验证**: 确保扩展在隐私模式下正常工作

现在你的预装扩展将自动获得隐私模式访问权限，为用户提供完整的浏览体验！🚀