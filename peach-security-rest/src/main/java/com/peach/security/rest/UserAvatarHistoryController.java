package com.peach.security.rest;

import com.peach.common.response.Response;
import com.peach.fileservice.impl.AbstractFileStorageService;
import com.peach.security.api.IUserAvatarHistory;
import com.peach.security.vo.UserAvatarHistoryVO;
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
 * @CreateTime 2025/6/21 0:06
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/userAvatarHistory")
public class UserAvatarHistoryController {

    @Resource
    private IUserAvatarHistory userAvatarHistory;

    @Resource
    private AbstractFileStorageService abstractFileStorageService;

    @GetMapping("/list/｛userId｝")
    @ApiOperation("根据userId获取所有的非当前头像的所有头像")
    public Response getUserAvatarHistoryList(@PathVariable("userId") String userId) {
        List<UserAvatarHistoryVO> avatarHistoryVOList = userAvatarHistory.selectByUserId(userId);
        return Response.success().setData(avatarHistoryVOList);
    }

    @DeleteMapping("/｛id｝")
    @ApiOperation("删除当前选中的头像")
    public Response deleteUserAvatarHistory(@PathVariable("id") String id) {
        userAvatarHistory.deleteById(id);
        return Response.success();
    }
}
