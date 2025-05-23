package com.peach.security.api;

import com.peach.common.response.Response;
import com.peach.security.entity.PeachRoleDO;
import com.peach.security.qo.PeachRoleQO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 20:14
 */
public interface IRoleService {

    List<PeachRoleDO> selectByUserCode(String userCode);

    PeachRoleDO selectById(String roleId);

    List<PeachRoleDO> selectByQO(PeachRoleQO qo);

    Response deleteById(String roleId);

    Response deleteByQO(PeachRoleQO qo);

    Response modify(PeachRoleDO roleDO);

    Response insert(PeachRoleDO roleDO);

}
