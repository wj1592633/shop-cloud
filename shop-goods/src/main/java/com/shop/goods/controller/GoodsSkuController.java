package com.shop.goods.controller;

import com.shop.common.dto.ResponseResult;
import com.shop.entity.GoodsSku;
import com.shop.goods.service.IGoodsSkuService;
import com.shop.api.feign.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wj
 */
@RestController
public class GoodsSkuController {

    @Autowired
    IGoodsSkuService skuService;

    @Autowired
    OrderService orderService;

    //方法作废
    @Deprecated
    @GetMapping("/sku/reduce/{goodsId}/{skuId}")
    public ResponseResult reduceReserve(@PathVariable(value = "goodsId",required = true) String goodsId,
                                        @PathVariable(value = "skuId",required = true) String skuId) throws IOException {
        boolean b = false;
        if(b){
            return ResponseResult.ok("购买成功");
        }
        return ResponseResult.fail("购买失败");
    }
    @GetMapping("/sku/{goodsId}")
    public ResponseResult getGoodsSkuByGoodsId(@PathVariable("goodsId") String goodsId){
        List<GoodsSku> skus = skuService.getGoodsSkuByGoodsId(goodsId);
        return ResponseResult.ok(skus);
    }
}
