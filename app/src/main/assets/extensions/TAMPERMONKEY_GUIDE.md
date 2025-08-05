# 🐒 Tampermonkey 使用指南

## 📖 什么是Tampermonkey？

Tampermonkey是一个用户脚本管理器，允许你运行自定义的JavaScript脚本来增强网页功能。它可以：

- 修改网页外观和行为
- 添加新功能到现有网站
- 自动化重复性任务
- 绕过某些网站限制
- 增强用户体验

## 🚀 如何获取和安装

### 1. 下载Tampermonkey扩展
- 访问：https://addons.mozilla.org/firefox/addon/tampermonkey/
- 点击"Add to Firefox"按钮旁的下拉箭头
- 选择"Save File"保存.xpi文件
- 重命名为`tampermonkey.xpi`

### 2. 放置文件
- 将`tampermonkey.xpi`放入`app/src/main/assets/extensions/`目录
- 重新编译应用

### 3. 验证安装
- 启动应用后，进入设置 > 扩展
- 确认Tampermonkey已安装并启用

## 📝 如何使用用户脚本

### 安装脚本的方法

#### 方法1: 从Greasy Fork安装
1. 访问 https://greasyfork.org/
2. 搜索你需要的脚本
3. 点击脚本名称进入详情页
4. 点击"Install this script"按钮
5. Tampermonkey会自动打开安装界面

#### 方法2: 手动创建脚本
1. 点击Tampermonkey图标
2. 选择"Create a new script"
3. 编写或粘贴脚本代码
4. 保存脚本

### 常用脚本推荐

#### 🎯 实用工具类
- **AC-baidu**: 重定向优化百度搜索结果
- **Userscript+**: 显示当前网站可用的用户脚本
- **网页限制解除**: 解除网页复制、右键限制

#### 🎨 界面优化类
- **网站暗色模式**: 为网站添加暗色主题
- **字体渲染优化**: 改善网页字体显示效果
- **广告屏蔽增强**: 配合uBlock使用的额外广告屏蔽

#### 🔧 功能增强类
- **视频下载助手**: 添加视频下载按钮
- **图片批量下载**: 批量保存网页图片
- **自动翻页**: 自动加载下一页内容

## ⚙️ 脚本管理

### 查看已安装脚本
1. 点击Tampermonkey图标
2. 选择"Dashboard"
3. 查看所有已安装的脚本

### 启用/禁用脚本
- 在Dashboard中点击脚本名称前的开关
- 或者点击Tampermonkey图标快速切换

### 编辑脚本
1. 在Dashboard中点击脚本名称
2. 进入编辑器修改代码
3. 按Ctrl+S保存

### 删除脚本
1. 在Dashboard中找到要删除的脚本
2. 点击垃圾桶图标
3. 确认删除

## 🛡️ 安全注意事项

### ⚠️ 脚本安全
- 只从可信来源安装脚本
- 仔细阅读脚本权限要求
- 定期检查和清理不需要的脚本
- 避免运行来源不明的脚本

### 🔒 隐私保护
- 某些脚本可能收集用户数据
- 检查脚本的网络请求权限
- 使用隐私浏览模式测试新脚本

## 🐛 故障排除

### 脚本不工作
1. 检查脚本是否启用
2. 确认网站URL匹配脚本规则
3. 查看浏览器控制台错误信息
4. 尝试重新安装脚本

### 性能问题
1. 禁用不必要的脚本
2. 检查脚本是否有内存泄漏
3. 限制同时运行的脚本数量

### 兼容性问题
1. 更新Tampermonkey到最新版本
2. 检查脚本是否支持当前浏览器
3. 查看脚本更新日志

## 📚 学习资源

### 脚本开发
- **Tampermonkey文档**: https://www.tampermonkey.net/documentation.php
- **Greasy Fork**: https://greasyfork.org/ (脚本分享平台)
- **用户脚本API**: 学习GM_*函数的使用

### 社区支持
- Tampermonkey官方论坛
- Reddit r/userscripts社区
- GitHub上的用户脚本项目

## 💡 高级技巧

### 脚本同步
- 使用Tampermonkey的云同步功能
- 在多个设备间同步脚本设置

### 脚本调试
- 使用console.log()输出调试信息
- 利用浏览器开发者工具调试
- 设置断点调试脚本逻辑

### 性能优化
- 使用事件委托减少事件监听器
- 避免频繁的DOM操作
- 合理使用定时器和延迟执行

现在你可以充分利用Tampermonkey的强大功能来定制你的浏览体验了！🚀