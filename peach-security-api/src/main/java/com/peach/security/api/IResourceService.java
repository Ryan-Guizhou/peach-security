package com.peach.security.api;

import com.peach.common.response.Response;
import com.peach.security.entity.PeachAppResourceDO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 0:42
 */
public interface IResourceService {

    Response delResource(List<String> resourceIdList);

    Boolean addResource(PeachAppResourceDO resourceDO);

    Response updateResource(PeachAppResourceDO resourceDO);

    /**
     * 根据功能编码查询资源信息,isDeleted 为null 时查询所有
     *
     * @param funcCode 资源编码
     * @param isDeleted 是否删除 1是 0否
     * @return
     */
    PeachAppResourceDO getByFuncCode(String funcCode,Integer isDeleted);
}
