package blossom.project.rpc.nacos;

import blossom.project.rpc.common.loadbalance.PollLoadBalance;
import com.alibaba.boot.nacos.discovery.autoconfigure.NacosDiscoveryAutoConfiguration;
import com.alibaba.boot.nacos.discovery.properties.NacosDiscoveryProperties;
import com.alibaba.boot.nacos.discovery.properties.Register;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@ConditionalOnBean(NacosDiscoveryProperties.class)
public class RPCNacosAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    Logger logger = LoggerFactory.getLogger(RPCNacosAutoConfiguration.class);

    /**
     * 这个bean只会在存在nacos的依赖的时候才会创建
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public NacosRegisterService nacosRegisterService() {
        NacosDiscoveryProperties nacosDiscoveryProperties = applicationContext.getBean(NacosDiscoveryProperties.class);
        Register register = nacosDiscoveryProperties.getRegister();
        if (StringUtils.isBlank(register.getIp())){
            logger.error("nacos connect info is not be null,please check config on nacos");
        }
        String nacosAddress = String.format("%s:%s",register.getIp(),register.getPort());
        //
        NacosRegisterService nacosRegisterService = new NacosRegisterService(nacosAddress,new PollLoadBalance());
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
}