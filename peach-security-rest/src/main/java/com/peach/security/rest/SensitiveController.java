package com.peach.security.rest;

import com.peach.common.response.PageResult;
import com.peach.common.response.Response;
import com.peach.security.api.ISensitiveService;
import com.peach.security.entity.PeachSensitiveDO;
import com.peach.security.qo.PeachSensitiveQO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description 值集管理
 * @CreateTime 2025/5/24 0:02
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/sensitive")
public class SensitiveController {

    @Resource
    private ISensitiveService sensitiveService;

    @ApiOperation("分页查询敏感词配置")
    @PostMapping("/selectByPage")
    public Response selectByPage(@RequestBody PeachSensitiveQO peachSensitiveQO) {
        PageResult<PeachSensitiveDO> pageResult = sensitiveService.selectByPage(peachSensitiveQO);
        return Response.success().setData(pageResult);
    }

    @ApiOperation("新增敏感词配置")
    @PostMapping("/insert")
    public Response insert(@RequestBody PeachSensitiveDO peachSensitiveDO) {
        sensitiveService.insert(peachSensitiveDO);
        return Response.success();
    }

    @ApiOperation("删除敏感词配置信息")
    @PostMapping("/batchDel")
    public Response batchDel(@RequestBody List<String> ids) {
        sensitiveService.deleteByIds(ids);
        return Response.success();
    }

    @ApiOperation("根据id获取敏感词配置信息")
    @GetMapping("/getById/{id}")
    public Response getById(@PathVariable("id") String id) {
        PeachSensitiveDO peachRouterDO = sensitiveService.selectById(id);
        return Response.success().setData(peachRouterDO);
    }

    @ApiOperation("修改敏感词配置")
    @PostMapping("/update")
    public Response update(@RequestBody PeachSensitiveDO peachSensitiveDO) {
        sensitiveService.update(peachSensitiveDO);
        return Response.success();
    }

}
