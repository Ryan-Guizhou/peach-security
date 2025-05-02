package com.peach.security.rest;

import com.peach.common.response.Response;
import com.peach.security.api.IRoleService;
import com.peach.security.entity.PeachRoleDO;
import com.peach.security.qo.PeachRoleQO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 1:33
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @PostMapping("/addRole")
    @ApiOperation("新增角色")
    public Response addRole(@RequestBody PeachRoleDO roleDO) {
        return roleService.addRole(roleDO);
    }

    @DeleteMapping("/delById/{roleId}")
    @ApiOperation("根据ID删除角色")
    public Response delRole(@PathVariable("roleId") String roleId) {
        return roleService.deleteById(roleId);
    }

    @DeleteMapping("/batchDelRole")
    @ApiOperation("批量删除删除角色")
    public Response batchDelRole(@RequestBody PeachRoleQO roleQO) {
        return Response.success();
    }

    @PostMapping("/modifyRule")
    @ApiOperation("修改角色")
    public Response modifyRole(@RequestBody PeachRoleDO roleDO) {
        return roleService.updateRole(roleDO);
    }

    @GetMapping("/getById/{roleId}")
    @ApiOperation("根据ID获取角色详情")
    public Response getRoleByID(@PathVariable String roleId) {
        PeachRoleDO peachRoleDO = roleService.selectById(roleId);
        return Response.success().setData(peachRoleDO);
    }
}
