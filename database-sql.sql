-- 商品库存
CREATE TABLE goods_inventory
(
    goods_inventory_id BIGINT PRIMARY KEY,
    goods_name         varchar,
    goods_number       INT,
    goods_start_time   TIMESTAMP,
    goods_end_time     TIMESTAMP,
    goods_create_time  TIMESTAMP,
    version            INT
);
-- 支付订单
CREATE TABLE payment
(
    payment_id          BIGINT PRIMARY KEY,
    seckill_success_id  BIGINT,
    goods_inventory_id  BIGINT,
    user_id             BIGINT,
    payment_state       INT,
    payment_money       DOUBLE PRECISION,
    payment_create_time TIMESTAMP
);
-- 秒杀成功订单记录
CREATE TABLE seckill_success
(
    seckill_success_id          BIGINT PRIMARY KEY,
    user_id                     BIGINT,
    payment_state               INT,
    seckill_success_create_time TIMESTAMP
);

-- 商品库存 数据
insert into goods_inventory (goods_inventory_id, goods_name, goods_number, goods_start_time, goods_end_time, goods_create_time, version)VALUES (1001, '5000元秒杀iPhone15pro', 100, '2023-07-11 17:00:00', '2023-07-11 18:00:00', '2023-07-10 17:00:00', 0);
insert into goods_inventory (goods_inventory_id, goods_name, goods_number, goods_start_time, goods_end_time, goods_create_time, version)VALUES (1002, '4999元秒杀华为P40', 100, '2023-07-11 17:00:00', '2023-07-11 18:00:00', '2023-07-10 17:00:00', 0);
insert into goods_inventory (goods_inventory_id, goods_name, goods_number, goods_start_time, goods_end_time, goods_create_time, version)VALUES (1003, '3999元秒杀小米13pro', 100, '2023-07-11 17:00:00', '2023-07-11 18:00:00', '2023-07-10 17:00:00', 0);
insert into goods_inventory (goods_inventory_id, goods_name, goods_number, goods_start_time, goods_end_time, goods_create_time, version)VALUES (1004, '2999元秒杀红米K60pro', 100, '2023-07-11 17:00:00', '2023-07-11 18:00:00', '2023-07-10 17:00:00', 0);