package blossom.project.rpc.core.proxy.spring.server;

import blossom.project.rpc.common.register.RegisterService;
import blossom.project.rpc.core.proxy.spring.SpringRpcProperties;
import blossom.project.rpc.core.proxy.spring.server.SpringRpcServiceDeclarationProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;
import java.util.ServiceLoader;

@Configuration
public class SpringRpcServerSpiAutoConfiguration {

    @Bean
    public RegisterService spiRegisterService() {
        ServiceLoader<RegisterService> serviceLoader =
                ServiceLoader.load(RegisterService.class);
        for (RegisterService service : serviceLoader) {
            // TODO 添加一些逻辑来选择特定的实现
            return service;
        }
        //throw new IllegalStateException("No RegisterService implementation found");
        return null;
    }

    @Bean
    public SpringRpcServiceDeclarationProcessor springRpcServiceDeclarationProcessor(
            @Qualifier("springRpcProperties")
            SpringRpcProperties properties,
            RegisterService registerService
    ) throws UnknownHostException {
        return new SpringRpcServiceDeclarationProcessor
                (properties.getServicePort(), registerService);
    }
}
