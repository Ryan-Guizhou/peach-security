package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachLanguageDO;
import org.springframework.stereotype.Indexed;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/31 18:15
 */
@Indexed
@MyBatisDao
public interface PeachLanguageDao extends BaseDao<PeachLanguageDO> {

}
