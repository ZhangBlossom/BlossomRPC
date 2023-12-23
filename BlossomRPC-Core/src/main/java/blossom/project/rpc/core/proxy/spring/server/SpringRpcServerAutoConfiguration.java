package blossom.project.rpc.core.proxy.spring.server;

import blossom.project.rpc.common.register.RegisterService;
import blossom.project.rpc.core.proxy.spring.SpringRpcProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.net.UnknownHostException;
import java.util.*;

@Configuration
//@AutoConfigureOrder(value = Integer.MAX_VALUE)
//@AutoConfigureAfter(RegisterService.class)
public class SpringRpcServerAutoConfiguration
    //implements InitializingBean
    implements ApplicationContextAware
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
     * @return SpringRpcServiceDeclarationProcessor实例
     * @throws UnknownHostException
     */
    @Bean
    @Lazy
    public SpringRpcServiceDeclarationProcessor
    springRpcServiceDeclarationProcessor(
            @Qualifier("springRpcProperties")
            SpringRpcProperties properties
            //RegisterService registerService
    ) throws UnknownHostException
    {
        //创建注册中心
        Map<String, RegisterService> registerServices = applicationContext.getBeansOfType(RegisterService.class);
        // 优先检查是否存在 SPI 实现类
        RegisterService registerService = registerServices.get("spiRegisterService");
        if (Objects.isNull(registerService)) {
            // 如果没有找到 SPI 实现类，使用其他实现
            registerService = registerServices.values().stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No RegisterService implementation found"));
        }
        return new SpringRpcServiceDeclarationProcessor
                (properties.getServicePort(), registerService);
    }
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
