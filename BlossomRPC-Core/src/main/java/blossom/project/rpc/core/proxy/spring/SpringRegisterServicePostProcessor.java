package blossom.project.rpc.core.proxy.spring;

import blossom.project.rpc.common.constants.RpcCommonConstants;
import blossom.project.rpc.common.loadbalance.LoadBalanceStrategy;
import blossom.project.rpc.common.loadbalance.PollLoadBalance;
import blossom.project.rpc.common.register.RegisterService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.ServiceLoader;

@Configuration
public class SpringRegisterServicePostProcessor implements
        BeanDefinitionRegistryPostProcessor ,
        EnvironmentAware,
        ApplicationContextAware {

    private Environment environment;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ServiceLoader<RegisterService> serviceLoader = ServiceLoader.load(RegisterService.class);
        if (!serviceLoader.iterator().hasNext()) {
            // 没有通过SPI找到实现，加载Nacos或Zookeeper的实现
            String registerAddress = environment.getProperty(RpcCommonConstants.REGISTER_ADDRESS);
            registerServiceBeanDefinition(registry, registerAddress);
        } else {
            // 通过SPI找到了实现，将其注册到Spring容器
            registerServiceViaSpi(serviceLoader, registry);
        }
    }

    private void registerServiceViaSpi(ServiceLoader<RegisterService> serviceLoader, BeanDefinitionRegistry registry) {
        // 获取SPI的RegisterService实现
        RegisterService registerService = serviceLoader.iterator().next();

        // 创建BeanDefinition
        BeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(registerService.getClass())
                .getBeanDefinition();

        // 注册BeanDefinition到Spring容器
        registry.registerBeanDefinition("spiRegisterService", beanDefinition);
    }

    private void registerServiceBeanDefinition(BeanDefinitionRegistry registry, String registerAddress) {
        try {
            registerReflectiveService(registry, RpcCommonConstants.NACOS_REGISTER_CLASS, registerAddress);
        } catch (Exception e) {
            registerReflectiveService(registry, RpcCommonConstants.ZK_REGISTER_CLASS, registerAddress);
        }
    }

    private void registerReflectiveService(BeanDefinitionRegistry registry, String className, String registerAddress) {
        try {
            Class<?> registerServiceClass = Class.forName(className);
            BeanDefinition beanDefinition =
                    BeanDefinitionBuilder.genericBeanDefinition(registerServiceClass)
                    .getBeanDefinition();
            registry.registerBeanDefinition(className, beanDefinition);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register " + className, e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // No implementation required for this method in this context
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}