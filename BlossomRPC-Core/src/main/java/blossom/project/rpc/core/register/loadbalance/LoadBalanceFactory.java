package blossom.project.rpc.core.register.loadbalance;

import blossom.project.rpc.core.enums.LoadBalanceTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 19:56
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * LoadBalanceFactory类
 */
public class LoadBalanceFactory {

    private static Map<LoadBalanceTypeEnum,LoadBalanceStrategy>
                    LOADBALANCE_FACTORY = new HashMap<>();

    static {
        LOADBALANCE_FACTORY.put(LoadBalanceTypeEnum.POLL,new PollLoadBalance());
        LOADBALANCE_FACTORY.put(LoadBalanceTypeEnum.RANDOM,new RandomLoadBalance());
        LOADBALANCE_FACTORY.put(LoadBalanceTypeEnum.WEIGHT,new WeightLoadBalance());
    }

    private LoadBalanceFactory(){}

    public static LoadBalanceStrategy getLoadBalanceStrategy(LoadBalanceTypeEnum typeEnum){
        return LOADBALANCE_FACTORY.getOrDefault(typeEnum,new PollLoadBalance());
    }

}
