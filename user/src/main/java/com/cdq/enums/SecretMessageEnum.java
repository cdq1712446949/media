package com.cdq.enums;

public enum SecretMessageEnum {

    SUCCESS(0, "操作成功"),INNER_ERROR(-1001,"系统错误"),EMPTY_USER(-1002,"user不能为null"),
    EMPTY_CONTENT(-1003,"内容部能为空"),TO_USER_REFUSE(-1004,"该用户拒绝接收私信");

    private int state;
    private String stateInfo;

    SecretMessageEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
