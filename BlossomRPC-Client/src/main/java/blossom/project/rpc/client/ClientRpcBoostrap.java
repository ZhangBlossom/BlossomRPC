package blossom.project.rpc.client;

import blossom.project.rpc.client.controller.BlossomRpcController;
import blossom.project.rpc.core.proxy.spring.client.RpcClientProxyFactory;
import blossom.project.rpc.core.service.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/16 15:48
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * ClientRpcBoostrap类
 * client端的启动类
 */
@SpringBootApplication(scanBasePackages = "blossom.project")
@Slf4j
public class ClientRpcBoostrap {
    public static void main(String[] args) {

        //RpcClientProxyFactory proxy = new RpcClientProxyFactory();
        //RpcService clientProxy = proxy.getClientProxy
        //        (RpcService.class, "192.168.2.6", 8080);
        //System.out.println(clientProxy.testRpcRequest("hello"));

        ConfigurableApplicationContext context = SpringApplication.run(ClientRpcBoostrap.class, args);
        BlossomRpcController blossomRpcController = context.getBean(BlossomRpcController.class);
        if (Objects.isNull(blossomRpcController)){
            log.error("the controller does not be load");
            System.exit(-1);
        }
    }
}
