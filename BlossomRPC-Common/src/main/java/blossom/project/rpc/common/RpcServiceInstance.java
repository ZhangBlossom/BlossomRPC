package blossom.project.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/19 23:53
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RpcServiceInfo类
 * 仿照nacos的Instance进行编写
 * 存储服务实例信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcServiceInstance {

    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务ip
     */
    private String serviceIp;
    /**
     * 服务端口
     */
    private int servicePort;
    /**
     * nacos会用到env信息
     */
    private String groupName = RpcCommonConstants.DEFAULT_GROUP;

    /**
     * 元数据信息
     */
    private Map<String,String> metadata = new ConcurrentHashMap<>();

}
