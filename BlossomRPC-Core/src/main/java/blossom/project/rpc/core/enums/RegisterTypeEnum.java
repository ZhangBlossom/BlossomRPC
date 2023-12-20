package blossom.project.rpc.core.enums;/**
 * @Author:Serendipity
 * @Date:
 * @Description:
 */

/**
 * @author: ZhangBlossom
 * @date: 2023/12/19 23:36
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * RegisterTypeEnum枚举类
 */
public enum RegisterTypeEnum {
    NACOS("nacos"), ZOOKEEPER("zookeeper");

    private String name;

    RegisterTypeEnum(String name) {
        this.name = name;
    }


    public static RegisterTypeEnum findByName(String name) {
        for (RegisterTypeEnum type : RegisterTypeEnum.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}

    