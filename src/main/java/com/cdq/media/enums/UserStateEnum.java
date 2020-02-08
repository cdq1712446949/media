package com.cdq.media.enums;

public enum UserStateEnum {

    SUCCESS(0, "操作成功"),INNER_ERROR(-1001,"系统错误"),EMPTY_USER(-1002,"user不能为null"),
    EMPTY_USERNAME(-1003,"账号不能为空"),EMPTY_PASSWORD(-1004,"密码不能为空"),ACCOUNT_ERROR(-1005,"帐号或者密码错误"),
    PASSWORD_ERROR(-1006,"密码错误"),ACCOUNT_BAN(-1007,"账号已经被冻结"),EMPTY_ARTICLE(-1008,"请选择文章"),
    EMPTY_ARGS(-1009,"请填写必填的选项"),EMPTY_NICKNAME(-1010,"用户昵称不符合规范");

    private int state;
    private String stateInfo;

    UserStateEnum(int state, String stateInfo) {
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
