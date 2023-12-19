package blossom.project.rpc.core.proxy.spring.rpcmethod;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/18 22:04
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RpcServiceMethodInfo类
 * 对当前方法用于存储RpcService的方法信息
 */
@Data
public class RpcServiceMethod {

    /**
     * 自定义规范：调用RPC的时候通过
     * 类路径 + 方法名称的方式去注册中心得到方法
     */
    private Object methodPath;

    /**
     * 实际被调用方法
     */
    private Method method;
}
