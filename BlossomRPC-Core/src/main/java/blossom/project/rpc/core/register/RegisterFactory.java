package blossom.project.rpc.core.register;

import blossom.project.rpc.core.enums.RegisterTypeEnum;
import blossom.project.rpc.core.register.loadbalance.LoadBalanceStrategy;
import blossom.project.rpc.core.register.nacos.NacosRegisterService;
import blossom.project.rpc.core.register.zk.ZookeeperRegisterService;

import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/19 23:35
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RegisterFactory类
 *
 * 1: 我们在使用nacos作为注册中心的时候会引入nacos-discovery依赖
 * 可以看看这个依赖中有什么特别的类 作为conditonal的条件
 *
 */
public class RegisterFactory  {

    //TODO 使用spring的后置afterProperties方法/bean工厂判断是否存在有nacos/zk等依赖
    //private static  Map<String,Object> REGISTER_CACHE = new ConcurrentHashMap<>();


    public static RegisterService createRegisterService(String registerAddress,
                                                        RegisterTypeEnum registryType,
                                                        LoadBalanceStrategy loadBalanceStrategy){
        RegisterService registerService=null;
        try{
            switch (registryType){
                case NACOS:
                    registerService=new NacosRegisterService(registerAddress,loadBalanceStrategy);
                    break;
                case ZOOKEEPER:
                    registerService=new ZookeeperRegisterService(registerAddress,loadBalanceStrategy);
                    break;
                default:
                    registerService=new NacosRegisterService(registerAddress,loadBalanceStrategy);
                    break;
            }
            if (Objects.isNull(registerService)){
                throw new Exception("the Registere Service can not be null!!!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return registerService;
    }


}
