package blossom.project.rpc.server;

import blossom.project.rpc.common.register.RegisterService;
import blossom.project.rpc.common.register.RpcServiceInstance;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/22 20:51
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * NacosRegisterServicePlus类
 */
@Slf4j
public class NacosRegisterServicePlus implements RegisterService {


    @Override
    public void register(RpcServiceInstance instance) {

    }

    @Override
    public RpcServiceInstance discovery(RpcServiceInstance instance) {
        return null;
    }
}
