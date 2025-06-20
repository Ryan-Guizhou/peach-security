package com.peach.security.api;

import com.peach.security.entity.PeachUserAvatarHistoryDO;
import com.peach.security.qo.PeachUserAvatarHistoryQO;
import com.peach.security.vo.UserAvatarHistoryVO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/20 23:51
 */
public interface IUserAvatarHistory {

    /**
     * 查询用户的历史头像,最多保留十张
     * @param userId
     * @return
     */
    List<UserAvatarHistoryVO> selectByUserId(String userId);

    /**
     * 新增用户历史头像信息
     * @param peachWitheListDO
     * @return
     */
    void insert(PeachUserAvatarHistoryDO peachWitheListDO);


    /**
     * 根据id删除用户历史头像信息
     * @param id
     * @return
     */
    void deleteById(String id);

    /**
     * 根据条件判断该附件是否存在
     * @param peachWitheListDO
     * @return
     */
    boolean existAvatar(PeachUserAvatarHistoryDO peachWitheListDO);

    /**
     * 根据QO删除
     * @param peachWitheListQO
     */
    void deleteByQO(PeachUserAvatarHistoryQO peachWitheListQO);

}
