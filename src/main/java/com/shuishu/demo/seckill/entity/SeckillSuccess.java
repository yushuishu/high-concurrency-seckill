package com.shuishu.demo.seckill.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Author ：谁书-ss
 * @Date ：2023-05-21 15:12
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：秒杀成功明细表
 * <p></p>
 */
@Setter
@Getter
@ToString
@TableName("seckill_success")
public class SeckillSuccess {
    @TableId(value = "seckill_success_id", type = IdType.AUTO)
    private Long seckillSuccessId;

    @TableField("user_id")
    private Long userId;

    /**
     * -1指无效，0指成功，1指已付款
     */
    @TableField("payment_state")
    private Integer paymentState;

    @TableField("seckill_success_create_time")
    private Date seckillSuccessCreateTime;

}
