package blossom.project.rpc.core.proxy.spring.server;

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
@EnableConfigurationProperties(SpringRpcProperties.class)
public class SpringRpcAutoConfiguration {

    @Bean
    public SpringRpcProcessor springRpcProviderBean(SpringRpcProperties properties) throws UnknownHostException {
        return new SpringRpcProcessor(properties.getServicePort());
    }


}
