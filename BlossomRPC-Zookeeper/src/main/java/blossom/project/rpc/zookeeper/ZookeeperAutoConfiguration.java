package blossom.project.rpc.zookeeper;

import blossom.project.rpc.common.constants.RpcCommonConstants;
import blossom.project.rpc.common.register.RegisterService;
import blossom.project.rpc.common.loadbalance.LoadBalanceStrategy;
import blossom.project.rpc.common.loadbalance.PollLoadBalance;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
@Conditional(OnZookeeperClientClassCondition.class)
public class ZookeeperAutoConfiguration {

    /**
     * 这个bean只会在存在nacos的依赖的时候才会创建
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RegisterService zookeeperRegisterService() {
        // 获取Nacos相关配置，例如服务器地址等
        String serverAddress = "localhost:8848"; // 从配置中读取zk服务器地址
        // ... 其他所需配置

        try {
            // 使用反射创建NamingService实例
            //Class<?> namingFactoryClass =
            //        Class.forName("com.alibaba.nacos.api.naming.NamingFactory");
            //Method createNamingServiceMethod =
            //        namingFactoryClass.getMethod("createNamingService", String.class);
            //Object namingServiceInstance = createNamingServiceMethod.invoke(null, serverAddress);

            // 创建NacosRegisterService实例
            Class<?> zkRegisterServiceClass = Class.forName(RpcCommonConstants.ZK_REGISTER_CLASS);
            Constructor<?> constructor = zkRegisterServiceClass.getConstructor(String.class, LoadBalanceStrategy.class);
            return (RegisterService) constructor.newInstance(serverAddress,new PollLoadBalance<>());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new IllegalStateException("Failed to create NacosRegisterService", e);
        }
    }
}