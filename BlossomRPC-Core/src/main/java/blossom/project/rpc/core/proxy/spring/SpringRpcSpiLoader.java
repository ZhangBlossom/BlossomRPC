package blossom.project.rpc.core.proxy.spring;

import blossom.project.rpc.common.constants.RpcCommonConstants;
import blossom.project.rpc.common.loadbalance.LoadBalanceStrategy;
import blossom.project.rpc.common.loadbalance.PollLoadBalance;
import blossom.project.rpc.common.register.RegisterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/23 10:11
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcSpiLoader类
 */
@Configuration
public class SpringRpcSpiLoader {

    @Bean
    public Boolean spiCondition() {
        ServiceLoader<RegisterService> serviceLoader = ServiceLoader.load(RegisterService.class);
        return serviceLoader.iterator().hasNext();
    }

    @Primary
    @Bean(name = "spiRegisterService")
    @ConditionalOnBean(name = "spiCondition")
    public RegisterService spiRegisterService() throws Exception {
        ServiceLoader<RegisterService> serviceLoader = ServiceLoader.load(RegisterService.class);
        RegisterService registerService = serviceLoader.iterator().hasNext() ? serviceLoader.iterator().next() : null;
        if (Objects.isNull(registerService)) {
            try {
                String serverAddress = "localhost:8848"; // 从配置中读取Nacos服务器地址
                Class<?> nacosRegisterServiceClass = Class.forName(RpcCommonConstants.NACOS_REGISTER_CLASS);
                Constructor<?> constructor = nacosRegisterServiceClass.getConstructor(String.class,
                        LoadBalanceStrategy.class);
                return (RegisterService) constructor.newInstance(serverAddress, new PollLoadBalance<>());
            } catch (Exception e) {
                throw e;
            }
        } else {
            return registerService;
        }
    }

}
