package blossom.project.rpc.core.proxy.spring.client;

import blossom.project.rpc.core.proxy.spring.SpringRpcProperties;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
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
 * TODO 解决属性注入的问题
 * 发现在client启动的时候得不到client端的配置文件信息
 * 1：可以考虑先使用EnvironmentAware来暴力解决
 * 2：考虑用一下@Value注解看看行不行
 */
@Deprecated
@Data
//@Configuration(value = "springRpcClientProperties")
//@ConfigurationProperties(prefix = "blossom.rpc.client")
public class SpringRpcClientProperties extends SpringRpcProperties implements InitializingBean {

    //注册中心的地址 localhost:8848
    @Value("${blossom.rpc.client.registerAddress:localhost:8848}")
    private String registerAddress = "localhost:8848";

    //注册中心的类型 nacos zk
    @Value("${blossom.rpc.client.registerName:nacos}")
    private String registerName = "nacos";


    @Value("${blossom.rpc.client.loadBalance:POLL}")
    private String loadBalanceStrategy;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----Blossom RPC Client Config Info-----");
        System.out.println(this.registerAddress);
        System.out.println(this.registerName);
        System.out.println(this.loadBalanceStrategy);
        System.out.println("-----Blossom RPC Client Config Info-----");
    }
}
