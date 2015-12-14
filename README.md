# xunlian_app
refactoring xunlian_app server

讯联 APP 服务端 （重构版本）

服务端整体分为主服务器和消息服务器

主服务器  ：采用 Java 开发，Netty 为网络框架，Json 为序列化工具，数据库端为 Mysql + c3p0 数据库连接池（基本完成）。

消息服务器：采用 Erlang 开发（正在开发中）。
