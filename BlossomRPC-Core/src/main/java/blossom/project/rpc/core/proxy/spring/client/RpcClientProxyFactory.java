package blossom.project.rpc.core.proxy.spring.client;

import blossom.project.rpc.common.register.RegisterService;

import java.lang.reflect.Proxy;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/16 22:17
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RpcClientProxy类
 * 客户端代理类 用于获取客户端代理类
 */

//TODO 后续考虑由spring管理，使得项目确定注册中心之后，没必要重复传参
public class RpcClientProxyFactory {
    /**
     * 当前方法用于返回需要调用的接口的客户端代理对象
     * @param interfaceName
     * @param host
     * @param port
     * @return
     * @param <T>
     */
    @Deprecated
    public <T> T getClientProxy(final Class<T> interfaceName, final String host, int port) {
        return (T) Proxy.newProxyInstance(interfaceName.getClassLoader(),
                new Class<?>[]{interfaceName},
                new JdkRpcProxyInvocationHandler(host, port));
    }

    /**
     * 当前方法就是用来返回调用rpc服务的代理对象
     * @param interfaceName 被代理接口名称
     * @param registerService 注册中心
     * @return
     * @param <T>
     */
    public <T> T getClientProxy(final Class<T> interfaceName, RegisterService registerService) {
        return (T) Proxy.newProxyInstance(interfaceName.getClassLoader(),
                new Class<?>[]{interfaceName},
                new JdkRpcProxyInvocationHandler(registerService));
    }
}
