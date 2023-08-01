package com.shuishu.demo.seckill.controller;


import com.shuishu.demo.seckill.entity.ApiResponse;
import com.shuishu.demo.seckill.service.SeckillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author ：谁书-ss
 * @Date ：2023-05-12 13:03
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：秒杀
 * <p></p>
 * 方式一（改进版加锁）
 * 方式二（AOP版加锁）
 * 方式三（悲观锁一）
 * 方式四（悲观锁二）
 * 方式五（乐观锁）
 * 方式六（阻塞队列）
 * 方式七（Disruptor队列）
 */
@Tag(name = "秒杀测试")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("seckill")
public class SeckillController {
    private final SeckillService seckillService;

    Lock lock = new ReentrantLock(true);


    @Operation(summary = "方式一：lock锁", description = "会出现超卖的情况：这里在业务方法开始加了锁，在业务方法结束后释放了锁。但这里的事务提交却不是这样的，有可能在事务提交之前，就已经把锁释放了，这样会导致商品超卖现象")
    @PostMapping("start/lock/one")
    public ApiResponse<String> startLock(long goodsInventoryId) {
        try {
            log.info("开始秒杀方式一...");
            final long userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;
            ApiResponse<String> apiResponse = seckillService.startSecondKillByLock(goodsInventoryId, userId);
            if (apiResponse != null) {
                log.info("用户:{}--{}", userId, apiResponse.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人也太多了，请稍后！");
            }
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.error();
    }


    @Operation(summary = "方式一：改进版加锁", description = "可以解决事务未提交之前，锁释放的问题")
    @PostMapping("start/lock/two")
    public ApiResponse<String> startLock2(long goodsInventoryId) {
        // 在此处加锁
        lock.lock();
        try {
            log.info("开始秒杀方式一...");
            final long userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;
            ApiResponse<String> apiResponse = seckillService.startSecondKillByLock2(goodsInventoryId, userId);
            if (apiResponse != null) {
                log.info("用户:{}--{}", userId, apiResponse.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人也太多了，请稍后！");
            }
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 在此处释放锁
            lock.unlock();
        }
        return ApiResponse.error();
    }


    @Operation(summary = "方式二：AOP版加锁", description = "AOP版加锁")
    @PostMapping("start/aop")
    public ApiResponse<String> startAop(long goodsInventoryId) {
        // 在此处加锁
        try {
            log.info("开始秒杀方式二...");
            final long userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;
            ApiResponse<String> apiResponse = seckillService.startSecondKillByAop(goodsInventoryId, userId);
            if (apiResponse != null) {
                log.info("用户:{}--{}", userId, apiResponse.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人也太多了，请稍后！");
            }
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.error();
    }


    @Operation(summary = "方式三：悲观锁1", description = "使用for update一定要加上事务，当事务处理完后，for update才会将行级锁解除。如果请求数和秒杀商品数量一致，会出现少卖")
    @PostMapping("/start/pes/lock/one")
    public ApiResponse<String> startPesLockOne(long goodsInventoryId) {
        try {
            log.info("开始秒杀方式三...");
            final long userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;
            ApiResponse<String> apiResponse = seckillService.startSecondKillByUpdate(goodsInventoryId, userId);
            if (apiResponse != null) {
                log.info("用户:{}--{}", userId, apiResponse.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人也太多了，请稍后！");
            }
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.error();
    }


    @Operation(summary = "方式四：悲观锁2", description = "")
    @PostMapping("/start/pes/lock/two")
    public ApiResponse<String> startPesLockTwo(long goodsInventoryId) {
        try {
            log.info("开始秒杀方式四...");
            final long userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;
            ApiResponse<String> apiResponse = seckillService.startPesLockTwo(goodsInventoryId, userId);
            if (apiResponse != null) {
                log.info("用户:{}--{}", userId, apiResponse.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人也太多了，请稍后！");
            }
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.error();
    }


    @Operation(summary = "方式五：乐观锁", description = "乐观锁会出现大量的数据更新异常（抛异常就会导致购买失败），会出现少买的情况，不推荐使用乐观锁")
    @PostMapping("/start/opt/lock/one")
    public ApiResponse<String> startOptLock(long goodsInventoryId) {
        try {
            log.info("开始秒杀方式四...");
            final long userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;
            ApiResponse<String> apiResponse = seckillService.startSecondKillByPesLock(goodsInventoryId, userId, 1);
            if (apiResponse != null) {
                log.info("用户:{}--{}", userId, apiResponse.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人也太多了，请稍后！");
            }
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.error();
    }


}
