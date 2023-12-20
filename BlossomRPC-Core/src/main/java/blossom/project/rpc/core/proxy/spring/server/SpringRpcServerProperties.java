package blossom.project.rpc.core.proxy.spring.server;

import blossom.project.rpc.core.enums.RegisterTypeEnum;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
@ConfigurationProperties(prefix = "blossom.rpc")
public class SpringRpcServerProperties implements InitializingBean {
    private String serviceAddress = "localhost";

    private int servicePort = 8080;

    //注册中心的地址 localhost:8848
    private String registerAddress = "localhost:8848";

    //注册中心的类型 nacos zk
    private String  registerType = "nacos";

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----Blossom RPC Config Info-----");
        System.out.println(this.serviceAddress);
        System.out.println(this.servicePort);
        System.out.println(this.registerAddress);
        System.out.println(this.registerType);
        System.out.println("-----Blossom RPC Config Info-----");
    }
}
