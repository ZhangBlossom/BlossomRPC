package blossom.project.rpc.server.controller;

import blossom.project.rpc.api.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/21 16:39
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * TestServerController类
 */
@RestController
public class TestServerController {
    @Autowired
    private TestService testService;


    @GetMapping("/server")
    public String test(){
        return testService.test("hello");
    }
}
