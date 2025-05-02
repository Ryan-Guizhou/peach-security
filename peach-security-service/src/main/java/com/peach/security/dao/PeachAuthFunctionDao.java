package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachAuthFunctionDO;
import org.springframework.stereotype.Indexed;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 1:15
 */
@Indexed
@MyBatisDao
public interface PeachAuthFunctionDao extends BaseDao<PeachAuthFunctionDO> {


    void batchDelByRoleCodeList(List<String> roleCodeList);
}
