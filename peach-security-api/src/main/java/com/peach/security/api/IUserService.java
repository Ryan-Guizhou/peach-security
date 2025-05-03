package com.peach.security.api;


import com.peach.common.response.PageResult;
import com.peach.common.response.Response;
import com.peach.security.entity.PeachUserDO;
import com.peach.security.qo.PeachUserQO;

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
    PeachUserDO getUserInfo(String userId);

    /**
     * 获取用户分页列表
     * @param userQO
     * @return
     */
    PageResult<PeachUserDO> getUserList(PeachUserQO userQO);

    /**
     * 更新用户信息
     * @param userDO
     */
    void updateUserInfo(PeachUserDO userDO);

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


    /**
     * 根据Id删除用户
     * @param userId
     * @return
     */
    Response deleteById(String userId);


    /**
     * 根据ID更新用户信息
     *
     * @param userDO 用户信息
     * @return
     */
    Response updateUser(PeachUserDO userDO);

    /**
     * 新增用户
     *
     * @param userDO 用户信息
     * @return
     */
    Response addUser(PeachUserDO userDO);

    /**
     * 根据userId 获取用户信息
     *
     * @param userId 用户ID
     * @return
     */
    PeachUserDO selectById(String userId);

}
