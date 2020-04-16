package com.shop.common.script;

public interface RedisScript {
    /**
     * redis脚本
     * KEYS[1] -> sku在redis的大key
     */
    public static final String REDUCE_SKU_RESERVE = "local count1 = redis.call('hget',KEYS[1],'skuReserve'); \n" +
            "if type(count1) == 'string' then \n" +
            "count1 = tonumber(count1); \n" +
            "end \n" +
            "if count1 > 0 then \n" +
            "return redis.call('hincrby',KEYS[1],'skuReserve',-1); \n" +
            "else return -1; \n" +
            "end \n";
}
