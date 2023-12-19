package blossom.project.rpc.core.proxy.spring.server;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
public class SpringRpcProperties implements InitializingBean {
    private String serviceAddress;

    private int servicePort;

    private String registryAddress;  //注册中心的地址

    private byte registryType; //注册中心的类型

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----Blossom RPC Config Info-----");
        System.out.println(this.serviceAddress);
        System.out.println(this.servicePort);
        System.out.println(this.registryAddress);
        System.out.println(this.registryType);
        System.out.println("-----Blossom RPC Config Info-----");
    }
}
