package com.shop.goods.redis;

import com.shop.common.constant.Constant;
import com.shop.common.constant.RedisConstant;
import com.shop.common.util.ToolUtils;
import com.shop.entity.Goods;
import com.shop.entity.GoodsSku;
import com.shop.entity.util.PageUtil;
import com.shop.goods.mapper.GoodsMapper;
import com.shop.goods.service.IGoodsSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class RedisService {
    @Autowired
    RedisTemplate<String,Serializable> redisTemplate;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    IGoodsSkuService skuService;

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 把goods数据加载到redis,hash结构存储
     * key-> goods:(index)
     * sku-> sku:(goodsId:skuid)
     */
    @PostConstruct
    public void addGoodsNSkuToRedis(){
        Set<String> keys = redisTemplate.keys(RedisConstant.GOODS_PRE + RedisConstant.KEYS_USUAL);
        //redis已经存在数据就不用再次加载
        if (keys != null && keys.size() > 0){
            keys = null;
            return;
        }
        //查询出所有符合要求的总数量
        long count = goodsMapper.getGoodsNSkuListCount(new PageUtil<Goods>());
        //每次查10条
        int oneTimeCount = 10;
        //每次查10条，所需查的次数
        long times = 0 ;
        if ((count % oneTimeCount) == 0){
            times = count / oneTimeCount ;
        }else {
            times = (count / oneTimeCount) + 1 ;
        }
        //每次查10条记录，避免一次加载太多数据
        for (long i = 0; i < times; i++) {
            PageUtil<Goods> util = new PageUtil<>();
            util.setCurrent(i + 1);
            util.setSize(oneTimeCount);
            List<Goods> list = goodsMapper.getGoodsNSkuList(util);
            if (list != null && list.size() > 0){
                for (int k = 0; k <list.size() ; k++){
                    Goods goods = list.get(k);
                    if(goods == null){
                        break;
                    }
                    //根据遍历的goods查询对应的所有sku
                    List<GoodsSku> skus = skuService.getGoodsSkuByGoodsId(goods.getGoodsId());
                    Map<String, Object> stringObjectMap = ToolUtils.bean2Map(goods);
                    stringObjectMap.put("startTime",formatter.format(goods.getStartTime()));
                    stringObjectMap.put("endTime",formatter.format(goods.getEndTime()));
                    long temp = (oneTimeCount * i) + k;
                   //RedisConstant.GOODS_PRE + temp,是为了便于模拟redis的简单分页
                   redisTemplate.opsForHash().putAll(RedisConstant.GOODS_PRE + temp,stringObjectMap);
                   if (skus != null && skus.size()>0){
                       for (GoodsSku sku : skus){
                           //sku在redis的key设置为sku:goodsid:skuid，便于根据goodsid查询对应的sku,命令keys sku:goodsId*
                           redisTemplate.opsForHash().putAll(RedisConstant.GOODS_SKU_PRE + goods.getGoodsId()
                                   + ":" + sku.getSkuId(),ToolUtils.bean2Map(sku));
                       }
                   }
                }
            }else {
                break;
            }
        }
    }

    /**
     * 分页查询goods
     * @param pageUtil
     * @return
     */
    public PageUtil<Goods> getGoodsListWithPage(PageUtil<Goods> pageUtil){
        List<Goods> result = new LinkedList();
        PageUtil<Goods> returnResult = new PageUtil<Goods>();
        if (pageUtil != null){
            long startRecord = pageUtil.getStartRecord();
            for (long x = startRecord ; x < (startRecord + pageUtil.getSize()); x++){
                Map<Object, Object> entries =(Map<Object, Object>) redisTemplate.boundHashOps(RedisConstant.GOODS_PRE + x).entries();
                if (entries != null && entries.size() > 0){
                    Goods goods = Goods.builder().goodsId((String) entries.get("goodsId")).endTime(LocalDateTime.parse(((String)entries.get("endTime")), formatter))
                            .goodsDesc((String) entries.get("goodsDesc")).name((String) entries.get("name"))
                            .startTime(LocalDateTime.parse(((String)entries.get("startTime")), formatter)).title((String) entries.get("title"))
                            .userId((String) entries.get("userId")).build();
                    result.add(goods);
                }
            }
            Set<String> total = redisTemplate.keys(RedisConstant.GOODS_PRE + RedisConstant.KEYS_USUAL);
            returnResult.setTotal(total.size());
            returnResult.setRecords(result);
        }
        return returnResult;
    }

    /**
     * 根据goodsId查询goodsSku
     * @param goodsId
     * @return
     */
    public List<GoodsSku> getGoodsSkuByGoodsId(String goodsId){
        String keyPre = RedisConstant.GOODS_SKU_PRE + goodsId;
        Set<String> keys = redisTemplate.keys(keyPre + RedisConstant.KEYS_USUAL);
        List<GoodsSku> result = null;
        if (keys != null && keys.size() > 0){
            result = new LinkedList<>();
            for (String key : keys){
                Map<Object, Object> entries = redisTemplate.boundHashOps(key).entries();
                if (entries != null && entries.size() > 0){
                    GoodsSku goodsSku = GoodsSku.builder().goodsId(goodsId).picture((String) entries.get("picture")).price(BigDecimal.valueOf((double)entries.get("price")))
                            .skuId((String) entries.get("skuId")).skuParams((String) entries.get("skuParams"))
                            .skuReserve((Integer) entries.get("skuReserve")).skuStartReserve((Integer) entries.get("skuStartReserve"))
                            .state((Integer) entries.get("state")).build();
                    result.add(goodsSku);
                }
            }
        }
        return result;
    }
}
