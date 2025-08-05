# 🔧 扩展安装模式配置

## 📋 配置选项

在 `preinstalled_extensions.json` 中的 `install_settings` 部分可以配置安装行为：

```json
{
  "install_settings": {
    "sequential_install": true,
    "install_delay_ms": 1000,
    "auto_enable": true,
    "silent_install": false
  }
}
```

## ⚙️ 配置说明

### `sequential_install` (布尔值)
- **true**: 顺序安装扩展，一个接一个
- **false**: 并行安装所有扩展
- **推荐**: `true` (避免多个对话框同时弹出)

### `install_delay_ms` (数字)
- 顺序安装时每个扩展之间的延迟时间（毫秒）
- **默认**: 1000 (1秒)
- **推荐**: 1000-3000 (给用户足够时间处理对话框)

### `auto_enable` (布尔值)
- **true**: 安装后自动启用扩展
- **false**: 安装后不自动启用，需要用户手动启用
- **推荐**: `true`

### `silent_install` (布尔值)
- **true**: 尝试静默安装（默认设置）
- **false**: 显示安装确认对话框
- **注意**: 由于Firefox安全策略，可能仍需要用户确认

### `allow_private_mode` (布尔值)
- **true**: 自动允许扩展在隐私浏览模式下运行
- **false**: 扩展在隐私模式下不可用，需要用户手动启用
- **推荐**: `true` (提供完整的隐私浏览体验)

## 🎯 推荐配置

### 静默安装模式（推荐）
```json
{
  "install_settings": {
    "sequential_install": true,
    "install_delay_ms": 1000,
    "auto_enable": true,
    "silent_install": true,
    "allow_private_mode": true
  }
}
```
- 顺序安装，每个扩展间隔1秒
- 尝试静默安装，减少用户干预
- 自动启用扩展
- 自动允许隐私模式访问

### 快速安装模式
```json
{
  "install_settings": {
    "sequential_install": true,
    "install_delay_ms": 500,
    "auto_enable": true,
    "silent_install": false
  }
}
```
- 快速顺序安装，间隔0.5秒
- 适合熟悉用户

### 并行安装模式（不推荐）
```json
{
  "install_settings": {
    "sequential_install": false,
    "install_delay_ms": 0,
    "auto_enable": true,
    "silent_install": false
  }
}
```
- 同时安装所有扩展
- 可能导致多个对话框同时弹出

## 📱 用户体验

### 顺序安装流程
1. 应用启动
2. 检测到需要安装的扩展
3. 弹出第一个扩展的安装对话框
4. 用户确认安装
5. 等待设定的延迟时间
6. 弹出第二个扩展的安装对话框
7. 重复直到所有扩展安装完成

### 日志输出示例
```
I/FenixApplication: Install settings - Sequential: true, Delay: 1000ms, Auto-enable: true, Silent: true, Private-mode: true
I/FenixApplication: Starting sequential installation of 2 extensions...
I/FenixApplication: Installing extension 1/2: uBlock Origin
I/FenixApplication: Successfully installed: uBlock Origin (uBlock0@raymondhill.net)
I/FenixApplication: Successfully enabled: uBlock Origin
I/FenixApplication: Setting private mode permission for extension: uBlock Origin
I/FenixApplication: Successfully enabled private mode access for: uBlock Origin
I/FenixApplication: Installing extension 2/2: Tampermonkey
I/FenixApplication: Successfully installed: Tampermonkey (firefox@tampermonkey.net)
I/FenixApplication: Successfully enabled: Tampermonkey
I/FenixApplication: Setting private mode permission for extension: Tampermonkey
I/FenixApplication: Successfully enabled private mode access for: Tampermonkey
I/FenixApplication: All preinstalled extensions installation completed
```

## 🔧 故障排除

### 问题1: 扩展安装失败
- **症状**: 日志显示安装失败
- **解决**: 检查.xpi文件是否有效，扩展是否与Firefox版本兼容

### 问题2: 对话框不出现
- **症状**: 没有安装确认对话框
- **解决**: 检查Firefox权限设置，确保允许安装扩展

### 问题3: 安装过程中断
- **症状**: 只安装了部分扩展
- **解决**: 检查日志错误信息，增加延迟时间

## ⚠️ 静默安装限制

### Firefox安全策略
- Firefox出于安全考虑，通常需要用户确认扩展安装
- `silent_install: true` 会尝试减少用户交互，但可能仍需确认
- 预装扩展从本地文件安装，安全性相对较高

### 实际效果
- **最佳情况**: 完全静默安装，无用户交互
- **常见情况**: 减少确认步骤，但仍可能需要用户点击确认
- **最差情况**: 与普通安装相同，需要完整的用户确认流程

## 💡 最佳实践

1. **使用顺序安装**: 避免用户界面混乱
2. **启用静默安装**: 减少用户干预，提升体验
3. **设置合理延迟**: 给系统足够时间处理安装
4. **启用详细日志**: 便于调试问题
5. **测试扩展兼容性**: 确保扩展能正常工作
6. **用户友好提示**: 如果需要确认，提供清晰的说明

现在你可以通过修改配置文件来控制扩展的安装行为了！🚀