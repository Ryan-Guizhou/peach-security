package com.peach.security.rest;

import cn.dev33.satoken.stp.StpUtil;
import com.peach.common.anno.HandleLogin;
import com.peach.common.response.Response;
import com.peach.common.util.IDGenerator;
import com.peach.common.util.IpUtil;
import com.peach.security.LoginRequestInfo;
import com.peach.security.RegisterRequestInfo;
import com.peach.security.api.ILoginService;
import com.peach.security.exception.AuthorityException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 11:05
 */
@Slf4j
@Indexed
@RestController
@RequestMapping()
public class LoginController {

    @Resource
    private ILoginService loginService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    @HandleLogin
    public Response login(@RequestBody LoginRequestInfo loginRequestInfo, HttpServletRequest request) {
        try{
            String remoteIp = IpUtil.getIpAddr(request);
            loginRequestInfo.setRemoteIp(remoteIp);
            return loginService.login(loginRequestInfo);
        } catch (AuthorityException ex) {
            return Response.fail().setMsg(ex.getExtMsg());
        } catch (Exception ex) {
            log.error("系统错误", ex);
            return Response.fail().setMsg("系统错误");
        }
    }


    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Response register(@RequestBody RegisterRequestInfo registerRequestInfo) {
        try {
            return loginService.register(registerRequestInfo);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/logout")
    @ApiOperation("登出")
    public Response logout(HttpServletRequest request) {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
            return Response.success().setMsg("退出成功");
        }
        return Response.success();
    }

    @PostMapping("/init")
    @ApiOperation("初始化资源")
    public Response init() {
        String uniqueKey = IDGenerator.UUID();
        return loginService.init(uniqueKey);
    }
}
