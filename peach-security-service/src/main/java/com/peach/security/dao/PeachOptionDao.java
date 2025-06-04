package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachOptionDO;
import com.peach.security.qo.PeachOptionQO;
import org.springframework.stereotype.Indexed;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/4 22:55
 */
@Indexed
@MyBatisDao
public interface PeachOptionDao extends BaseDao<PeachOptionDO> {


    List<PeachOptionDO> selectByQO(PeachOptionQO optionQO);

}
