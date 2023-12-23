package blossom.project.rpc.core.proxy.spring;

import blossom.project.rpc.common.constants.RpcCommonConstants;
import blossom.project.rpc.common.loadbalance.LoadBalanceStrategy;
import blossom.project.rpc.common.loadbalance.PollLoadBalance;
import blossom.project.rpc.common.register.RegisterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

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

//@Configuration
@Deprecated
public class SpringRpcSpiLoader implements EnvironmentAware {

    private String registerAddress;

    @Override
    public void setEnvironment(Environment environment) {
        String registerAddress = environment.getProperty("blossom.rpc.registerAddress");
        this.registerAddress = registerAddress;
    }

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
                Class<?> nacosRegisterServiceClass = Class.forName(RpcCommonConstants.NACOS_REGISTER_CLASS);
                Constructor<?> constructor = nacosRegisterServiceClass.getConstructor(String.class,
                        LoadBalanceStrategy.class);
                return (RegisterService) constructor.newInstance(registerAddress, new PollLoadBalance<>());
            } catch (Exception e) {
                try {

                    Class<?> zkRegisterServiceClass = Class.forName(RpcCommonConstants.ZK_REGISTER_CLASS);
                    Constructor<?> constructor =
                            zkRegisterServiceClass.getConstructor(String.class, LoadBalanceStrategy.class);
                    return (RegisterService) constructor.newInstance(registerAddress, new PollLoadBalance<>());
                } catch (Exception zkException) {
                    throw new RuntimeException("You neither implement the RegisterService interface nor use registry " +
                            "dependencies");
                }
            }
        } else {
            return registerService;
        }
    }

}
