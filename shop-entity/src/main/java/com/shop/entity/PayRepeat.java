package com.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wj
 * PayRepeat
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_pay_repeat")
public class PayRepeat implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("order_id")
    private String orderId;


}
