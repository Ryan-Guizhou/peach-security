package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachSensitiveDO;
import com.peach.security.qo.PeachSensitiveQO;
import org.springframework.stereotype.Indexed;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/11 22:17
 */
@Indexed
@MyBatisDao
public interface PeachSensitiveDao extends BaseDao<PeachSensitiveDO> {

    List<PeachSensitiveDO> selectByQO(PeachSensitiveQO peachSensitiveQO);


}
