package blossom.project.rpc.core.proxy.spring.client;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 20:58
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcClientProperties类
 */
@Data
@Configuration(value = "springRpcClientProperties")
@ConfigurationProperties(prefix = "blossom.rpc.client")
public class SpringRpcClientProperties implements InitializingBean {
    private String serviceAddress;

    private int servicePort;

    //注册中心的地址 localhost:8848
    private String registerAddress = "localhost:8848";

    //注册中心的类型 nacos zk
    private String registerName = "nacos";

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----Blossom RPC Client Config Info-----");
        System.out.println(this.serviceAddress);
        System.out.println(this.servicePort);
        System.out.println(this.registerAddress);
        System.out.println(this.registerName);
        System.out.println("-----Blossom RPC Client Config Info-----");
    }
}
