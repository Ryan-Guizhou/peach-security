package com.peach.security.service;

import com.peach.common.response.Response;
import com.peach.common.util.IDGenerator;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.StringUtil;
import com.peach.security.api.IRoleService;
import com.peach.security.dao.PeachAuthFunctionDao;
import com.peach.security.dao.PeachAuthPartyDao;
import com.peach.security.dao.PeachAuthResourceDao;
import com.peach.security.dao.PeachRoleDao;
import com.peach.security.entity.PeachRoleDO;
import com.peach.security.qo.PeachRoleQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 20:22
 */
@Slf4j
@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    private PeachRoleDao peachRoleDao;

    @Resource
    private PeachAuthPartyDao peachAuthPartyDao;

    @Resource
    private PeachAuthResourceDao peachAuthResourceDao;

    @Resource
    private PeachAuthFunctionDao peachAuthFunctionDao;

    @Override
    public List<PeachRoleDO> selectByUserCode(String userCode) {
        List<PeachRoleDO> peachRoleDOList = peachRoleDao.selectByUserCode(userCode);
        return PeachCollectionUtil.isEmpty(peachRoleDOList) ? Collections.emptyList() : peachRoleDOList;
    }

    @Override
    public PeachRoleDO selectById(String roleId) {
        if (StringUtil.isBlank(roleId)) {
            log.error("角色ID为空");
            return new PeachRoleDO();
        }
        PeachRoleDO peachRoleDO = peachRoleDao.selectById(roleId);
        return peachRoleDO;
    }

    @Override
    public List<PeachRoleDO> selectByQO(PeachRoleQO qo) {
        List<PeachRoleDO> peachRoleDOList = peachRoleDao.selectByQO(qo);
        return PeachCollectionUtil.isEmpty(peachRoleDOList) ? Collections.emptyList() : peachRoleDOList;
    }

    @Override
    public Response deleteById(String roleId) {
        if (StringUtil.isBlank(roleId)) {
            log.error("需要删除的角色不存在");
            return Response.commonResponse("需要删除的角色不存在");
        }
        PeachRoleDO existRoleDO = peachRoleDao.selectById(roleId);
        if (existRoleDO == null) {
            log.error("需要删除的角色不存在");
            return Response.commonResponse("需要删除的角色不存在");
        }
        // 如果已经有用户挂接了这个角色  不允许删除
        List<String> delRoleCodeList = Arrays.asList(existRoleDO.getRoleCode());
        Integer bindCount = peachAuthPartyDao.bindCount(delRoleCodeList);
        if (bindCount > 0) {
            log.error("该角色已被用户使用,不可被删除");
            return Response.commonResponse("该角色已被用户使用,不可被删除");
        }
        // 删除角色本身
        peachRoleDao.delById(roleId);

        // 删除该角色已授权的资源
        peachAuthResourceDao.batchDelByRoleCodeList(delRoleCodeList);
        peachAuthFunctionDao.batchDelByRoleCodeList(delRoleCodeList);
        peachAuthPartyDao.batchDelByRoleCodeList(delRoleCodeList);
        return Response.success().setMsg("角色删除成功");
    }

    @Override
    public Response deleteByQO(PeachRoleQO qo) {
        return null;
    }

    @Override
    public Response updateRole(PeachRoleDO roleDO) {
        peachRoleDao.update(roleDO);
        return Response.success();
    }

    @Override
    public Response addRole(PeachRoleDO roleDO) {
        if(roleDO == null) {
            log.error("新增角色信息为空");
            return Response.fail();
        }
        if (StringUtil.isBlank(roleDO.getRoleCode())) {
            log.error("新增角色编码为空");
            return Response.fail();
        }

        // 校验角色编码是否存在,如果存在 直接返回错误
        String roleCode = roleDO.getRoleCode();
        PeachRoleDO query = new PeachRoleDO();
        query.setRoleCode(roleCode);
        int roleExistCount = peachRoleDao.count(query);
        if (roleExistCount > 0) {
            log.error("角色编码:[{}],已存在,不可新增",roleCode);
            return Response.fail();
        }

        // 补充主键并入库
        roleDO.setRoleId(IDGenerator.UUID());
        peachRoleDao.insert(roleDO);
        return Response.success();
    }
}
