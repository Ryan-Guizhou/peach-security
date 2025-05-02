package com.peach.security.api;


import com.peach.common.response.PageResult;
import com.peach.common.response.Response;
import com.peach.security.dto.UserDTO;
import com.peach.security.entity.PeachUserDO;
import com.peach.security.qo.UserQO;
import com.peach.security.vo.UserVO;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/2/26 17:39
 */
public interface IUserService {

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    UserVO getUserInfo(String userId);

    /**
     * 获取用户分页列表
     * @param userQO
     * @return
     */
    PageResult<PeachUserDO> getUserList(UserQO userQO);

    /**
     * 更新用户信息
     * @param userDO
     */
    void updateUserInfo(PeachUserDO userDO);


    /**
     * 用户登录
     * @param userAccount
     * @param password
     * @return
     */
    PeachUserDO checkLogin(String userAccount, String password);

    /**
     * 校验新增用户入参
     * @param userDTO
     * @return
     */
    String validateUserDTO(UserDTO userDTO);

    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    Response insert(UserDTO userDTO);

    /**
     * 根据用户账号获取用户信息
     *
     * @param userAccount
     * @return
     */
    PeachUserDO getUserByUserAccount(String userAccount);

    /**
     * 注册用户
     *
     * @param userAccount 用户账号
     * @param password 密码
     * @return
     */
    PeachUserDO register(String userAccount,String password);

}
