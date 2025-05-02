package com.peach.security.rest;

import com.peach.common.response.Response;
import com.peach.security.api.IUserService;
import com.peach.security.dto.UserDTO;
import com.peach.security.qo.PeachUserQO;
import com.peach.security.qo.UserQO;
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
    public Response getUserList(UserQO userQO){
//        PageResult<UserDO> userList = userService.getUserList(userQO);
        return Response.success();
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    public Response addUser(UserDTO userDTO){
        userService.insert(userDTO);
        return Response.success();
    }


    @PostMapping("/delById/{userId}")
    @ApiOperation("根据ID刪除用戶")
    public Response delRole(@PathVariable("userId") String userId) {
        return Response.success();
    }

    @DeleteMapping("/batchDel")
    @ApiOperation("批量删除用户")
    public Response batchDelUser(@RequestBody PeachUserQO userQO) {
        return Response.success();
    }

    @PostMapping("/modifyUser")
    @ApiOperation("修改用户")
    public Response modifyUser(@RequestBody PeachUserQO userQO) {
        return Response.success();
    }

    @GetMapping("/getById/{userId}")
    @ApiOperation("根据ID获取用户详情")
    public Response getUserByID(@PathVariable String roleId) {
        return Response.success();
    }
}
