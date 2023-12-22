package blossom.project.netty.serviceload;

import blossom.project.rpc.common.register.RegisterService;
import jdk.jshell.spi.SPIResolutionException;
import lombok.extern.slf4j.Slf4j;

import java.util.ServiceLoader;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/22 20:47
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RegisterServiceLoad类
 */
@Slf4j
public class RegisterServiceLoad {
    private RegisterService registerService;

    public void loadRegisterService(){
        ServiceLoader<RegisterService> registerServices =
                ServiceLoader.<RegisterService>load(RegisterService.class);
        this.registerService = registerServices.findFirst().orElseThrow(() -> {
            log.error("the Register Service Impl can not be null!!!!");
            return new RuntimeException("meet the spi exception");
        });
        registerService.init();
    }
}
