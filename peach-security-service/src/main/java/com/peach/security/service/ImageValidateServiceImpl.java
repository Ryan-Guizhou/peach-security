package com.peach.security.service;

import com.peach.common.response.Response;
import com.peach.security.api.IImageValidateService;
import com.peach.security.vo.LoginConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 17:44
 */
@Slf4j
@Indexed
@Service
public class ImageValidateServiceImpl implements IImageValidateService {


    @Override
    public LoginConfigVO initCaptcha(String uniqueKey) {
        return null;
    }


    @Override
    public LoginConfigVO getCaptcha(String uniqueKey) {
        return null;
    }


    @Override
    public Response checkCaptcha(String token, int x, int y) {
        return null;
    }
}
