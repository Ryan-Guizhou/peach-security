package com.peach.security.api;

import com.peach.common.response.Response;
import com.peach.security.LoginRequestInfo;
import com.peach.security.RegisterRequestInfo;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 14:56
 */
public interface ILoginService {

    /**
     * 登录
     * @param loginRequestInfo
     * @return
     */
    Response login(LoginRequestInfo loginRequestInfo);

    /**
     * 注册
     * @param registerRequestInfo
     * @return
     */
    Response register(RegisterRequestInfo registerRequestInfo) throws ValidationException;

    /**
     * 登出
     * @param request
     * @return
     */
    Response logout(HttpServletRequest request);

    /**
     * 初始化资源
     * @param uniqueKey 用于标识此次请求的唯一标注 验证码验证一次销毁一次
     * @return
     */
    Response init(String uniqueKey);

    /**
     * 滑块验证
     * @param loginRequestInfo
     * @return
     */
    Response validateImage(LoginRequestInfo loginRequestInfo);
}
