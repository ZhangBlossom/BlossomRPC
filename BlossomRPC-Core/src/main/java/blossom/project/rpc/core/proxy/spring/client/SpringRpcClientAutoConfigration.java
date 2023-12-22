package blossom.project.rpc.core.proxy.spring.client;

import blossom.project.rpc.common.RegisterService;
import blossom.project.rpc.core.proxy.spring.SpringRpcProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 22:12
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcClientAutoConfigration类
 */
@Configuration
//开启配置文件
//@EnableConfigurationProperties(SpringRpcClientProperties.class)
public class SpringRpcClientAutoConfigration
{

    @Bean
    public SpringRpcAutowiredProxyProcessor
    springRpcAutowiredProxyProcessor(
            //@Qualifier(value = "springRpcClientProperties")
            //SpringRpcClientProperties properties
            @Qualifier("springRpcProperties")
            SpringRpcProperties properties,
            RegisterService registerService
    )
    {
        //创建注册中心
        return new SpringRpcAutowiredProxyProcessor(properties,registerService);
    }

}
