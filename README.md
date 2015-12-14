# xunlian_app
讯联 APP 服务端 （重构版本）

服务端整体分为主服务器和消息服务器

主服务器  （基本完成）  ：采用 Java 开发，Netty 为网络框架，Json 为数据交换格式，数据库端为 Mysql + c3p0 数据库连接池。

消息服务器（正在开发中）：采用 Erlang/OTP开发，Google Protocol buffer 为数据交换格式，网络和数据库采用 Erlang 内置进行处理。
