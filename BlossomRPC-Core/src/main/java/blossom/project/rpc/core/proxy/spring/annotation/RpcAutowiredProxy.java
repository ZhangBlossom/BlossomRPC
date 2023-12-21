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

//注意RUNTIME和CLASS的区别
//当注解的保留策略设置为 RUNTIME 时，注解不仅被保留在编译后的.class文件中，
// 而且在运行时通过反射机制仍然可见。这意味着这类注解可以在程序运行时被读取和查询。

//当注解的保留策略设置为 CLASS 时，注解被保留在编译后的.class文件中，
// 但在运行时不再可见。这意味着虚拟机在运行时不会保留这些注解，因此它们不能被反射机制读取。
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Autowired
public @interface RpcAutowiredProxy {
}
