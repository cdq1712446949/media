package com.cdq.media.unit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/1/14 18:48
 * @description：承担user类静态功能
 * @modified By：
 * @version: 1.0.1
 */
public class UserIdUtil {

    /**
     * 生成userId
     * 规则：四位随机数+时间戳
     *
     * @return
     */
    public static String createUserId() {
        String timeStr = String.valueOf(System.currentTimeMillis());
        String randomStr = String.valueOf(Math.round((Math.random() + 1) * 1000));
        return randomStr + timeStr;
    }

    /**
     * 检查字符串中是否包含非法字符
     *
     * @param str
     * @return
     */
    //TODO 修改正则表达式
    public static boolean isSpecialChar(String str) {
        String regEx = "[ `~@#$%&()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 检查用户性别属性，不能是null不能为空
     * 男女二选一
     * @param sex
     * @return
     */
    public static boolean checkSex(String sex) {
        if (sex != null && !sex.equals("")) {
            if (sex.equals("男") || sex.equals("女")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(createUserId());
        }
    }

}
