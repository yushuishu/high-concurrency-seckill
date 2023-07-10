package com.shuishu.demo.seckill.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-05-21 15:12
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：秒杀成功明细表
 * <p></p>
 */
@Setter
@Getter
@ToString
@TableName("success_killed")
public class SuccessKilled {
    @TableId("seckill_id")
    private Long seckillId;

    private Long userId;

    private Integer state;

    private Date createTime;

}
