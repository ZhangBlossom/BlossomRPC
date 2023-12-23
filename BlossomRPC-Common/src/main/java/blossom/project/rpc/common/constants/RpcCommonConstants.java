package blossom.project.rpc.common.constants;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/16 16:57
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RpcCommonConstants类用于提供当前项目所有的常量
 */
public interface RpcCommonConstants {
    //当前RPC版本 先从1开始
    byte VERSION_ID = 0x01;

    //请求头长度
    int HEADER_LENGTH = 15;
    /**
     * nacos 默认分组名称
     */
    String DEFAULT_GROUP = "DEFAULT_GROUP";
    /**
     * nacos默认名称中心
     */
    String namespace = "public";

    String NACOS_NAMING_CLASS = "com.alibaba.nacos.api.naming.NamingFactory";
    String NACOS_REGISTER_CLASS = "blossom.project.rpc.nacos.NacosRegisterService";


    String ZK_DISCOVERY_CLASS = "org.apache.curator.x.discovery.ServiceDiscovery";
    String ZK_REGISTER_CLASS = "blossom.project.rpc.zookeeper.ZookeeperAutoConfiguration";

}
