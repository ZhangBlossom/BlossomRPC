package blossom.project.rpc.zookeeper;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


/**
 * @author: ZhangBlossom
 * @date: 2023/12/22 18:50
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * NacosAutoConfiguration类
 */

public class OnZookeeperClientClassCondition implements Condition {

    private static final String ZK_DISCOVERY_CLASS = "org.apache.curator.x.discovery.ServiceDiscovery";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            Class.forName(ZK_DISCOVERY_CLASS);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
