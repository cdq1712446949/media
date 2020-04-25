package com.cdq.dao;

import com.cdq.model.UserComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCommentDao {

    /**
     * 根据创建时间降序排序
     * @param userComment 查询条件
     * @param rowIndex 行数索引
     * @param  pageSize 数量索引
     * @return 结果
     */
    List<UserComment> queryUserCommentList(@Param("userComment") UserComment userComment,
                                           @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 添加用户评论
     * @param userComment
     * @return
     */
    int insertUserComment(UserComment userComment);

}
