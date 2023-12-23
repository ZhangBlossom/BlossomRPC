package blossom.project.rpc.server;

import blossom.project.rpc.core.entity.RpcRequest;
import blossom.project.rpc.core.proxy.spring.server.SpringBeanManager;
import blossom.project.rpc.core.proxy.spring.server.SpringRpcProxy;
import blossom.project.rpc.server.service.impl.RpcServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/16 16:30
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * ServerRpcBootstrap类
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"blossom.project.rpc","blossom.project.rpc.nacos"})
public class ServerRpcBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ServerRpcBootstrap.class, args);

        //下面的所有代码其实都不需要 做校验用的而已
        SpringBeanManager bean = context.getBean(SpringBeanManager.class);
        RpcServiceImpl service = context.getBean(RpcServiceImpl.class);
        SpringRpcProxy rpcProxy = context.getBean(SpringRpcProxy.class);
        //检测一下是否能找到bean
        if (Objects.isNull(rpcProxy) ||
                Objects.isNull(service) ||
                Objects.isNull(bean)) {
            log.error("Server startup with error,can not found the spring bean");
            System.exit(-1);
        }
        try {
            RpcRequest request = new RpcRequest();
            //这里要解决只用用全类路径的问题需要引入注册中心
            //这里我简单的用Map模拟了一个注册中心
            request.setClassName("blossom.project.rpc.core.service.RpcService");
            request.setMethodName("testRpcRequest");
            request.setParams(new Object[]{"Test the Server Successfully..."});
            request.setParamsTypes(new Class<?>[]{String.class});
            Object data = rpcProxy.invoke(request);
            System.out.println(data);
        } catch (Exception e) {
            log.error("proxy the Service failed!!!,please check the Spring...");
            e.printStackTrace();
            System.exit(-1);
        }
        //------如上所有操作都是为了检查Spring是否生效...----
        //如果不用spring啥事情没有

        //正式启动RPC Server
        //TODO 考虑整合Spring之后使用InitialBean来进行初始化
        //TODO 考虑注册中心的实现
        //new NettyRpcServer("127.0.0.1",8080).start();

        log.info("Server startup successfully!!!");
    }
}
