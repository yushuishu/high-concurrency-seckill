package com.shuishu.demo.seckill.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
@TableName("seckill")
public class SecondKill {

    @TableId(value = "seckill_id", type = IdType.AUTO)
    private Long seckillId;

    private String name;

    private Integer number;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    @Version
    private Integer version;
}
