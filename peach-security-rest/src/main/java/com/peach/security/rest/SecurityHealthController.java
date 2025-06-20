package com.peach.security.rest;

import com.peach.common.response.Response;
import com.peach.common.util.DateUtil;
import com.peach.common.util.IDGenerator;
import com.peach.fileservice.impl.AbstractFileStorageService;
import com.peach.security.api.IUserAvatarHistory;
import com.peach.security.common.UserEnum;
import com.peach.security.entity.PeachUserAvatarHistoryDO;
import com.peach.security.util.UserAvarGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/21 0:34
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/security")
public class SecurityHealthController {


    @Resource
    private IUserAvatarHistory userAvatarHistory;

    @Resource
    private AbstractFileStorageService abstractFileStorageService;

    @PostMapping("/healthCheck")
    @ApiOperation("安全模块健康检查")
    public Response healthCheck() {
        return Response.commonResponse("I'm healthy");
    }

    @PostMapping("/avator/auto/upload/{name}")
    @ApiOperation("写入当前头像")
    public Response write(@PathVariable("name") String name) {
        BufferedImage bufferedImage = UserAvarGenerator.generateAvatar(name);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage,"png",os);
            String upload = abstractFileStorageService.upload(new ByteArrayInputStream(os.toByteArray()), "/avatar", name + System.nanoTime() + ".png");
            PeachUserAvatarHistoryDO peachUserAvatarHistoryDO = new PeachUserAvatarHistoryDO();
            peachUserAvatarHistoryDO.setChangeReason(UserEnum.ChanegReason.SYSTEM_UPLOAD.getValue());
            boolean isExist = userAvatarHistory.existAvatar(peachUserAvatarHistoryDO);
            if (!isExist) {
                peachUserAvatarHistoryDO.setId(IDGenerator.UUID());
                peachUserAvatarHistoryDO.setAvatarUrl(abstractFileStorageService.getUrlByKey(upload));
                peachUserAvatarHistoryDO.setCreator("Administrator");
                peachUserAvatarHistoryDO.setCreatorName("系统管理员");
                peachUserAvatarHistoryDO.setModifier("Administrator");
                peachUserAvatarHistoryDO.setModifierName("系统管理员");
                peachUserAvatarHistoryDO.setUserId("12310948129412841902");
                peachUserAvatarHistoryDO.setIsCurrent(2);
                peachUserAvatarHistoryDO.setChangeReason(UserEnum.ChanegReason.SYSTEM_UPLOAD.getValue());
                peachUserAvatarHistoryDO.setCreateTime(DateUtil.nowTime());
                userAvatarHistory.insert(peachUserAvatarHistoryDO);
            }
            return Response.success().setData(upload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/avator/delete/{id}")
    @ApiOperation("删除头像")
    public Response delete(@PathVariable("id") String id) {
        userAvatarHistory.deleteById(id);
        return Response.success();
    }
}
