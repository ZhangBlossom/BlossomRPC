package blossom.project.rpc.core.proxy.spring.rpcmethod;

import blossom.project.rpc.core.entity.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/18 22:10
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RpcServiceMethodCache类
 */
public class RpcServiceMethodCache {

    /**
     * rpc方法cache
     * 规则：使用 class.getClass()+"."+methodName的方式保存方法路径
     */
    public static Map<String, RpcServiceMethod> METHOD_CACHE =new ConcurrentHashMap<>();

    /**
     * 使用饿汉式单例
     */
    private static RpcServiceMethodCache INSTANCE = new RpcServiceMethodCache();

    private RpcServiceMethodCache(){}

    public static RpcServiceMethodCache getInstance(){
        return INSTANCE;
    }

    public Object processor(RpcRequest request){
        String key=request.getClassName()+"."+request.getMethodName();
        RpcServiceMethod rpcServiceMethod= METHOD_CACHE.get(key);
        if(Objects.isNull(rpcServiceMethod)){
            return null;
        }
        Object methodPath =rpcServiceMethod.getMethodPath();
        Method method=rpcServiceMethod.getMethod();
        try {
            return method.invoke(methodPath,request.getParams());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
