package com.cdq.Service.impl;

import com.cdq.Service.AttentionService;
import com.cdq.dao.AttentionDao;
import com.cdq.enums.BaseStateEnum;
import com.cdq.execution.AttentionExecution;
import com.cdq.model.Attention;
import com.cdq.until.ConstansUtil;
import com.cdq.until.PageUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/8 15:28
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Service
public class AttentionServiceImpl implements AttentionService {

    @Autowired
    private AttentionDao attentionDao;

    /**
     * 根据userId查询关注列表
     * @param attention
     * @param indexPage
     * @param pageSize
     * @return
     */
    @Override
    public AttentionExecution getAttentionListByUser(Attention attention, int indexPage, int pageSize) {
        //校验参数
        if (attention.getAttentionUser()==null || ConstansUtil.EMPTY_STR.equals(attention.getAttentionUser().getUserId())){
            return new AttentionExecution(BaseStateEnum.EMPTY_INFO);
        }
        //调用dao层
        List<Attention> result = attentionDao.selectAttention(attention,
                PageUtil.pageToRowIndex(indexPage,pageSize),10);
        if (result!=null){
            AttentionExecution attentionExecution = new AttentionExecution(BaseStateEnum.SUCCESS,result);
            attentionExecution.setCount(attentionDao.queryAttenCount(attention));
            return attentionExecution;
        }else{
            return new AttentionExecution(BaseStateEnum.INNER_ERROR);
        }
    }

    /**
     * 添加关注记录
     *
     * @param attention
     * @return
     */
    @Override
    public AttentionExecution addAttention(Attention attention) {
        //参数校验
        if (attention.getAttentedUser()==null || ConstansUtil.EMPTY_STR.equals(attention.getAttentedUser().getUserId())){
            return new AttentionExecution(BaseStateEnum.EMPTY_INFO);
        }
        if (attention.getAttentionUser()==null || ConstansUtil.EMPTY_STR.equals(attention.getAttentionUser().getUserId())){
            return new AttentionExecution(BaseStateEnum.EMPTY_INFO);
        }
        //查重
        List<Attention> list = attentionDao.checkRepeat(attention);
        if (list.size()==0){
            //添加创建时间
            attention.setAttentionCreateTime(new Date());
            //调用dao层添加关注记录
            int result = attentionDao.insertAttention(attention);
            if (result!=0){
                return new AttentionExecution(BaseStateEnum.SUCCESS);
            }else{
                return new AttentionExecution(BaseStateEnum.INNER_ERROR);
            }
        }else {
            return new AttentionExecution(BaseStateEnum.ILLEGAL_REQUEST);
        }
    }

    /**
     * 删除关注记录
     * @param attention
     * @return
     */
    @Override
    public AttentionExecution delAttention(Attention attention) {
        //参数校验
        if (attention.getAttentionId()==null||attention.getAttentionId()==0){
            return new AttentionExecution(BaseStateEnum.EMPTY_INFO);
        }
        if (attention.getAttentionUser()==null || ConstansUtil.EMPTY_STR.equals(attention.getAttentionUser().getUserId())){
            return new AttentionExecution(BaseStateEnum.EMPTY_INFO);
        }
        //调用dao层删除关注记录
        int result = attentionDao.delAttention(attention);
        if (result!=0){
            AttentionExecution attentionExecution = new AttentionExecution(BaseStateEnum.SUCCESS);
            return attentionExecution;
        }else{
            return new AttentionExecution(BaseStateEnum.INNER_ERROR);
        }
    }
}
