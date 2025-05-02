package com.peach.security.api;

import com.peach.security.entity.PeachAppResourceDO;

import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 0:42
 */
public interface IResourceService {

    Boolean delResource(List<String> resourceIdList);

    Boolean addResource(PeachAppResourceDO resourceDO);

    Boolean updateResource(PeachAppResourceDO resourceDO);
}
