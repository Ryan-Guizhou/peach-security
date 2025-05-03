package com.peach.security.rest;

import com.peach.common.response.PageResult;
import com.peach.common.response.Response;
import com.peach.security.api.IUserService;
import com.peach.security.entity.PeachUserDO;
import com.peach.security.qo.PeachUserQO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 11:04
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("/list")
    @ApiOperation("分页查询用户列表")
    public Response getUserList(PeachUserQO userQO){
        PageResult<PeachUserDO> userList = userService.getUserList(userQO);
        return Response.success().setData(userList);
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    public Response addUser(PeachUserDO peachUserDO){
        return userService.addUser(peachUserDO);
    }

    @PostMapping("/delById/{userId}")
    @ApiOperation("根据ID刪除用戶")
    public Response delRole(@PathVariable("userId") String userId) {
        return userService.deleteById(userId);
    }

    @PostMapping("/batchDel")
    @ApiOperation("批量删除用户")
    public Response batchDelUser(@RequestBody PeachUserQO userQO) {
        return Response.success();
    }

    @PostMapping("/modifyUser")
    @ApiOperation("修改用户")
    public Response modifyUser(@RequestBody PeachUserDO userDO) {
        return userService.updateUser(userDO);
    }

    @PostMapping("/getById/{userId}")
    @ApiOperation("根据ID获取用户详情")
    public Response getUserByID(@PathVariable("userId") String userId) {
        PeachUserDO userInfo = userService.getUserInfo(userId);
        return Response.success().setData(userInfo);
    }
}
