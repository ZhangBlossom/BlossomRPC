package blossom.project.rpc.core.proxy.spring.client;

import blossom.project.rpc.common.register.RegisterService;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import java.util.ServiceLoader;

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
//@AutoConfigureAfter(RegisterService.class)
@AutoConfigureOrder(Integer.MAX_VALUE)
//开启配置文件
//@EnableConfigurationProperties(SpringRpcClientProperties.class)
public class SpringRpcClientAutoConfigration
    implements ApplicationContextAware
{

    private ApplicationContext applicationContext;



    @Bean
    @Lazy
    //public SpringRpcAutowiredProxyProcessor
    public SpringRpcAutowiredProxyProcessorGentle
    springRpcAutowiredProxyProcessor(
            //@Qualifier(value = "springRpcClientProperties")
            //SpringRpcClientProperties properties
            //@Qualifier("springRpcProperties")
            //SpringRpcProperties properties,
            RegisterService registerService
    )
    {
        //创建注册中心
        //return new SpringRpcAutowiredProxyProcessor(properties,registerService);
        return new SpringRpcAutowiredProxyProcessorGentle(registerService);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        //RegisterService bean = applicationContext.getBean(RegisterService.class);
        //System.out.println(bean);
    }
}
