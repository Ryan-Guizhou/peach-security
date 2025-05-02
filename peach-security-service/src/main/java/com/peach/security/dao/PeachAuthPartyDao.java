package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachAuthPartyDO;
import org.springframework.stereotype.Indexed;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 15:37
 */
@Indexed
@MyBatisDao
public interface PeachAuthPartyDao extends BaseDao<PeachAuthPartyDO> {

    Integer bindCount(List<String> roleCodeList);

    void batchDelByRoleCodeList(List<String> roleCodeList);
}
