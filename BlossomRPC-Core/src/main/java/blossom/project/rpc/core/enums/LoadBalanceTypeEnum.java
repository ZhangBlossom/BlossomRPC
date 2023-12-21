package blossom.project.rpc.core.enums;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/20 19:56
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * LoadBalanceTypeEnum类
 */
public enum LoadBalanceTypeEnum {

    //轮询
    POLL("POLL"),
    //权重
    WEIGHT("WEIGHT"),
    //负载均衡
    RANDOM("RANDOM");
    private String name;


    LoadBalanceTypeEnum(String name) {
        this.name = name;
    }

    public static LoadBalanceTypeEnum findByName(String name) {
        for (LoadBalanceTypeEnum type : LoadBalanceTypeEnum.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
