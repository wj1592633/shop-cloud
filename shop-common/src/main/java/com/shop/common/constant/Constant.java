package com.shop.common.constant;

public class Constant {
    public static final int RESPONSE_CODE_SUCCESS = 200;
    public static final int RESPONSE_CODE_FAIL = 400;
    public static final int RESPONSE_CODE_UNAUTHORIZED = 401;
    public static final int RESPONSE_CODE_FORBIDDEN = 403;
    public static final int RESPONSE_CODE_MISS = 404;
    public static final int STATE_NORMAL = 1;//状态正常为1
    public static final int STATE_ABNORMAL = 0;
    public static final String TXMSG_PRODUCER_GROUP_GOODS = "txmsg_producer_group_goods";
    public static final String TXMSG_PRODUCER_GROUP_USER = "txmsg_producer_group_user";
    public static final String TXMSG_CONSUMER_GROUP_ORDER = "txmsg_consumer_group_order";
    public static final String TXMSG_PRODUCER_GROUP_ORDER_TIME_OUT = "txmsg_producer_group_order_time_out";

    public static final String TXMSG_TOPIC_ORDER_STATE = "txmsg_topic_order_state";
    public static final String TXMSG_TOPIC_ORDER_GENERATE = "txmsg_topic_order_generate";
    public static final String TXMSG_TOPIC_ORDER_TIME_OUT = "txmsg_topic_order_time_out";
    public static final String TIME_OUT_ITEM_REDIS_KEY = "redisSkuKey";
    public static final String TIME_OUT_ITEM_DB_SKU_KEY = "orderId";

}
