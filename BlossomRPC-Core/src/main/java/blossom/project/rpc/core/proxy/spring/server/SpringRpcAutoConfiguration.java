package blossom.project.rpc.core.proxy.spring.server;

import blossom.project.rpc.core.enums.RegisterTypeEnum;
import blossom.project.rpc.core.register.RegisterFactory;
import blossom.project.rpc.core.register.RegisterService;
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
@EnableConfigurationProperties(SpringRpcServerProperties.class)
public class SpringRpcAutoConfiguration {

    @Bean
    public SpringRpcServiceDeclarationProcessor springRpcProviderBean(SpringRpcServerProperties properties) throws UnknownHostException {
        //创建注册中心
        RegisterService registerService = RegisterFactory.createRegistryService(
                properties.getRegisterAddress(),
                RegisterTypeEnum.findByName(properties.getRegisterType()));
        return new SpringRpcServiceDeclarationProcessor
                (properties.getServicePort(),registerService);
    }


}
