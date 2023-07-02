package com.shuishu.demo.seckill.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuishu.demo.seckill.entity.SecondKill;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author ：谁书-ss
 * @date ：2023-05-21 15:40
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 * <p></p>
 */
@Repository
public interface SecondKillMapper extends BaseMapper<SecondKill> {
    /**
     * 将此行数据进行加锁，当整个方法将事务提交后，才会解锁
     * for update进行对查询数据加锁，加的是行锁
     *
     * @param skgId -
     * @return -
     */
    @Select(value = "SELECT * FROM seckill WHERE seckill_id=#{skgId} FOR UPDATE")
    SecondKill querySecondKillForUpdate(@Param("skgId") Long skgId);

    @Update(value = "UPDATE seckill SET number=number-1 WHERE seckill_id=#{skgId} AND number > 0")
    int updateSecondKillById(@Param("skgId") long skgId);

    @Update(value = "UPDATE seckill  SET number=number-#{number},version=version+1 WHERE seckill_id=#{skgId} AND version = #{version}")
    int updateSecondKillByVersion(@Param("number") int number, @Param("skgId") long skgId, @Param("version")int version);
}
