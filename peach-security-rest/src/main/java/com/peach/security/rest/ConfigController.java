package com.peach.security.rest;

import com.peach.common.response.Response;
import com.peach.security.api.IConfigService;
import com.peach.security.entity.PeachConfigDO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/1 13:29
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private IConfigService configService;

    @ApiOperation("获取页面配置信息")
    @GetMapping("/info")
    public Response info() {
        PeachConfigDO configInfo = configService.getConfigInfo();
        return Response.success().setData(configInfo);
    }

    @ApiOperation("修改页面配置信息")
    @GetMapping("/modify")
    public Response modify(@RequestBody PeachConfigDO configInfo) {
        configService.modifyConfigInfo(configInfo);
        return Response.success();
    }


}
