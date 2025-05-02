package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachUserDO;
import com.peach.security.qo.UserQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Indexed;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 26 2月 2025 20:09
 */
@Indexed
@MyBatisDao
public interface PeachUserDao extends BaseDao<PeachUserDO> {

    List<PeachUserDO> selectByQO(UserQO userQO);

    PeachUserDO getUserByAccount(@Param("userAccount") String userAccount);

}
