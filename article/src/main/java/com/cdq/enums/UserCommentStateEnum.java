package com.cdq.enums;

public enum UserCommentStateEnum {

    SUCCESS(0,"操作成功"),INNER_ERROR(-1001,"系统错误"),EMPTY_ARTICLEID(-1002,"文章id为空"),
    EMPTY_FROM_USER(-1003,"发表用户不能为空"),EMPTY_COMMENT_CONTENT(-1004,"评论内容不能为空"),
    BAD_ROLE(-1005,"您没有权限执行此操作"),EMPTY_COMMENTID(-1002,"评论id不能为空");

    private int state;
    private String stateInfo;

    UserCommentStateEnum(int state,String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
