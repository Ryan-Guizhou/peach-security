package com.peach.security.api;

import com.peach.common.response.Response;
import com.peach.security.vo.LoginConfigVO;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 16:20
 */
public interface IImageValidateService {

    /**
     * 初始化验证码
     *
     * @param uniqueKey 惟一键
     * @return
     */
    void initCaptcha(String uniqueKey,LoginConfigVO loginConfigVO);


    /**
     * 校验验证码
     *
     * @param token 验证码token
     * @param x 验证码x坐标
     * @param y 验证码y坐标
     * @return
     */
    Response checkCaptcha(String token,int x,int y);

}
