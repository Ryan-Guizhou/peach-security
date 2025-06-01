package com.peach.security.dao;

import com.peach.common.BaseDao;
import com.peach.common.anno.MyBatisDao;
import com.peach.security.entity.PeachMenuDO;
import com.peach.security.qo.PeachMenuQO;
import com.peach.security.vo.MenuVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 20:22
 */
@MyBatisDao
public interface PeachMenuDao extends BaseDao<PeachMenuDO> {

    /**
     * qo查询 不分页
     *
     * @param qo 查询对象
     * @return
     */
    List<PeachMenuDO> selectByQO(PeachMenuQO qo);

    /**
     * 根据roleIdList 查询已授权菜单
     * @param roleCodeList 角色集合
     * @return
     */
    List<MenuVO> selectByRoleCodeList(@Param("roleCodeList") List<String> roleCodeList);

    PeachMenuDO selectByMenuCode(@Param("menuCode") String menuCode);
}
