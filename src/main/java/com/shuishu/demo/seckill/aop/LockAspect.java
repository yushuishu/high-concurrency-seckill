package com.shuishu.demo.seckill.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author ：谁书-ss
 * @Date ：2023-05-21 15:48
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Slf4j
@Component
@Scope
@Aspect
@Order(1) //order越小越是最先执行，但更重要的是最先执行的最后结束
public class LockAspect {
    /**
     * 思考：为什么不用synchronized
     * service 默认是单例的，并发下lock只有一个实例
     */
    // 互斥锁 参数默认false，不公平锁
    private static Lock lock = new ReentrantLock(true);

    // Service层切点   用于记录错误日志
    @Pointcut("@annotation(com.shuishu.demo.seckill.aop.ServiceLock)")
    public void lockAspect() {

    }

    @Around("lockAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        lock.lock();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
        return obj;
    }

}
