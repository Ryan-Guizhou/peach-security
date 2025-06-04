package com.peach.security.api;

import com.peach.common.response.PageResult;
import com.peach.security.entity.PeachOptionDO;
import com.peach.security.qo.PeachOptionQO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/4 23:09
 */
public interface IOptionService {

    /**
     * 分页查询值集
     * @param peachOptionQO
     * @return
     */
    PageResult<PeachOptionDO> selectByPage(PeachOptionQO peachOptionQO);

    /**
     * 新增值集
     * @param peachOptionD
     * @return
     */
    void insert(PeachOptionDO peachOptionD);

    /**
     * 更新值集
     * @param peachOptionD
     * @return
     */
    void update(PeachOptionDO peachOptionD);

    /**
     * 根据id批量值集值集
     * @param idList
     * @return
     */
    void deleteByIds(List<String> idList);


    /**
     * 根据id获取值集详细信息
     * @param id
     * @return
     */
    PeachOptionDO selectById(String id);
}
