package blossom.project.rpc.core.proxy.spring.server;

import blossom.project.rpc.core.proxy.spring.annotation.RpcServiceDeclaration;
import blossom.project.rpc.core.proxy.spring.rpcmethod.RpcServiceMethod;
import blossom.project.rpc.core.proxy.spring.rpcmethod.RpcServiceMethodCache;
import blossom.project.rpc.core.starter.NettyRpcServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/18 22:01
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcServiceLoader类
 * 1：当前类可以考虑整合Spring的InitializingBean然后用来启动Netty服务器
 * 2：当前类得考虑将IOC容器中的所有被特定注解管理的方法注册到注册中心去
 */
@Slf4j
public class SpringRpcProcessor implements InitializingBean,BeanPostProcessor {
    private final String serverAddress;
    private final int serverPort;

    public SpringRpcProcessor(int serverPort) throws UnknownHostException {
        //this.serverPort= serverPort;
        //this.registryService=registryService;

        InetAddress address=InetAddress.getLocalHost();
        this.serverAddress=address.getHostAddress();

        this.serverPort = serverPort;
    }

    /**
     * 再当前类完成加载之后
     * 启动netty-server端
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(()->{
            log.info("startup the NettyRpcServer...");
            new NettyRpcServer(serverAddress,serverPort).start();
        }).start();
    }


    /**
     * 当前ServiceMethodLoader的作用应该是加载我们项目中所有的被注解管理的Service服务中的所有方法
     * 1:我需要一个容器，当前容器的作用是存储--》类+方法的字符串路径：方法
     * 2:需要配合注册中心保存这些方法的地址
     * 3：...
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //扫描存在当前注解的所有方法
        if(bean.getClass().isAnnotationPresent(RpcServiceDeclaration.class)){
            Method[] methods=bean.getClass().getDeclaredMethods();
            for(Method method:methods){
                String serviceName=bean.getClass().getInterfaces()[0].getName();
                String key = serviceName+"."+method.getName();
                RpcServiceMethod rpcServiceMethod=new RpcServiceMethod();
                rpcServiceMethod.setMethodPath(bean);
                rpcServiceMethod.setMethod(method);
                RpcServiceMethodCache.METHOD_CACHE.put(key,rpcServiceMethod);

                //ServiceInfo serviceInfo=new ServiceInfo();
                //serviceInfo.setServiceAddress(this.serverAddress);
                //serviceInfo.setServicePort(this.serverPort);
                //serviceInfo.setServiceName(serviceName);
                try {
                    //registryService.register(serviceInfo); //住的服务
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("register serivce {} failed",serviceName,e);
                }
            }
        }
        return bean;
    }
}
