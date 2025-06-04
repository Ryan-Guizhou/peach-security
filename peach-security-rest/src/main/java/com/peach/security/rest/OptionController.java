package com.peach.security.rest;

import com.peach.common.response.PageResult;
import com.peach.common.response.Response;
import com.peach.security.api.IOptionService;
import com.peach.security.entity.PeachOptionDO;
import com.peach.security.qo.PeachOptionQO;
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
@RequestMapping("/option")
public class OptionController {

    @Resource
    private IOptionService optionService;

    @ApiOperation("分页查询值集")
    @PostMapping("/selectByPage")
    public Response selectByPage(@RequestBody PeachOptionQO peachOptionQO) {
        PageResult<PeachOptionDO> pageResult = optionService.selectByPage(peachOptionQO);
        return Response.success().setData(pageResult);
    }

    @ApiOperation("新增值集")
    @PostMapping("/insert")
    public Response insert(@RequestBody PeachOptionDO peachOptionDO) {
        optionService.insert(peachOptionDO);
        return Response.success();
    }

    @ApiOperation("删除值集")
    @PostMapping("/batchDel")
    public Response batchDel(@RequestBody List<String> ids) {
        optionService.deleteByIds(ids);
        return Response.success();
    }

    @ApiOperation("根据id获取值集")
    @GetMapping("/getById/{id}")
    public Response getById(@PathVariable("id") String id) {
        PeachOptionDO peachOptionDO = optionService.selectById(id);
        return Response.success().setData(peachOptionDO);
    }

    @ApiOperation("修改值集")
    @PostMapping("/update")
    public Response update(@RequestBody PeachOptionDO peachOptionDO) {
        optionService.update(peachOptionDO);
        return Response.success();
    }

}
