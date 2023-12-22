package blossom.project.rpc.nacos;

import blossom.project.rpc.common.loadbalance.PollLoadBalance;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/22 18:50
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * NacosAutoConfiguration类
 */

//@Configuration
//@ConditionalOnClass(NamingFactory.class)
public class RpcNacosAutoConfiguration implements ApplicationContextAware, EnvironmentAware {

    private Environment environment;

    Logger logger = LoggerFactory.getLogger(RpcNacosAutoConfiguration.class);
    private ApplicationContext applicationContext;


    /**
     * 这个bean只会在存在nacos的依赖的时候才会创建
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public NacosRegisterService nacosRegisterService() {
        String serverAddr = environment.getProperty("nacos.discovery.server-addr");
        if (StringUtils.isBlank(serverAddr)){
            logger.error("nacos server address could not be null");
        }
        //
        NacosRegisterService nacosRegisterService = new NacosRegisterService(serverAddr,new PollLoadBalance());
        return nacosRegisterService;
        //        // 获取Nacos相关配置，例如服务器地址等
//        String serverAddress = "localhost:8848"; // 从配置中读取Nacos服务器地址
//        // ... 其他所需配置
//
//        try {
//            // 使用反射创建NamingService实例
//            //Class<?> namingFactoryClass =
//            //        Class.forName("com.alibaba.nacos.api.naming.NamingFactory");
//            //Method createNamingServiceMethod =
//            //        namingFactoryClass.getMethod("createNamingService", String.class);
//            //Object namingServiceInstance = createNamingServiceMethod.invoke(null, serverAddress);
//
//            // 创建NacosRegisterService实例
//            Class<?> nacosRegisterServiceClass = Class.forName("blossom.project.rpc.nacos.NacosRegisterService");
//            Constructor<?> constructor = nacosRegisterServiceClass.getConstructor(String.class, LoadBalanceStrategy.class);
//            return (RegisterService) constructor.newInstance(serverAddress,new PollLoadBalance<>());
//        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException |
//                 InvocationTargetException e) {
//            throw new IllegalStateException("Failed to create NacosRegisterService", e);
//        }
        //获取nacos配置
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}