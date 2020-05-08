package com.cdq.dao;


import com.cdq.model.Attention;
import com.cdq.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2019/9/27 11:09
 * @description：关注dao层测试
 * @modified By：
 * @version: $
 */
public class AttentionDaoTest extends BaseTest {

    @Autowired
    private AttentionDao attentionDao;

    @Test
    public void testSelect(){
        Attention attention=new Attention();
        User user=new User();
        user.setUserId("19980818");
        attention.setAttentionUser(user);
        List<Attention> list=attentionDao.selectAttention(attention,0,5);
        for (int i=0;i<list.size();i++){
            System.out.println("ss");
        }
    }

}
