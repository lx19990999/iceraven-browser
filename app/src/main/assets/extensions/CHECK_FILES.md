# 🔍 文件检查清单

## 📁 必需的文件结构

确保以下文件存在于 `app/src/main/assets/extensions/` 目录中：

```
app/src/main/assets/extensions/
├── preinstalled_extensions.json  ✅ (已存在)
├── ublock-origin.xpi             ❓ (需要下载)
├── tampermonkey.xpi              ❓ (需要下载)
└── README.md                     ✅ (已存在)
```

## 🔍 检查步骤

### 1. 验证配置文件
```bash
# 检查JSON格式是否正确
cat app/src/main/assets/extensions/preinstalled_extensions.json
```

### 2. 检查扩展文件
```bash
# 检查文件是否存在
ls -la app/src/main/assets/extensions/
```

### 3. 验证文件大小
```bash
# .xpi文件应该有合理的大小（通常几MB）
du -h app/src/main/assets/extensions/*.xpi
```

## 📥 下载扩展文件

### uBlock Origin
1. 访问: https://github.com/gorhill/uBlock/releases
2. 下载最新的 `uBlock0.firefox.xpi`
3. 重命名为 `ublock-origin.xpi`
4. 放入 `app/src/main/assets/extensions/`

### Tampermonkey
1. 访问: https://addons.mozilla.org/firefox/addon/tampermonkey/
2. 右键点击"Add to Firefox" → "Save Link As"
3. 保存为 `tampermonkey.xpi`
4. 放入 `app/src/main/assets/extensions/`

## 🐛 常见问题

### 问题1: 文件不存在
- **症状**: 日志显示 "Extension file not found"
- **解决**: 确保.xpi文件存在且文件名正确

### 问题2: JSON格式错误
- **症状**: 日志显示 JSON 解析错误
- **解决**: 验证preinstalled_extensions.json格式

### 问题3: 权限问题
- **症状**: 无法读取assets文件
- **解决**: 确保文件在正确的assets目录中

### 问题4: 扩展ID不匹配
- **症状**: 日志显示 "Extension ID mismatch"
- **解决**: 检查配置中的ID是否与扩展真实ID一致

## 📱 调试日志

编译并运行应用后，使用以下命令查看日志：

```bash
adb logcat | grep -E "(FenixApplication|installPreinstalled)"
```

应该看到类似的输出：
```
I/FenixApplication: Starting preinstalled extensions installation process...
I/FenixApplication: Found 2 extensions in configuration
I/FenixApplication: Extension file found, installing preinstalled extension: uBlock Origin
I/FenixApplication: Successfully installed preinstalled extension: uBlock Origin
```

## ✅ 验证安装

1. 启动应用
2. 进入 设置 > 扩展
3. 确认扩展已安装并启用
4. 检查扩展图标是否出现在工具栏中