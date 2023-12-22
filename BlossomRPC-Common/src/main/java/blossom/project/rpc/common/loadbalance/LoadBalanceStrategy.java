package blossom.project.rpc.common.loadbalance;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 19:52
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * LoadBalanceStrategy类
 */
public interface LoadBalanceStrategy<T> {

    /**
     * 从实力列表中返回一个实例
     * @param instances
     * @return
     */
    T choose(List<T>instances);
}
