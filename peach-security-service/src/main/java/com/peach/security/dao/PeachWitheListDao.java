package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachWitheListDO;
import com.peach.security.qo.PeachWitheListQO;
import org.springframework.stereotype.Indexed;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/4 23:02
 */
@Indexed
@MyBatisDao
public interface PeachWitheListDao extends BaseDao<PeachWitheListDO> {

    List<PeachWitheListDO> selectByQO(PeachWitheListQO witheListQO);
}
