分析一下需要做的事情
1：消息协议的定义
- 版本 协议版本 1byte
- 压缩/序列化等算法的使用 1byte
- 消息类型 1byte
- 请求ID 8byte
- 消息长度 4byte
- 消息内容 Object
2：编写编码器和解码器
- Server和Client都需要引入编解码器
- 编解码器需要对我这个特定的协议进行解析（基于上面的协议）
- 编解码器只是解析数据，最终数据的处理应该用Handler去操作
3：处理序列化和反序列化
- 参考grpc、google、
- 可以考虑自己实现，也可以考虑使用开源的框架
- Java原生序列化 、 Json 、Arvo 、 protobuf
4：编写Handler处理器

5: 对客户端带有注解的服务进行代理
- JDK动态代理、CGLIB、SpringAop
- 其中Spring这种方式可以考虑使用BeanFactory的方式来得到代理对象

6：编写注册中心代码/服务感知
- 项目是要整合springboot进行使用的
-- 要确保项目于springboot的无缝整合 减少用户感知
- 如何整合注册中心比如zk、nacos
- 如何编写一种通用的模板使得用户无感知，而只需要用到注解
-- 考虑使用AutoConfiguration+Conditional类型的注解进行依赖引入的判断
-- 如果引入了Nacos相关的重点依赖，那么注册中心用nacos
-- 也可以考虑提供枚举类型 / 通过配置文件的方式 让用户手动的去选择选用的注册中心
-- 优化用户体验，考虑在spring的application类型的文件中添加配置文件注释
-- 比如考虑到Spring中提供的各种Aware/Processor的使用
--- 比如ApplicatiopnAware/BeanFactoryAware/BeanClassLoaderAware/EnvironmentAware
--- 策略模式/单例模式/模板方法注册
--- 负载均衡的考虑
--- 考虑服务注册的时候注册上去的元数据信息
--- 如何从多个实例中选取一个特定实例（负载均衡算法实现）

分析意义
1: 某些公司那边并不使用rpc通信的方式，他们用的是mq通信
2: 基站即服务
3: 什么时候用http，什么使用用rpc？
--------------------------