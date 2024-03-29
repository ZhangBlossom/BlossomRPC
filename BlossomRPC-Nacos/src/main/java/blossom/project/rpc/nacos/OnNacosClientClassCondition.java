package blossom.project.rpc.nacos;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import static blossom.project.rpc.common.constants.RpcCommonConstants.NACOS_NAMING_CLASS;


/**
 * @author: ZhangBlossom
 * @date: 2023/12/22 18:50
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * NacosAutoConfiguration类
 */

public class OnNacosClientClassCondition implements Condition {

    /**
     * 存在有nacos的namingservice的时候才会使用nacos
     */

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            Class.forName(NACOS_NAMING_CLASS);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
