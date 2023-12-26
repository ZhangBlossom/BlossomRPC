package blossom.project.rpc.core.proxy.spring.client;

import blossom.project.rpc.common.register.RegisterService;
import blossom.project.rpc.core.proxy.spring.SpringRpcProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.aop.framework.ProxyFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import blossom.project.rpc.core.proxy.spring.annotation.RpcAutowiredProxy;
import java.lang.reflect.Field;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/19 21:56
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcAutowiredProxyProcessorGentle类
 * Spring Bean后置处理器，用于处理RpcAutowiredProxy注解标记的字段。
 */
@Slf4j
public class SpringRpcAutowiredProxyProcessorGentle
        implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    private final RegisterService registerService;

    public SpringRpcAutowiredProxyProcessorGentle(RegisterService registerService) {
        this.registerService = registerService;
    }


    /**
     * 在bean初始化之前执行的操作，用于处理RpcAutowiredProxy注解。
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        // 遍历Bean的所有字段，查找RpcAutowiredProxy注解
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            RpcAutowiredProxy rpcAutowiredProxy = field.getAnnotation(RpcAutowiredProxy.class);
            if (rpcAutowiredProxy != null) {
                // 如果存在RpcAutowiredProxy注解，创建代理对象
                ProxyFactory proxyFactory = new ProxyFactory();
                //设定代理对象的类型
                proxyFactory.setTargetClass(field.getType());
                //ProxyFactory 用于创建代理对象。我们为其设置目标类，
                // 并添加一个MethodInterceptor，这样每次方法调用都会经过这个拦截器。
                proxyFactory.addAdvice((MethodInterceptor) invocation ->
                        new JdkRpcProxyInvocationHandler(registerService)
                                .invoke(invocation.getThis(), invocation.getMethod(), invocation.getArguments())
                );
                // 获取代理对象并设置到字段
                Object proxy = proxyFactory.getProxy();
                field.setAccessible(true);
                field.set(bean, proxy);
            }
        });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 初始化后的逻辑可以在这里实现
        return bean;
    }
}