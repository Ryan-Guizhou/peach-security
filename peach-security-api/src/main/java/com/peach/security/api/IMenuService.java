package com.peach.security.api;

import com.peach.common.response.Response;
import com.peach.security.entity.PeachMenuDO;
import com.peach.security.qo.PeachMenuQO;
import com.peach.security.vo.MenuVO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 20:19
 */
public interface IMenuService {

    List<PeachMenuDO> selectByQO(PeachMenuQO qo);

    List<MenuVO> selectByRoleCodeList(List<String> roleCodeList);

    Response deleteById(String menuId);

    Response modify(PeachMenuDO menuDO);

    Response insert(PeachMenuDO menuDO);

    PeachMenuDO selectById(String menuId);
}
