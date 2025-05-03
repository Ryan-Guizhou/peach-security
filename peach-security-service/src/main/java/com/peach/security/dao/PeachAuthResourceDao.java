package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachAuthResourceDO;
import com.peach.security.qo.PeachAuthResourceQO;
import org.springframework.stereotype.Indexed;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 0:40
 */
@Indexed
@MyBatisDao
public interface PeachAuthResourceDao extends BaseDao<PeachAuthResourceDO> {


    void batchDelByRoleCodeList(List<String> roleCodeList);


    void updateByQO(PeachAuthResourceQO authResourceQO);

    List<PeachAuthResourceDO> selectByQO(PeachAuthResourceQO authResourceQO);

}
