package com.shuishu.demo.seckill.service;


import com.shuishu.demo.seckill.entity.ApiResponse;

/**
 * @author ：谁书-ss
 * @date ：2023-05-21 15:29
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：秒杀
 * <p></p>
 */
public interface SeckillService {
    /**
     * 方式一：lock锁
     *
     * @param skgId 秒杀商品id
     * @param userId 用户id
     * @return -
     */
    ApiResponse<String> startSecondKillByLock(long skgId, Long userId);

    /**
     * 方式一：改进版加锁
     *
     * @param skgId 秒杀商品id
     * @param userId 用户id
     * @return -
     */
    ApiResponse<String> startSecondKillByLock2(long skgId, Long userId);

    /**
     * 方式二：AOP版加锁
     *
     * @param skgId 秒杀商品id
     * @param userId 用户id
     * @return -
     */
    ApiResponse<String> startSecondKillByAop(long skgId, Long userId);

    /**
     * 方式三：悲观锁1
     * 使用for update一定要加上事务，当事务处理完后，for update才会将行级锁解除。如果请求数和秒杀商品数量一致，会出现少卖
     *
     * @param skgId 秒杀商品id
     * @param userId 用户id
     * @return -
     */
    ApiResponse<String> startSecondKillByUpdate(long skgId, Long userId);

    /**
     * 方式四：悲观锁2
     *
     * @param skgId 秒杀商品id
     * @param userId 用户id
     * @return -
     */
    ApiResponse<String> startPesLockTwo(long skgId, Long userId);

    /**
     * 方式五：乐观锁
     * 乐观锁会出现大量的数据更新异常（抛异常就会导致购买失败），会出现少买的情况，不推荐使用乐观锁
     *
     * @param skgId 秒杀商品id
     * @param userId 用户id
     * @param number 数量
     * @return -
     */
    ApiResponse<String> startSecondKillByPesLock(long skgId, Long userId, int number);
}
