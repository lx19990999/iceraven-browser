# 如何添加预装扩展

## 📦 获取扩展文件

### 方法1: 从Firefox Add-ons官网下载
1. 访问 [Firefox Add-ons](https://addons.mozilla.org/)
2. 搜索你想要的扩展（如uBlock Origin）
3. 右键点击"添加到Firefox"按钮
4. 选择"另存为"，保存.xpi文件

### 方法2: 从GitHub发布页下载
- **uBlock Origin**: https://github.com/gorhill/uBlock/releases
- **Privacy Badger**: https://github.com/EFForg/privacybadger/releases  
- **Decentraleyes**: https://git.synz.io/Synzvato/decentraleyes/-/releases

### 方法3: 从官方网站下载
- **Tampermonkey**: https://addons.mozilla.org/firefox/addon/tampermonkey/

## 📁 添加扩展文件

1. 将下载的.xpi文件放入 `app/src/main/assets/extensions/` 目录
2. 重命名文件为配置中指定的名称：
   - `ublock-origin.xpi`
   - `tampermonkey.xpi`
   - `privacy-badger.xpi`
   - `decentraleyes.xpi`

## ⚙️ 修改配置

编辑 `preinstalled_extensions.json` 文件来添加或修改扩展：

```json
{
  "extensions": [
    {
      "id": "扩展ID",
      "name": "扩展名称", 
      "filename": "文件名.xpi",
      "enabled": true,
      "description": "扩展描述"
    }
  ]
}
```

## 🔍 如何找到扩展ID（重要！）

⚠️ **扩展ID必须与扩展文件中的真实ID完全一致，不能随意修改！**

### 方法1: 从扩展文件中提取
```bash
# .xpi文件实际上是zip格式，可以解压查看
unzip extension.xpi
cat manifest.json | grep '"id"'
```

### 方法2: 从Firefox调试页面查看
1. 在Firefox中安装扩展
2. 访问 `about:debugging#/runtime/this-firefox`
3. 找到扩展，查看其内部UUID

### 方法3: 从Add-ons页面URL
1. 访问扩展的Firefox Add-ons页面
2. 查看页面源码中的扩展ID信息

### 方法4: 使用开发者工具
1. 在Firefox中按F12打开开发者工具
2. 在控制台中输入：`browser.management.getAll()`
3. 查看已安装扩展的ID列表

## 📋 推荐的预装扩展

### 🛡️ 隐私保护类
- **uBlock Origin** (`uBlock0@raymondhill.net`)
- **Privacy Badger** (`jid1-MnnxcxisBPnSXQ@jetpack`)
- **Decentraleyes** (`jid1-BoFifL9Vbdl2zQ@jetpack`)

### 🔧 实用工具类
- **Tampermonkey** (`firefox@tampermonkey.net`)
- **Dark Reader** (`addon@darkreader.org`)
- **ClearURLs** (`{74145f27-f039-47ce-a470-a662b129930a}`)

## ⚠️ 注意事项

1. **兼容性**: 确保扩展与当前Firefox版本兼容
2. **权限**: 某些扩展可能需要特殊权限
3. **性能**: 过多扩展可能影响浏览器性能
4. **更新**: 预装扩展不会自动更新，需要手动替换文件

## 🚀 编译和测试

1. 添加扩展文件后重新编译应用
2. 首次启动时扩展会自动安装
3. 在设置 > 扩展中查看已安装的扩展
4. 检查logcat输出确认安装状态

## 🐛 故障排除

如果扩展安装失败，检查：
- 文件路径是否正确
- 扩展ID是否匹配
- .xpi文件是否有效
- logcat中的错误信息