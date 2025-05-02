package com.peach.security.rest;

import com.peach.common.response.Response;
import com.peach.security.api.IFunctionService;
import com.peach.security.entity.PeachAppFunctionDO;
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
@RequestMapping("/function")
public class FunctionController {

    @Resource
    private IFunctionService functionService;

    @PostMapping("/addFuntion")
    @ApiOperation("新增功能")
    public Response addFunction(@RequestBody PeachAppFunctionDO appFunctionDO) {
        return Response.success();
    }

    @PostMapping("/delFunction")
    @ApiOperation("删除功能")
    public Response delFunction(@RequestBody PeachAppFunctionDO appFunctionDO) {
        return Response.success();
    }


    @PostMapping("/modifyResource")
    @ApiOperation("修改功能")
    public Response modifyResource(@RequestBody PeachAppFunctionDO appFunctionDO) {
        return Response.success();
    }

    @GetMapping("/getById/{fucnId}")
    @ApiOperation("根据ID获取详情")
    public Response getFuncById(@PathVariable("funcId") Integer funcId) {
        return Response.success();
    }

}
