package com.shop.goods.mongo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuyRecord implements Serializable {
    private String goodsId;
    private String UserId;
}
