package com.peach.security.rest;

import com.peach.common.response.Response;
import com.peach.security.api.IResourceService;
import com.peach.security.entity.PeachAppResourceDO;
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
@RequestMapping("/resource")
public class ResourceController {

    @Resource
    private IResourceService resourceService;

    @PostMapping("/addResource")
    @ApiOperation("新增资源")
    public Response addResource(@RequestBody PeachAppResourceDO resourceDO) {
        return Response.success();
    }

    @PostMapping("/delResource")
    @ApiOperation("删除资源")
    public Response delResource(@RequestBody PeachAppResourceDO resourceDO) {
        return Response.success();
    }

    @PostMapping("/modifyResource")
    @ApiOperation("修改资源")
    public Response modifyResource(@RequestBody PeachAppResourceDO resourceDO) {
        return Response.success();
    }

    @GetMapping("/getById/{resourceId}")
    @ApiOperation("根据ID获取角色详情")
    public Response getByResourceId(@PathVariable("resourceId") String resourceId) {
//        PeachRoleDO peachRoleDO = roleService.selectById(roleId);
        return Response.success();
    }


}
