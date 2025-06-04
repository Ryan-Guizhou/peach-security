package com.peach.security.rest;

import com.peach.common.response.PageResult;
import com.peach.common.response.Response;
import com.peach.security.api.IWitheListService;
import com.peach.security.entity.PeachWitheListDO;
import com.peach.security.qo.PeachWitheListQO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description 白名单
 * @CreateTime 2025/5/24 0:02
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/allow")
public class AllowListController {

    @Resource
    private IWitheListService witheListService;

    @ApiOperation("分页查询白名单配置")
    @PostMapping("/selectByPage")
    public Response selectByPage(@RequestBody PeachWitheListQO peachWitheListQO) {
        PageResult<PeachWitheListDO> pageResult = witheListService.selectByPage(peachWitheListQO);
        return Response.success().setData(pageResult);
    }


    @ApiOperation("新增白名单配置")
    @PostMapping("/insert")
    public Response insert(@RequestBody PeachWitheListDO peachWitheListDO) {
        witheListService.insert(peachWitheListDO);
        return Response.success();
    }

    @ApiOperation("删除白名单配置信息")
    @PostMapping("/batchDel")
    public Response batchDel(@RequestBody List<String> ids) {
        witheListService.deleteByIds(ids);
        return Response.success();
    }

    @ApiOperation("根据id获取白名单配置信息")
    @GetMapping("/getById/{id}")
    public Response getById(@PathVariable("id") String id) {
        PeachWitheListDO peachWitheListDO = witheListService.selectById(id);
        return Response.success().setData(peachWitheListDO);
    }

    @ApiOperation("修改白名单配置")
    @PostMapping("/update")
    public Response update(@RequestBody PeachWitheListDO peachWitheListDO) {
        witheListService.update(peachWitheListDO);
        return Response.success();
    }

}
