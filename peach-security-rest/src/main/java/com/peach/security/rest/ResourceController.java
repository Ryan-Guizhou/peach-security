package com.peach.security.rest;

import com.peach.common.constant.PubCommonConst;
import com.peach.common.response.Response;
import com.peach.security.api.IResourceService;
import com.peach.security.entity.PeachAppResourceDO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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


    @PostMapping("/insert")
    @ApiOperation("新增资源")
    public Response insert(@RequestBody PeachAppResourceDO resourceDO) {
        return Response.success();
    }

    @PostMapping("/delResource")
    @ApiOperation("删除资源")
    public Response delResource(@RequestBody List<String> resourceIdList) {
        return resourceService.delResource(resourceIdList);

    }

    @PostMapping("/modify")
    @ApiOperation("修改资源")
    public Response modify(@RequestBody PeachAppResourceDO resourceDO) {
        return resourceService.modify(resourceDO);
    }

    @GetMapping("/getByFuncCode/{funcCode}")
    @ApiOperation("根据功能编码获取资源详情")
    public Response getByFuncCode(@PathVariable("funcCode") String funcCode) {
        PeachAppResourceDO resourceDO = resourceService.getByFuncCode(funcCode, PubCommonConst.LOGIC_FLASE);
        return Response.success().setData(resourceDO);
    }


}
