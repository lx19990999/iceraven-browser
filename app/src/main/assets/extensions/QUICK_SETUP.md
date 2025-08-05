# ⚡ 快速设置指南

## 🎯 预装扩展快速配置

### 📥 下载必需文件

#### 1. uBlock Origin (广告拦截)
```bash
# 访问并下载
https://github.com/gorhill/uBlock/releases
# 下载: uBlock0.firefox.xpi
# 重命名为: ublock-origin.xpi
```

#### 2. Tampermonkey (用户脚本管理)
```bash
# 访问并下载
https://addons.mozilla.org/firefox/addon/tampermonkey/
# 点击"Add to Firefox"旁的下拉箭头 > "Save File"
# 重命名为: tampermonkey.xpi
```

### 📁 文件放置
将下载的文件放入：
```
app/src/main/assets/extensions/
├── ublock-origin.xpi
├── tampermonkey.xpi
└── preinstalled_extensions.json
```

### 🔨 编译应用
```bash
./gradlew assembleFenixForkRelease
```

### ✅ 验证安装
1. 启动应用
2. 进入 设置 > 扩展
3. 确认两个扩展都已安装并启用

## 🚀 首次使用配置

### uBlock Origin 设置
1. 点击uBlock图标
2. 启用"高级用户模式"（可选）
3. 更新过滤器列表
4. 根据需要调整拦截级别

### Tampermonkey 设置
1. 点击Tampermonkey图标
2. 进入Dashboard
3. 在设置中配置：
   - 启用"检查更新"
   - 设置"注入模式"为"即时"
   - 启用"实验性功能"（可选）

## 📝 推荐用户脚本

访问 https://greasyfork.org/ 安装：
- **AC-baidu**: 优化百度搜索
- **网页限制解除**: 解除复制限制
- **视频下载助手**: 下载网页视频

## 🔧 故障排除

### 扩展未安装
- 检查.xpi文件是否存在
- 确认文件名与配置匹配
- 查看logcat输出错误信息

### 扩展ID错误
- 解压.xpi文件查看manifest.json
- 确认ID与配置文件一致
- 参考common_extension_ids部分

现在你的Firefox应用将预装uBlock Origin和Tampermonkey，提供强大的广告拦截和脚本管理功能！🎉