package blossom.project.rpc.core.proxy.spring;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/21 16:37
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcProperties类
 */
@Data
@Configuration(value = "springRpcProperties")
@ConfigurationProperties(prefix = "blossom.rpc")
public class SpringRpcProperties implements EnvironmentAware {

    private String registerAddress = "localhost:8848";

    //注册中心的类型 nacos zk
    private String registerName = "nacos";


    private String loadBalanceStrategy = "POLL";

    private String serviceAddress = "localhost";

    private int servicePort = 8081;


    @Override
    public void setEnvironment(Environment environment) {
        //这两个不能一样 netty得用一个
        //SpringBoot-Web的tomcat也得用一个
        //String port = environment.getProperty("server.port");
        //this.servicePort = Integer.parseInt(port);
    }
}
