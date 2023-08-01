package com.shuishu.demo.seckill.enums;


/**
 * @Author ：谁书-ss
 * @Date ：2023-05-21 15:46
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
public interface SecondKillEnum {

    enum StateEnum {
        /**
         * 秒杀状态
         */
        MUCH(2, "哎呦喂，人也太多了，请稍后！"),
        SUCCESS(1, "秒杀成功"),
        END(0, "秒杀结束"),
        REPEAT_KILL(-1, "重复秒杀"),
        INNER_ERROR(-2, "系统异常"),
        DATE_REWRITE(-3, "数据篡改");

        private int state;
        private String info;

        StateEnum(int state, String info) {
            this.state = state;
            this.info = info;
        }

        public int getState() {
            return state;
        }


        public String getInfo() {
            return info;
        }


        public static StateEnum stateOf(int index) {
            for (StateEnum state : values()) {
                if (state.getState() == index) {
                    return state;
                }
            }
            return null;
        }

    }

}
