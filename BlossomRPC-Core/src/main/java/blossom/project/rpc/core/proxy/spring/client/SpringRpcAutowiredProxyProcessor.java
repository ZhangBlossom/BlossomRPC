package blossom.project.rpc.core.proxy.spring.client;

import blossom.project.rpc.common.register.RegisterService;
import blossom.project.rpc.core.proxy.spring.SpringRpcProperties;
import blossom.project.rpc.core.proxy.spring.annotation.RpcAutowiredProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 21:00
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcAutowiredProxyProstProcessor类
 * 当前类应该做到如下的事情
 * 1: 得到所有RpcAutowiredProxy注解的属性，对这些属性进行代理修改
 */
@Slf4j
public class SpringRpcAutowiredProxyProcessor implements
        ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {
    private ApplicationContext context;
    private ClassLoader classLoader;
    private SpringRpcProperties properties;

    private RegisterService registerService;

    public SpringRpcAutowiredProxyProcessor(SpringRpcProperties properties) {
        this.properties = properties;
    }
    public SpringRpcAutowiredProxyProcessor(SpringRpcProperties properties, RegisterService registerService) {
        this.properties = properties;
        this.registerService = registerService;
    }


    //保存发布的引用bean的信息
    private final Map<String, BeanDefinition> rpcBeanDefinitionCache = new ConcurrentHashMap<>();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * TODO 这个方法需要实现在得到beandefinition之后
     * 对这个bf进行重新的操作代理 使得得到的是代理对象
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //TODO 使用stream流优化
        for (String beanDefinitionname : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition =
                    beanFactory.getBeanDefinition(beanDefinitionname);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.classLoader);
                ReflectionUtils.doWithFields(clazz, this::resolveRpcAutowiredProxy);
            }
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        this.rpcBeanDefinitionCache.forEach((beanName, beanDefinition) -> {
            if (context.containsBean(beanName)) {
                log.warn("Spring Context has already contains bean {}", beanName);
                return;
            }
            registry.registerBeanDefinition(beanName, beanDefinition);
            log.info("register the beanName:{} successfully...",beanName);
        });
    }

    private void resolveRpcAutowiredProxy(Field field) {
        RpcAutowiredProxy rpcAutowiredProxy =
                AnnotationUtils.getAnnotation(field, RpcAutowiredProxy.class);
        if (rpcAutowiredProxy != null) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.
                    genericBeanDefinition(SpringRpcClientProxy.class);
            builder.setInitMethodName("generateProxy");
            builder.addPropertyValue("interfaceClass", field.getType());
            builder.addPropertyValue("registerService", this.registerService);
            builder.addPropertyValue("registerAddress", properties.getRegisterAddress());
            builder.addPropertyValue("registerName", properties.getRegisterName());
            builder.addPropertyValue("loadBalanceStrategy", properties.getLoadBalanceStrategy());
            builder.addPropertyValue("clientProperties", properties);

            BeanDefinition beanDefinition = builder.getBeanDefinition();
            rpcBeanDefinitionCache.put(field.getName(), beanDefinition);
        }
    }
}