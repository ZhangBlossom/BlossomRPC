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

import java.util.Map;
import java.util.Objects;
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
            //RegisterService registerService
    )
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
        return new SpringRpcAutowiredProxyProcessorGentle(registerService);
        //return new SpringRpcAutowiredProxyProcessor(properties,registerService);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        findAllRegisterServices();
        //RegisterService bean = applicationContext.getBean(RegisterService.class);
        //System.out.println(bean);
    }
    public void findAllRegisterServices() {
        // 获取所有RegisterService类型的Bean
        Map<String, RegisterService> registerServices = applicationContext.getBeansOfType(RegisterService.class);

        for (Map.Entry<String, RegisterService> entry : registerServices.entrySet()) {
            String beanName = entry.getKey();
            RegisterService service = entry.getValue();
            // 处理每个RegisterService实例
            System.out.println("Bean Name: " + beanName + ", Service: " + service);
        }
    }
}
