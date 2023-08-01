package com.shuishu.demo.seckill.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Author ：谁书-ss
 * @Date ：2023-05-21 15:10
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：商品库存
 * <p></p>
 */
@Setter
@Getter
@ToString
@TableName("goods_inventory")
public class GoodsInventory {

    @TableId(value = "goods_inventory_id", type = IdType.AUTO)
    private Long goodsInventoryId;

    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 商品库存量
     */
    @TableField("goods_number")
    private Integer goodsNumber;

    /**
     * 商品 开始销售时间
     */
    @TableField("goods_start_time")
    private Date goodsStartTime;

    /**
     * 商品结束时间
     */
    @TableField("goods_end_time")
    private Date goodsEndTime;

    /**
     * 商品创建时间
     */
    @TableField("goods_create_time")
    private Date goodsCreateTime;


    @TableField("version")
    @Version
    private Integer version;
}
