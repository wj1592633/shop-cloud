package com.shop.common.util;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class IdGenerator {
    /**
     * 生成订单编号
     * 格式："order-420-20191111123011-41-userId"
     * userId:订单商品所属的商家id
     * @return
     */
    public static String OrderIdGenarate(Serializable userId){
       return  "order-"+ SingleInstance.random.nextInt(1000)+"-"+(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))) +"-"+ SingleInstance.random.nextInt(1000) + "-" + userId;
    }

    private static class SingleInstance{
       private static Random  random = new Random();
    }
    public static Random getRandomInstance(){
        return SingleInstance.random;
    }
}
