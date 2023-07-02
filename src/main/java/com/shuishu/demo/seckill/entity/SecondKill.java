package com.shuishu.demo.seckill.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-05-21 15:10
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：秒杀库存
 * <p></p>
 */
@Setter
@Getter
@ToString
public class SecondKill {

    private Long seckillId;

    private String name;

    private Integer number;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Integer version;
}
