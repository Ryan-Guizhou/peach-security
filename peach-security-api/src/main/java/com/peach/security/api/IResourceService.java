package com.peach.security.api;

import com.peach.common.response.Response;
import com.peach.security.entity.PeachAppResourceDO;
import com.peach.security.vo.ResourceVO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 0:42
 */
public interface IResourceService {

    /**
     * 删除资源信息
     * @param resourceIdList
     * @return
     */
    Response del(List<String> resourceIdList);

    /**
     * 新增资源
     * @param resourceDO
     * @return
     */
    Boolean insert(PeachAppResourceDO resourceDO);

    /**
     * 修改资源
     * @param resourceDO
     * @return
     */
    Response modify(PeachAppResourceDO resourceDO);

    /**
     * 根据功能编码查询资源信息,isDeleted 为null 时查询所有
     *
     * @param funcCode 资源编码
     * @param isDeleted 是否删除 1是 0否
     * @return
     */
    PeachAppResourceDO getByFuncCode(String funcCode,Integer isDeleted);

    /**
     * 通过角色编码获取资源
     * @param roleCodeList
     * @return
     */
    List<ResourceVO> getByRoleCode(List<String> roleCodeList);
}
