package blossom.project.rpc.nacos;

import blossom.project.rpc.common.constants.RpcCommonConstants;
import blossom.project.rpc.common.loadbalance.LoadBalanceStrategy;
import blossom.project.rpc.common.loadbalance.PollLoadBalance;
import blossom.project.rpc.common.register.RegisterService;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/22 18:50
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * NacosAutoConfiguration类
 */
@Configuration
//@AutoConfiguration
//@AutoConfigureBefore(value = RegisterService.class)
@AutoConfigureOrder(value = Integer.MAX_VALUE)
//@Conditional(OnNacosClientClassCondition.class)
public class NacosAutoConfiguration implements ApplicationContextAware, EnvironmentAware {

    /**
     * 这个bean只会在存在nacos的依赖的时候才会创建
     *
     * @return
     */
    @Primary
    @Bean(name = "nacosRegisterService")
    @ConditionalOnMissingBean(name = "spiRegisterService")
    public RegisterService nacosRegisterService() {

        //创建注册中心
        // 优先检查是否存在 SPI 实现类
        // 获取Nacos相关配置，例如服务器地址等
        //String serverAddress = "localhost:8848"; // 从配置中读取Nacos服务器地址
        // ... 其他所需配置
        String registerAddress = environment.getProperty(RpcCommonConstants.REGISTER_ADDRESS);
        try {
            // 使用反射创建NamingService实例
            //Class<?> namingFactoryClass =
            //        Class.forName("com.alibaba.nacos.api.naming.NamingFactory");
            //Method createNamingServiceMethod =
            //        namingFactoryClass.getMethod("createNamingService", String.class);
            //Object namingServiceInstance = createNamingServiceMethod.invoke(null, serverAddress);

            // 创建NacosRegisterService实例
            Class<?> nacosRegisterServiceClass = Class.forName(RpcCommonConstants.NACOS_REGISTER_CLASS);
            Constructor<?> constructor = nacosRegisterServiceClass.getConstructor(String.class,
                    LoadBalanceStrategy.class);
            return (RegisterService) constructor.newInstance(registerAddress, new PollLoadBalance<>());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new IllegalStateException("Failed to create NacosRegisterService", e);
        }
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}