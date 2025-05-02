package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachRoleDO;
import com.peach.security.qo.PeachRoleQO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/22 18:36
 */
@MyBatisDao
public interface PeachRoleDao extends BaseDao<PeachRoleDO> {


    List<PeachRoleDO> selectByQO(PeachRoleQO qo);

    List<PeachRoleDO> selectByUserCode(String userCode);
}
