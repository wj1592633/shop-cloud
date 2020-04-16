package com.shop.order.service.impl;

import com.shop.common.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.*;

//@Component
@Slf4j
public class TestAsyHandle implements ApplicationListener<ContextRefreshedEvent> {
    public static ConcurrentHashMap<String,DeferredResult<ResponseResult>> map = new ConcurrentHashMap<String,DeferredResult<ResponseResult>>();
    public static BlockingQueue<Object> queue = new LinkedBlockingQueue<>();


    public Object mockHandleTask(Object take) throws ExecutionException, InterruptedException {
        Callable<String> callable = ()->{
            log.warn("开始处理请求了，模拟其他模块完成实际业务中..." + take);
            TimeUnit.SECONDS.sleep(2);
            return "模拟DeferredResult处理业务成功,结果是..." + take;
        };
        Map a = map;
        int size = map.size();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        String s = futureTask.get();
        return s;
    }

    //@PostConstruct
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {
            while (true){
                try {
                    Object take = queue.take();

                    if (take != null){
                        log.warn("准备开始处理异步处理请求....."+ take);
                        DeferredResult<ResponseResult> result = map.get(take);
                        String data = (String) mockHandleTask(take);
                        result.setResult(ResponseResult.ok(data));
                        map.remove(take);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
