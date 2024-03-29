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
 * @Date ：2023-05-21 15:08
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@TableName("payment")
public class Payment {

    @TableId(value = "payment_id", type = IdType.AUTO)
    private Long paymentId;

    /**
     * 商品秒杀成功ID
     */
    @TableField("seckill_success_id")
    private Long seckillSuccessId;

    /**
     * 商品库存ID
     */
    @TableField("goods_inventory_id")
    private Long goodsInventoryId;

    @TableField("user_id")
    private Long userId;

    /**
     * -1指无效，0指成功，1指已付款
     */
    @TableField("payment_state")
    private Integer paymentState;

    /**
     * 支付金额
     */
    @TableField("payment_money")
    private Double paymentMoney;

    /**
     * 支付订单创建时间
     */
    @TableField("payment_create_time")
    private Date paymentCreateTime;

}
