package com.peach.security.api;

import com.peach.common.response.PageResult;
import com.peach.security.entity.PeachWitheListDO;
import com.peach.security.qo.PeachWitheListQO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/4 23:08
 */
public interface IWitheListService {

    List<PeachWitheListDO> selectAll();

    /**
     * 分页查询白名单配置
     * @param peachWitheListQO
     * @return
     */
    PageResult<PeachWitheListDO> selectByPage(PeachWitheListQO peachWitheListQO);

    /**
     * 新增白名单配置信息
     * @param peachWitheListDO
     * @return
     */
    void insert(PeachWitheListDO peachWitheListDO);

    /**
     * 更新白名单配置信息
     * @param peachWitheListDO
     * @return
     */
    void update(PeachWitheListDO peachWitheListDO);

    /**
     * 根据id批量删除白名单配置信息
     * @param idList
     * @return
     */
    void deleteByIds(List<String> idList);


    /**
     * 根据id获取白名单配置信息详细信息
     * @param id
     * @return
     */
    PeachWitheListDO selectById(String id);
}
