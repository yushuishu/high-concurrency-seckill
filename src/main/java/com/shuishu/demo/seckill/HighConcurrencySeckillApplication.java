package com.shuishu.demo.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author ：谁书-ss
 * @date   ： 2023-05-11 '12:54'
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：高并发、秒杀 demo
 */
@MapperScan("com.shuishu.demo.mapper")
@SpringBootApplication
public class HighConcurrencySeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(HighConcurrencySeckillApplication.class, args);
    }

}
