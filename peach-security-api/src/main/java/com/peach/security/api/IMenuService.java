package com.peach.security.api;

import com.peach.common.response.Response;
import com.peach.security.entity.PeachMenuDO;
import com.peach.security.qo.PeachMenuQO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 20:19
 */
public interface IMenuService {

    List<PeachMenuDO> selectByQO(PeachMenuQO qo);

    List<PeachMenuDO> selectByRoleCodeList(List<String> roleCodeList);

    Response deleteById(String menuId);

    Response updateMenu(PeachMenuDO menuDO);

    Response addMenu(PeachMenuDO menuDO);

    PeachMenuDO selectById(String menuId);
}
