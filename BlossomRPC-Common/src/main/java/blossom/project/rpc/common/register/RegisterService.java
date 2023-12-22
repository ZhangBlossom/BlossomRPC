package blossom.project.rpc.common.register;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/19 23:31
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RegistryService接口
 */
public interface RegisterService {

    default void init(){}
    void register(RpcServiceInstance instance);

    default void unregister(RpcServiceInstance instance){}

    RpcServiceInstance discovery(RpcServiceInstance instance);
}
