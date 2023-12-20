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
    POLL((byte)0),
    //权重
    WEIGHT((byte)1),
    //负载均衡
    RANDOM((byte)2);
    private byte code;

    LoadBalanceTypeEnum(byte code){
        this.code=code;
    }

    public static LoadBalanceTypeEnum findByCode(int code){
        for(LoadBalanceTypeEnum type: LoadBalanceTypeEnum.values()){
            if(type.code==code){
                return type;
            }
        }
        return null;
    }
}
