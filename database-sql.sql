-- 支付
CREATE TABLE payment
(
    seckill_id  BIGINT PRIMARY KEY,
    user_id     BIGINT,
    state       INT,
    money       DOUBLE PRECISION,
    create_time TIMESTAMP
);

-- 订单
CREATE TABLE success_killed
(
    seckill_id  BIGINT PRIMARY KEY,
    user_id     BIGINT,
    state       INT,
    create_time TIMESTAMP
);

-- 库存
CREATE TABLE second_kill
(
    seckill_id  BIGINT PRIMARY KEY,
    name        varchar,
    number      INT,
    start_time  TIMESTAMP,
    end_time    TIMESTAMP,
    create_time TIMESTAMP,
    version     INT
);

insert into success_kill (seckill_id, name, number, start_time, end_time, create_time, version) VALUES (1001, '5000元秒杀iPhone15pro', 100, '2023-07-10 17:00:00', '2023-07-10 17:00:00', '2023-07-10 17:00:00', 0);
insert into success_kill (seckill_id, name, number, start_time, end_time, create_time, version) VALUES (1002, '4999元秒杀华为P40', 100, '2023-07-10 17:00:00', '2023-07-10 17:00:00', '2023-07-10 17:00:00', 0);
insert into success_kill (seckill_id, name, number, start_time, end_time, create_time, version) VALUES (1003, '3999元秒杀小米13pro', 100, '2023-07-10 17:00:00', '2023-07-10 17:00:00', '2023-07-10 17:00:00', 0);
insert into success_kill (seckill_id, name, number, start_time, end_time, create_time, version) VALUES (1004, '2999元秒杀红米K60pro', 100, '2023-07-10 17:00:00', '2023-07-10 17:00:00', '2023-07-10 17:00:00', 0);