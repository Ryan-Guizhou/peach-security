package com.peach.security.api;

import com.peach.common.response.PageResult;
import com.peach.security.entity.PeachRouterDO;
import com.peach.security.qo.PeachRouterQO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/23 23:01
 */
public interface IRouterService {

    /**
     * 查询所有的路由信息
     * @return
     */
    List<PeachRouterDO> selectAll();

    /**
     * 分页查询路由
     * @param peachRouterQO
     * @return
     */
    PageResult<PeachRouterDO> selectByPage(PeachRouterQO peachRouterQO);

    /**
     * 新增路由
     * @param peachRouterDO
     * @return
     */
    void insert(PeachRouterDO peachRouterDO);

    /**
     * 更新路由
     * @param peachRouterDO
     * @return
     */
    void update(PeachRouterDO peachRouterDO);

    /**
     * 根据id批量删除路由
     * @param idList
     * @return
     */
    void deleteByIds(List<String> idList);


    /**
     * 根据id获取路由详细信息
     * @param id
     * @return
     */
    PeachRouterDO selectById(String id);



}
