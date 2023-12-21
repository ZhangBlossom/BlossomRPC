package blossom.project.rpc.core.register.nacos;

import blossom.project.rpc.core.enums.LoadBalanceTypeEnum;
import blossom.project.rpc.core.register.RegisterService;
import blossom.project.rpc.core.register.RpcServiceInstance;
import blossom.project.rpc.core.register.loadbalance.LoadBalanceFactory;
import blossom.project.rpc.core.register.loadbalance.LoadBalanceStrategy;
import blossom.project.rpc.core.register.loadbalance.PollLoadBalance;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/19 23:46
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * NacosRegisterService类
 */
@Slf4j
public class NacosRegisterService implements RegisterService {

    private NamingService namingService;

    private LoadBalanceStrategy<Instance> loadBalanceStrategy
             = new PollLoadBalance<>();


    public NacosRegisterService(String serverAddress) {
        try {
            this.namingService = NamingFactory.createNamingService(serverAddress);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    public NacosRegisterService(String serverAddress, LoadBalanceTypeEnum typeEnum) {
        try {
            this.namingService = NamingFactory.createNamingService(serverAddress);
            this.loadBalanceStrategy = LoadBalanceFactory.getLoadBalanceStrategy(typeEnum);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void register(RpcServiceInstance instance) {
        if (Objects.isNull(instance)) {
            log.info("the Reigster Service Info can not be null!!!");
            return;
        }
        log.info("start to register instance to Nacos: {}",instance);
        try {
            //注册服务  服务名称：blossom.project.rpc.core.service.RpcService
            namingService.registerInstance(instance.getServiceName(), instance.getServiceIp(),
                    instance.getServicePort());
        } catch (NacosException e) {
            log.error("register the ServiceInstance to Nacos failed!!!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unregister(RpcServiceInstance instance) {
        if (Objects.isNull(instance)) {
            log.info("the Reigster Service Info can not be null!!!");
            return;
        }
        log.info("start to unregister instance to Nacos: {}",instance);
        try {
            //进行服务注销
            namingService.deregisterInstance(instance.getServiceName(), instance.getServiceIp(),
                    instance.getServicePort());
        } catch (NacosException e) {
            log.error("unregister the ServiceInstance from Nacos failed!!!");
            throw new RuntimeException(e);
        }

    }

    @Override
    public RpcServiceInstance discovery(RpcServiceInstance instance) {
        try {
            List<Instance> instances = namingService.selectInstances(instance.getServiceName(),
                    instance.getGroupName(), true);
            Instance rpcInstance = loadBalanceStrategy.choose(instances);
            if (Objects.nonNull(rpcInstance)) {


                return RpcServiceInstance.builder()
                        .serviceIp(rpcInstance.getIp())
                        .servicePort(rpcInstance.getPort())
                        .serviceName(rpcInstance.getServiceName())
                        .build();
            }
            return null;
        } catch (NacosException e) {
            log.error("discovery the ServiceInstance from Nacos failed!!!");
            throw new RuntimeException(e);
        }
    }


}
