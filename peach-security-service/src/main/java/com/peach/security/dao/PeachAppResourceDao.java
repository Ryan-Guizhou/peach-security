package com.peach.security.dao;


import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachAppResourceDO;
import org.springframework.stereotype.Indexed;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 0:32
 */
@Indexed
@MyBatisDao
public interface PeachAppResourceDao extends BaseDao<PeachAppResourceDO> {

}
