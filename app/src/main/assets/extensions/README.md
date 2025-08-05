# 预装扩展目录

这个目录用于存放预装的Firefox扩展。

## 支持的扩展

- uBlock Origin: 广告拦截器
- Privacy Badger: 隐私保护
- Decentraleyes: 本地CDN模拟

## 使用方法

1. 将扩展的.xpi文件放在对应的子目录中
2. 在FenixApplication.kt中添加安装逻辑
3. 重新编译应用

## 注意事项

- 扩展文件必须是有效的Firefox扩展(.xpi格式)
- 确保扩展与当前Firefox版本兼容
- 预装扩展会在应用首次启动时自动安装