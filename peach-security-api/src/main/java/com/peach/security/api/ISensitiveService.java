package com.peach.security.api;

import com.peach.common.response.PageResult;
import com.peach.security.entity.PeachSensitiveDO;
import com.peach.security.qo.PeachSensitiveQO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/4 23:54
 */
public interface ISensitiveService {

    /**
     * 分页查询敏感词配置
     * @param peachSensitiveQO
     * @return
     */
    PageResult<PeachSensitiveDO> selectByPage(PeachSensitiveQO peachSensitiveQO);

    /**
     * 新增敏感词配置信息
     * @param peachSensitiveDO
     * @return
     */
    void insert(PeachSensitiveDO peachSensitiveDO);

    /**
     * 更新敏感词配置信息
     * @param peachSensitiveDO
     * @return
     */
    void update(PeachSensitiveDO peachSensitiveDO);

    /**
     * 根据id批量删除敏感词配置信息
     * @param idList
     * @return
     */
    void deleteByIds(List<String> idList);


    /**
     * 根据id获取敏感词配置信息详细信息
     * @param id
     * @return
     */
    PeachSensitiveDO selectById(String id);
}
