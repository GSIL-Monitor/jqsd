package com.jqsd.common.constant;

/**
 * Created by sam on 16-7-18.
 */
public class RedisConstant {

    /**
     * http://www.cnblogs.com/edisonfeng/p/3571870.html
     * 参考资料
     */

    /**
     * 分割符号
     */
    public static final String SEP = ":";

    /**
     * 统一参数列表前缀
     */
    public static final String SDAP_CONFIG = "SDAP_CONFIG";

    /**
     * 过期时间
     */
    public static final int EX_DAY_TIME = 60*60*24;
    public static final int EX_7_DAY_TIME = EX_DAY_TIME*7;

    public static final String ESR_ORDER_PLATFORM = SDAP_CONFIG + SEP +  "ESR_ORDER_PLATFORM";
    public static final String ESR_ORDER_SHOP = SDAP_CONFIG + SEP +  "ESR_ORDER_SHOP";

}
