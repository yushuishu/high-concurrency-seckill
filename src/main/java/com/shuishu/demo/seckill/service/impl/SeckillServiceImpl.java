package com.shuishu.demo.seckill.service.impl;


import com.shuishu.demo.seckill.aop.ServiceLock;
import com.shuishu.demo.seckill.entity.ApiResponse;
import com.shuishu.demo.seckill.entity.Payment;
import com.shuishu.demo.seckill.entity.GoodsInventory;
import com.shuishu.demo.seckill.entity.SeckillSuccess;
import com.shuishu.demo.seckill.enums.SecondKillEnum;
import com.shuishu.demo.seckill.exception.BusinessException;
import com.shuishu.demo.seckill.mapper.PaymentMapper;
import com.shuishu.demo.seckill.mapper.GoodsInventoryMapper;
import com.shuishu.demo.seckill.mapper.SeckillSuccessMapper;
import com.shuishu.demo.seckill.service.SeckillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @Author ：谁书-ss
 * @Date ：2023-05-21 15:29
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：秒杀
 * <p></p>
 */
@Log4j2
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SeckillServiceImpl implements SeckillService {
    Lock lock = new ReentrantLock(true);

    private final PaymentMapper paymentMapper;

    private final GoodsInventoryMapper goodsInventoryMapper;

    private final SeckillSuccessMapper seckillSuccessMapper;


    @Override
    public ApiResponse<String> startSecondKillByLock(long goodsInventoryId, Long userId) {
        /**
         * 1.脏读：事务A读取了事务B更新的数据，然后B回滚操作，那么A读取到的数据是脏数据
         * 2.不可重复读：事务 A 多次读取同一数据，事务 B 在事务A多次读取的过程中，对数据作了更新并提交，导致事务A多次读取同一数据时，结果 不一致。
         * 3.幻读：系统管理员A将数据库中所有学生的成绩从具体分数改为ABCDE等级，但是系统管理员B就在这个时候插入了一条具体分数的记录，当系统管理员A改结束后发现还有一条记录没有改过来，就好像发生了幻觉一样，这就叫幻读。
         *
         * 事务隔离级别	                脏读	    不可重复读	幻读
         * 读未提交（read-uncommitted）	是	        是	     是
         * 不可重复读（read-committed）	否	        是	     是
         * 可重复读（repeatable-read）	否	        否	     是
         * 串行化（serializable）	        否	        否	     否
         *
         * 1)这里、不清楚为啥、总是会被超卖101、难道锁不起作用、lock是同一个对象
         * 2)分析一下，事务未提交之前，锁已经释放(事务提交是在整个方法执行完)，导致另一个事物读取到了这个事物未提交的数据，也就是传说中的脏读，
         *      但数据库默认的事务隔离级别为 可重复读(repeatable-read)，也就不可能出现脏读
         * 3)给自己留个坑思考：为什么分布式锁(zk和redis)没有问题？(事实是有问题的，由于redis释放锁需要远程通信，不那么明显而已)
         */
        lock.lock();
        try {
            // 校验库存
            GoodsInventory goodsInventory = goodsInventoryMapper.selectById(goodsInventoryId);
            Integer number = goodsInventory.getGoodsNumber();
            if (number > 0) {
                // 扣库存
                goodsInventory.setGoodsNumber(number - 1);
                goodsInventoryMapper.updateById(goodsInventory);
                // 创建秒杀成功订单
                SeckillSuccess seckillSuccess = new SeckillSuccess();
                seckillSuccess.setUserId(userId);
                seckillSuccess.setPaymentState(1);
                seckillSuccess.setSeckillSuccessCreateTime(new Timestamp(System.currentTimeMillis()));
                seckillSuccessMapper.insert(seckillSuccess);
                // 支付
                Payment payment = new Payment();
                payment.setGoodsInventoryId(goodsInventoryId);
                payment.setSeckillSuccessId(seckillSuccess.getSeckillSuccessId());
                payment.setUserId(userId);
                payment.setPaymentMoney(40.0);
                payment.setPaymentState(1);
                payment.setPaymentCreateTime(new Timestamp(System.currentTimeMillis()));
                paymentMapper.insert(payment);
            } else {
                return ApiResponse.error(SecondKillEnum.StateEnum.END.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("异常");
        } finally {
            // 释放锁
            lock.unlock();
        }
        return ApiResponse.success(SecondKillEnum.StateEnum.SUCCESS.getInfo(), null);
    }


    @Override
    public ApiResponse<String> startSecondKillByLock2(long goodsInventoryId, Long userId) {
        try {
            // 校验库存
            GoodsInventory goodsInventory = goodsInventoryMapper.selectById(goodsInventoryId);
            Integer number = goodsInventory.getGoodsNumber();
            if (number > 0) {
                // 扣库存
                goodsInventory.setGoodsNumber(number - 1);
                goodsInventoryMapper.updateById(goodsInventory);
                // 创建秒杀成功订单
                SeckillSuccess seckillSuccess = new SeckillSuccess();
                seckillSuccess.setUserId(userId);
                seckillSuccess.setPaymentState(0);
                seckillSuccess.setSeckillSuccessCreateTime(new Timestamp(System.currentTimeMillis()));
                seckillSuccessMapper.insert(seckillSuccess);
                // 支付
                Payment payment = new Payment();
                payment.setGoodsInventoryId(goodsInventoryId);
                payment.setSeckillSuccessId(seckillSuccess.getSeckillSuccessId());
                payment.setUserId(userId);
                payment.setPaymentMoney(40.0);
                payment.setPaymentState(1);
                payment.setPaymentCreateTime(new Timestamp(System.currentTimeMillis()));
                paymentMapper.insert(payment);
            } else {
                return ApiResponse.error(SecondKillEnum.StateEnum.END.getInfo());
            }
        } catch (Exception e) {
            throw new BusinessException("异常");
        }
        return ApiResponse.success(SecondKillEnum.StateEnum.SUCCESS.getInfo(), null);
    }

    /**
     * 方式二：AOP版加锁
     *
     * @param goodsInventoryId 秒杀商品id
     * @param userId           用户id
     * @return -
     */
    @ServiceLock
    @Override
    public ApiResponse<String> startSecondKillByAop(long goodsInventoryId, Long userId) {
        try {
            // 校验库存
            GoodsInventory goodsInventory = goodsInventoryMapper.selectById(goodsInventoryId);
            Integer number = goodsInventory.getGoodsNumber();
            if (number > 0) {
                // 扣库存
                goodsInventory.setGoodsNumber(number - 1);
                goodsInventoryMapper.updateById(goodsInventory);
                // 创建秒杀成功订单
                SeckillSuccess seckillSuccess = new SeckillSuccess();
                seckillSuccess.setUserId(userId);
                seckillSuccess.setPaymentState(0);
                seckillSuccess.setSeckillSuccessCreateTime(new Timestamp(System.currentTimeMillis()));
                seckillSuccessMapper.insert(seckillSuccess);
                // 支付
                Payment payment = new Payment();
                payment.setGoodsInventoryId(goodsInventoryId);
                payment.setSeckillSuccessId(seckillSuccess.getSeckillSuccessId());
                payment.setUserId(userId);
                payment.setPaymentMoney(40.0);
                payment.setPaymentState(1);
                payment.setPaymentCreateTime(new Timestamp(System.currentTimeMillis()));
                paymentMapper.insert(payment);
            } else {
                return ApiResponse.error(SecondKillEnum.StateEnum.END.getInfo());
            }
        } catch (Exception e) {
            throw new BusinessException("异常");
        }
        return ApiResponse.success(SecondKillEnum.StateEnum.SUCCESS.getInfo(), null);
    }


    @Override
    public ApiResponse<String> startSecondKillByUpdate(long goodsInventoryId, Long userId) {
        try {
            // 校验库存
            GoodsInventory goodsInventory = goodsInventoryMapper.querySecondKillForUpdate(goodsInventoryId);
            Integer number = goodsInventory.getGoodsNumber();
            if (number > 0) {
                // 扣库存
                goodsInventory.setGoodsNumber(number - 1);
                goodsInventoryMapper.updateById(goodsInventory);
                // 创建秒杀成功订单
                SeckillSuccess seckillSuccess = new SeckillSuccess();
                seckillSuccess.setUserId(userId);
                seckillSuccess.setPaymentState(0);
                seckillSuccess.setSeckillSuccessCreateTime(new Timestamp(System.currentTimeMillis()));
                seckillSuccessMapper.insert(seckillSuccess);
                // 支付
                Payment payment = new Payment();
                payment.setGoodsInventoryId(goodsInventoryId);
                payment.setSeckillSuccessId(seckillSuccess.getSeckillSuccessId());
                payment.setUserId(userId);
                payment.setPaymentMoney(40.0);
                payment.setPaymentState(1);
                payment.setPaymentCreateTime(new Timestamp(System.currentTimeMillis()));
                paymentMapper.insert(payment);
            } else {
                return ApiResponse.error(SecondKillEnum.StateEnum.END.getInfo());
            }
        } catch (Exception e) {
            throw new BusinessException("异常");
        }
        return ApiResponse.success(SecondKillEnum.StateEnum.SUCCESS.getInfo(), null);
    }


    @Override
    public ApiResponse<String> startPesLockTwo(long goodsInventoryId, Long userId) {
        try {
            // 不校验，直接扣库存更新
            int result = goodsInventoryMapper.updateSecondKillById(goodsInventoryId);
            if (result > 0) {
                // 创建秒杀成功订单
                SeckillSuccess seckillSuccess = new SeckillSuccess();
                seckillSuccess.setUserId(userId);
                seckillSuccess.setPaymentState(0);
                seckillSuccess.setSeckillSuccessCreateTime(new Timestamp(System.currentTimeMillis()));
                seckillSuccessMapper.insert(seckillSuccess);
                // 支付
                Payment payment = new Payment();
                payment.setGoodsInventoryId(goodsInventoryId);
                payment.setSeckillSuccessId(seckillSuccess.getSeckillSuccessId());
                payment.setUserId(userId);
                payment.setPaymentMoney(40.0);
                payment.setPaymentState(1);
                payment.setPaymentCreateTime(new Timestamp(System.currentTimeMillis()));
                paymentMapper.insert(payment);
            } else {
                return ApiResponse.error(SecondKillEnum.StateEnum.END.getInfo());
            }
        } catch (Exception e) {
            throw new BusinessException("异常");
        }
        return ApiResponse.success(SecondKillEnum.StateEnum.SUCCESS.getInfo(), null);
    }


    @Override
    public ApiResponse<String> startSecondKillByPesLock(long goodsInventoryId, Long userId, int number) {
        // 乐观锁，不进行库存数量的校验
        try {
            // 不校验，直接扣库存更新
            GoodsInventory goodsInventory = goodsInventoryMapper.selectById(goodsInventoryId);
            // 剩余的数量应该要大于等于秒杀的数量
            if (goodsInventory.getGoodsNumber() >= number) {
                int result = goodsInventoryMapper.updateSecondKillByVersion(number, goodsInventoryId, goodsInventory.getVersion());
                if (result > 0) {
                    // 创建秒杀成功订单
                    SeckillSuccess seckillSuccess = new SeckillSuccess();
                    seckillSuccess.setUserId(userId);
                    seckillSuccess.setPaymentState(0);
                    seckillSuccess.setSeckillSuccessCreateTime(new Timestamp(System.currentTimeMillis()));
                    seckillSuccessMapper.insert(seckillSuccess);
                    // 支付
                    Payment payment = new Payment();
                    payment.setGoodsInventoryId(goodsInventoryId);
                    payment.setSeckillSuccessId(seckillSuccess.getSeckillSuccessId());
                    payment.setUserId(userId);
                    payment.setPaymentMoney(40.0);
                    payment.setPaymentState(1);
                    payment.setPaymentCreateTime(new Timestamp(System.currentTimeMillis()));
                    paymentMapper.insert(payment);
                } else {
                    return ApiResponse.error(SecondKillEnum.StateEnum.END.getInfo());
                }
            }
        } catch (Exception e) {
            throw new BusinessException("异常");
        }
        return ApiResponse.success(SecondKillEnum.StateEnum.SUCCESS.getInfo(), null);
    }


}
