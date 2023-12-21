package blossom.project.rpc.server.service.impl;

import blossom.project.rpc.api.TestService;
import blossom.project.rpc.core.proxy.spring.annotation.RpcServiceDeclaration;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/21 13:52
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * TestServiceImpl类
 */
@RpcServiceDeclaration
public class TestServiceImpl implements TestService {
    @Override
    public String test(String info) {
        return "test service successfully:"+info;
    }
}
