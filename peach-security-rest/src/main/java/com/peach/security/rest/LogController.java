package com.peach.security.rest;

import com.github.pagehelper.PageInfo;
import com.peach.common.entity.qo.LogQO;
import com.peach.common.log.AbstractLogService;
import com.peach.common.response.Response;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/15 13:49
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private AbstractLogService abstractLogService;

    @PostMapping("/loginLog")
    @ApiOperation("查询登录日志")
    public Response loginLog(@RequestBody LogQO logQO) {
        PageInfo pageInfo = abstractLogService.selectOperLog(logQO);
        return Response.success().setData(pageInfo);
    }

    @PostMapping("/userOperLog")
    @ApiOperation("查询操作日志")
    public Response userOperLog(@RequestBody LogQO logQO) {
        PageInfo pageInfo = abstractLogService.selectOperLog(logQO);
        return Response.success().setData(pageInfo);
    }
}
