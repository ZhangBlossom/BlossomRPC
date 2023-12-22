package blossom.project.rpc.core.proxy.spring.server;

import blossom.project.rpc.common.enums.LoadBalanceTypeEnum;
import blossom.project.rpc.common.enums.RegisterTypeEnum;
import blossom.project.rpc.common.loadbalance.LoadBalanceFactory;
import blossom.project.rpc.core.proxy.spring.SpringRpcProperties;
import blossom.project.rpc.core.register.RegisterFactory;
import blossom.project.rpc.core.register.RegisterService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/18 22:22
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcConfiguration类
 */
@Configuration
//开启配置文件
//@EnableConfigurationProperties(SpringRpcServerProperties.class)
public class SpringRpcServerAutoConfiguration {

    @Bean
    public SpringRpcServiceDeclarationProcessor
    springRpcServiceDeclarationProcessor(
            //@Qualifier(value = "springRpcServerProperties")
            //SpringRpcServerProperties properties
            @Qualifier("springRpcProperties")
            SpringRpcProperties properties
    ) throws UnknownHostException {
        //创建注册中心
        RegisterService registerService = RegisterFactory
                .createRegisterService(
                        properties.getRegisterAddress(),
                        RegisterTypeEnum.findByName(properties.getRegisterName()),
                        LoadBalanceFactory
                                .getLoadBalanceStrategy
                                        (LoadBalanceTypeEnum.findByName(
                                                properties.getLoadBalanceStrategy()
                                                )
                                        )
                );
        return new SpringRpcServiceDeclarationProcessor
                (properties.getServicePort(), registerService);
    }


}
