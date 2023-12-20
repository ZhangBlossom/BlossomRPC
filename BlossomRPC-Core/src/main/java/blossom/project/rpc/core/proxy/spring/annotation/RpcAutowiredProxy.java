package blossom.project.rpc.core.proxy.spring.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 20:54
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RpcAutowiredProxy类
 * 当前注解在客户端使用
 * 客户端在需要进行rpc代理的类上添加该注解
 * 那么使用当前类发送请求的时候就会走代理
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD,ElementType.TYPE})
@Autowired
public @interface RpcAutowiredProxy {
}
