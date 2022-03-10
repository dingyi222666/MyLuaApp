# MyLuaApp
[![GitHub license](https://img.shields.io/github/license/dingyi222666/MyLuaApp)](https://github.com/dingyi222666/MyLuaApp/blob/main/LICENSE)
[![Telegram](https://img.shields.io/badge/Join-Telegram-blue)](https://t.me/MyLuaApp)
[![QQ](https://img.shields.io/badge/Join-QQ_Group-ff69b4)](https://jq.qq.com/?_wv=1027&k=XnJ4FMvS)   

[English](https://github.com/dingyi222666/MyLuaApp/tree/master/README.md) | 中文

目前该项目仍在***开发中***. 项目还处在**alpha** 阶段，大部分功能都**没有实现**(比如构建模块)。目前本软件还不能完全运行。

MyLuaApp 是一款轻量，快速的编程软件， 使用**Lua和Java混合** 来开发安卓软件。

是的，你可以使用Lua来开发安卓软件!本项目使用了
[AndroLua_pro](https://github.com/nirenr/AndroLua_pro)作为软件的lua运行时环境，它比原版[AndroLua](https://github.com/mkottman/AndroLua)的运行速度要快不少。

如果你还从未上手过Lua,那也没关系。你依旧可以使用Java来开发软件，本软件完全支持原生java开发，并且支持Lua与Java混合开发，但是暂时没有ndk的支持计划。

本项目使用Lua来解析项目配置脚本，基于lua的灵活性，未来甚至可能实现动态添加打包任务到构建模块。

## 功能
- [x] ecj支持(javac)
- [x] aapt2支持
- [x] d8,r8支持
- [x] maven依赖解析支持
- [x] 文件树列表
- [ ] 多模块支持*
- [ ] TextMate 支持(tm4e)*
- [ ] 插件模块*
- [ ] 构建模块*
- [ ] 项目模板*
- [ ] Lua的自动补全支持
- [ ] Java的自动补全支持
- [ ] Xml的自动补全支持

标注为带*号的功能代表着目前正在开发的功能

## 提交贡献
我们欢迎所有人为本项目添砖加瓦，你可以提出issues或者提交一个pull requests，我们欢迎任何人的贡献

## 测试版本
使用github actions,每次提交都可以自动打包测试版本的软件。点击[这里](https://github.com/dingyi222666/MyLuaApp/actions)
来获取上次提交所生成出来的预览测试版本软件

## 讨论组
 - QQ群:[1020019846](https://jq.qq.com/?_wv=1027&k=zGdBLMr8)
 - [Telegram 群](https://t.me/MyLuaApp)
