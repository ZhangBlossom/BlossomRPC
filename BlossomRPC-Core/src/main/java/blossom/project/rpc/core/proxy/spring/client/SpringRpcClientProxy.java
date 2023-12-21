package blossom.project.rpc.core.proxy.spring.client;

import blossom.project.rpc.core.enums.RegisterTypeEnum;
import blossom.project.rpc.core.register.RegisterFactory;
import blossom.project.rpc.core.register.RegisterService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 22:19
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * SpringRpcClientProxy类
 */
public class SpringRpcClientProxy implements
        FactoryBean<Object>, InitializingBean {

    private Object object;
    /*   private String serviceAddress;
       private int servicePort;*/
    private Class<?> interfaceClass;

    private String registerAddress;

    private String registerName;

    private SpringRpcClientProperties clientProperties;


    @Override
    public Object getObject() throws Exception {
        return object;
    }

    /**
     * 当前方法用于动态生成代理类
     * 需要在注入有注解的属性的时候调用这个方法
     *
     */
    public void generateProxy(){
        RegisterService registryService= RegisterFactory.createRegisterService(
                clientProperties.getRegisterAddress(),
                RegisterTypeEnum.findByName(clientProperties.getRegisterName()));
        this.object= Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new JdkRpcProxyInvocationHandler(registryService));
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(clientProperties);
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public SpringRpcClientProperties getClientProperties() {
        return clientProperties;
    }

    public void setClientProperties(SpringRpcClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }
}
