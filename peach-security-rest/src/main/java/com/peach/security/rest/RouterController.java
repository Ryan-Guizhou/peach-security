package com.peach.security.rest;

import com.peach.common.response.PageResult;
import com.peach.common.response.Response;
import com.peach.security.api.IRouterService;
import com.peach.security.entity.PeachRouterDO;
import com.peach.security.qo.PeachRouterQO;
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
 * @CreateTime 2025/5/23 23:21
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/router")
public class RouterController {

    @Resource
    private IRouterService routerService;

    @ApiOperation("分页查询路由")
    @PostMapping("/selectByPage")
    public Response selectByPage(PeachRouterQO peachRouterQO) {
        PageResult<PeachRouterDO> pageResult = routerService.selectByPage(peachRouterQO);
        return Response.success().setData(pageResult);
    }


    @ApiOperation("根据id列表删除路由")
    @PostMapping("/delByIds")
    public Response delByIds(List<String> ids) {
        routerService.deleteByIds(ids);
        return Response.success();
    }

    @ApiOperation("新增路由")
    @PostMapping("/insert")
    public Response insert(List<String> ids) {
        routerService.deleteByIds(ids);
        return Response.success();
    }

    @ApiOperation("删除路由信息")
    @PostMapping("/batchDel")
    public Response batchDel(List<String> ids) {
        routerService.deleteByIds(ids);
        return Response.success();
    }

    @ApiOperation("根据id获取路由信息")
    @GetMapping("/getById/{routerId}")
    public Response getById(@PathVariable("") String routerId) {
        PeachRouterDO peachRouterDO = routerService.selectById(routerId);
        return Response.success().setData(peachRouterDO);
    }

    @ApiOperation("修改路由")
    @PostMapping("/update")
    public Response update(@RequestBody PeachRouterDO peachRouterDO) {
        routerService.update(peachRouterDO);
        return Response.success();
    }
}
