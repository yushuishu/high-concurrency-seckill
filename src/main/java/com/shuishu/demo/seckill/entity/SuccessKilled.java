package com.shuishu.demo.seckill.entity;


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
public class SuccessKilled {
    private Long seckillId;

    private Long userId;

    private Integer state;

    private Date createTime;

}
