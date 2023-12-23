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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

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
    private final SpringRpcProperties properties;
    private final RegisterService registerService;

    // 保存代理bean的定义信息
    private final Map<String, BeanDefinition> rpcBeanDefinitionCache = new ConcurrentHashMap<>();

    public SpringRpcAutowiredProxyProcessor(SpringRpcProperties properties, RegisterService registerService) {
        this.properties = properties;
        this.registerService = registerService;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * 在Bean工厂配置阶段处理带有@RpcAutowiredProxy注解的字段。
     *  遍历所有的bean定义。
     * 对于每个bean定义，使用反射（ReflectionUtils.doWithFields）来访问它的所有字段。
     * 对每个字段，调用 resolveRpcAutowiredProxy 方法。
     * ReflectionUtils.doWithFields
     * ReflectionUtils.doWithFields 是Spring的一个工具方法，
     * 用于对给定类的所有字段执行某个操作。这个方法接受两个参数：
     * 一个类和一个 FieldCallback。对于类中的每个字段，都会调用这个FieldCallback。
     *
     * ReflectionUtils.doWithFields(clazz, this::resolveRpcAutowiredProxy)
     * 被用来检查每个bean的所有字段，并对每个字段应用
     * resolveRpcAutowiredProxy 方法。
     *
     * resolveRpcAutowiredProxy 方法
     * 这个方法用来处理具有 @RpcAutowiredProxy 注解的字段。
     * 对于这些字段，它创建一个新的 SpringRpcClientProxy bean定义，并将其添加到
     * rpcBeanDefinitionCache（最终注册到Spring容器中）。
     * @param beanFactory Bean工厂
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Stream.of(beanFactory.getBeanDefinitionNames())
                .map(beanFactory::getBeanDefinition)
                .map(BeanDefinition::getBeanClassName)
                .filter(Objects::nonNull)
                .map(className -> ClassUtils.resolveClassName(className, this.classLoader))
                .forEach(clazz -> ReflectionUtils.doWithFields(clazz, this::resolveRpcAutowiredProxy));

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        rpcBeanDefinitionCache.forEach((beanName, beanDefinition) -> {
            if (!context.containsBean(beanName)) {
                registry.registerBeanDefinition(beanName, beanDefinition);
                log.info("registe bean {} successfully...", beanName);
            } else {
                log.warn("Spring Context has contain bean {}", beanName);
            }
        });
    }

    /**
     * 处理带有@RpcAutowiredProxy注解的字段，创建相应的代理Bean定义。
     *
     * @param field 类字段
     */
    private void resolveRpcAutowiredProxy(Field field) {
        Optional.ofNullable(AnnotationUtils
                        .getAnnotation(field, RpcAutowiredProxy.class))
                .ifPresent(rpcAutowiredProxy -> {
                    BeanDefinitionBuilder builder =
                            BeanDefinitionBuilder.genericBeanDefinition(SpringRpcClientProxy.class);
                    builder.setInitMethodName("generateProxy");
                    builder.addPropertyValue("interfaceClass", field.getType());
                    builder.addPropertyValue("registerService", this.registerService);
                    builder.addPropertyValue("registerAddress", properties.getRegisterAddress());
                    builder.addPropertyValue("registerName", properties.getRegisterName());
                    builder.addPropertyValue("loadBalanceStrategy", properties.getLoadBalanceStrategy());
                    builder.addPropertyValue("clientProperties", properties);

                    BeanDefinition beanDefinition = builder.getBeanDefinition();
                    rpcBeanDefinitionCache.put(field.getName(), beanDefinition);
                });
    }
}
