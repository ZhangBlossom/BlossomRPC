package blossom.project.rpc.client.controller;

import blossom.project.rpc.api.TestService;
import blossom.project.rpc.core.proxy.spring.annotation.RpcAutowiredProxy;
import blossom.project.rpc.core.service.RpcService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/16 22:13
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * BlossomRpcController类
 */
@RestController
public class BlossomRpcController {

    //TODO 应该扫描所有有这个注解的属性
    //并且进行动态代理

    @RpcAutowiredProxy
    private RpcService rpcService;


    @RpcAutowiredProxy
    private TestService testService;

    @GetMapping("/rpc")
    public String test(){
        //rpcService.testRpcRequest("test info");
        return testService.test("test info");
    }
}
