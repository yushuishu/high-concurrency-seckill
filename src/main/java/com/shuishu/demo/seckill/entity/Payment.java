package com.shuishu.demo.seckill.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-05-21 15:08
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@TableName("payment")
public class Payment {
    @TableId("seckill_id")
    private Long seckillId;

    private Long userId;

    private Integer state;

    private Double money;

    private Date createTime;

}
