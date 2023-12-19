package blossom.project.rpc.core.proxy.spring.annotation;
/**
 * @Author:Serendipity
 * @Date:
 * @Description:
 */

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/18 19:20
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RpcServiceInfo注解
 * 当前注解的作用就在于加载所有需要被声明出去的Service服务
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service//因为要对外暴露的服务肯定都被spring管理了，直接再这个注解上面加Service注解即可
public @interface RpcServiceDeclaration {
    //TODO 扩展点...
}
