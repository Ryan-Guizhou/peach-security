package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachUserAvatarHistoryDO;
import com.peach.security.qo.PeachUserAvatarHistoryQO;
import com.peach.security.vo.UserAvatarHistoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/20 23:50
 */
@MyBatisDao
public interface PeachUserAvatarHistoryDao extends BaseDao<PeachUserAvatarHistoryDO> {

    List<UserAvatarHistoryVO> selectByUserId(@Param("userId") String userId);

    void deleteByQO(PeachUserAvatarHistoryQO peachUserAvatarHistoryQO);

}
