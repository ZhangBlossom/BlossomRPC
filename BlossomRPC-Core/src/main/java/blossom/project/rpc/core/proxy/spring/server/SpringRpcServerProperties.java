package blossom.project.rpc.core.proxy.spring.server;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/18 22:25
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcProperties类
 * 项目通用spring相关配置类
 */
@Data
@Configuration(value = "springRpcServerProperties")
//@ConfigurationProperties(prefix = "blossom.rpc.server")
public class SpringRpcServerProperties implements InitializingBean {
    @Value("${blossom.rpc.server.serviceAddress:localhost}")
    private String serviceAddress;

    @Value("${blossom.rpc.server.servicePort:8081}")
    private int servicePort;

    //注册中心的地址 localhost:8848

    @Value("${blossom.rpc.server.registerAddress:localhost:8848}")
    private String registerAddress = "localhost:8848";

    //注册中心的类型 nacos zk
    @Value("${blossom.rpc.server.registerName:nacos}")
    private String registerName = "nacos";

    @Value("${blossom.rpc.server.loadBalance:POLL}")
    private String loadBalanceStrategy;


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----Blossom RPC Server Config Info-----");
        System.out.println(this.serviceAddress);
        System.out.println(this.servicePort);
        System.out.println(this.registerAddress);
        System.out.println(this.registerName);
        System.out.println(this.loadBalanceStrategy);
        System.out.println("-----Blossom RPC Server Config Info-----");
    }
}
