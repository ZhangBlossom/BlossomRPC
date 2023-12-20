package blossom.project.rpc.core.register.loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 20:00
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * PollLoadBalance类
 */
public class PollLoadBalance<T> implements LoadBalanceStrategy<T>{

    private AtomicInteger position = new AtomicInteger(1);

    @Override
    public T choose(List<T> instances) {
        return null;
    }
}
