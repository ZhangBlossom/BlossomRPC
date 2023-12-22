package blossom.project.rpc.zookeeper;

import blossom.project.rpc.common.RegisterService;
import blossom.project.rpc.common.RpcServiceInstance;
import blossom.project.rpc.common.loadbalance.LoadBalanceStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
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
public class ZookeeperRegisterService implements RegisterService {

    private static final String REGISTRY_PATH = "/rpc_registry";

    /**
     * zk注册中心
     */
    private final ServiceDiscovery<RpcServiceInstance> serviceDiscovery;

    private LoadBalanceStrategy<ServiceInstance<RpcServiceInstance>> loadBalanceStrategy;

    public ZookeeperRegisterService(String serverAddress,LoadBalanceStrategy loadBalanceStrategy) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
                .newClient(serverAddress,
                        new ExponentialBackoffRetry(2000, 3));
        client.start();
        JsonInstanceSerializer<RpcServiceInstance> serializer = new JsonInstanceSerializer<>(RpcServiceInstance.class);
        this.serviceDiscovery =
                ServiceDiscoveryBuilder.builder(RpcServiceInstance.class)
                        .client(client)
                        .serializer(serializer)
                        .basePath(REGISTRY_PATH)
                        .build();
        this.serviceDiscovery.start();
        this.loadBalanceStrategy = loadBalanceStrategy;
    }

    @Override
    public void register(RpcServiceInstance instance) {
        if (Objects.isNull(instance)) {
            log.info("the Reigster Service Info can not be null!!!");
            return;
        }
        log.info("start to register instance to Zookeeper: {}",instance);
        try {
            ServiceInstance<RpcServiceInstance> serviceInstance =
                    ServiceInstance.<RpcServiceInstance>builder()
                            .name(instance.getServiceName()).address(instance.getServiceIp()).port(instance.getServicePort()).payload(instance).build();
            this.serviceDiscovery.registerService(serviceInstance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RpcServiceInstance discovery(RpcServiceInstance instance) {
        Collection<ServiceInstance<RpcServiceInstance>> serviceInstances = null;
        try {
            serviceInstances = this.serviceDiscovery.queryForInstances(instance.getServiceName());
            ServiceInstance<RpcServiceInstance> serviceInstance =
                    this.loadBalanceStrategy.choose((List<ServiceInstance<RpcServiceInstance>>) serviceInstances);
            if (serviceInstance != null) {
                return serviceInstance.getPayload();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
