# 简介

STUer是一个面向汕大学生的校园客户端，它主要有五个功能模块，分别为：拼车、约、转让、树洞以及校内。
Servlet + JDBC + MySql的组合进行开发，没有使用什么框架，部署在新浪云平台。
项目所使用的所有图片资源，基本来源于：[http://iconfont.cn/](http://iconfont.cn/)

# ScreenShot

![](https://github.com/leelit/STUer-client/blob/master/art/stuer.gif)

# 实践

- 网络请求：retrofit2 + gson
- 异步处理：RxJava + RxAndroid
- 图片处理：PhotoView + picasso
- 文字缓存：Sqlite
- 沉浸式状态栏：SystemBarTint + StatusBarCompat
- 一些系统新特性：夜间模式，5.x控件
- 项目组织：功能模块与公用基类放在顶级包，每个功能模块下用MVP组织，简单逻辑放在V
- ...

#LICENSE
[MIT](https://github.com/leelit/STUer-client/blob/master/LICENSE)
