package blossom.project.rpc.core.proxy.spring.server;

import blossom.project.rpc.common.register.RegisterService;
import blossom.project.rpc.core.proxy.spring.SpringRpcProperties;
import blossom.project.rpc.core.proxy.spring.server.SpringRpcServiceDeclarationProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;

@Configuration
//@AutoConfigureOrder(value = Integer.MAX_VALUE)
//@AutoConfigureAfter(RegisterService.class)
public class SpringRpcServerSpiAutoConfiguration
    //implements InitializingBean
{

    /**
     * 检查SPI实现并相应地注册RegisterService bean。
     * 如果没有SPI实现，则不注册bean。
     *
     * @return RegisterService的实现或null。
     */

    //private RegisterService registerService;
    //@Override
    //public void afterPropertiesSet() throws Exception {
    //    ServiceLoader<RegisterService> serviceLoader =
    //            ServiceLoader.load(RegisterService.class);
    //    this.registerService = Optional.of(serviceLoader.iterator())
    //            .filter(Iterator::hasNext)
    //            .map(Iterator::next)
    //            .orElse(null);
    //}




    /**
     * 创建SpringRpcServiceDeclarationProcessor bean。
     *
     * @param properties Spring RPC的配置属性
     * @param registerService 注册服务实现
     * @return SpringRpcServiceDeclarationProcessor实例
     * @throws UnknownHostException
     */
    @Bean
    @Lazy
    public SpringRpcServiceDeclarationProcessor
    springRpcServiceDeclarationProcessor(
            @Qualifier("springRpcProperties")
            SpringRpcProperties properties,
            RegisterService registerService
    ) throws UnknownHostException
    {
        return new SpringRpcServiceDeclarationProcessor
                (properties.getServicePort(), registerService);
    }

}
