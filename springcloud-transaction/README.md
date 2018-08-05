**springcloud整合lcn解决分布式事务问题：**
分布式事务产生的原因：
    不同的JDBC连接操作数据库时产生事务不一致。

一、LCN解决分布式事务
LCN启动：
    1、自行官网下载LCN源码(jar中Redis密码为:'')
    2、启动eureka服务注册中心
    3、修改LCN中tx-manager  application配置文件相关参数
    4、启动Redis服务（tx-manager依赖Redis缓存）
    5、启动tx-manager服务
    6、访问http://127.0.0.1:8899/index.html验证是否启动成功

二、TCC编程模式（bytetcc）
    所谓的TCC编程模式，也是两阶段提交的一个变种。TCC提供了一个编程框架，将整个业务逻辑分为三块：Try、Confirm和Cancel三个操作。
    以在线下单为例，Try阶段会去扣库存，Confirm阶段则是去更新订单状态，如果更新订单失败，则进入Cancel阶段，会去恢复库存。

三、基于MQ消息中间件柔性事务处理(message-*)
    提交订单时，发送消息至MQ，订单流水服务监听消息添加流水记录，消息异常通过重发机制完成订单流水记录，达到流水记录与订单最终一致。
    