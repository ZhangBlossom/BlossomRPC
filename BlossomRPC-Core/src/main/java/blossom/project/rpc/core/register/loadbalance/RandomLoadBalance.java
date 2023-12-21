package blossom.project.rpc.core.register.loadbalance;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 20:00
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * PollLoadBalance类
 */
public class RandomLoadBalance<T> implements LoadBalanceStrategy<T>{

    @Override
    public T choose(List<T> instances) {
        if (CollectionUtils.isEmpty(instances)){
            return null;
        }
        return instances.get(0);
    }
}
