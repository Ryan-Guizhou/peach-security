package com.peach.security.api;

import com.peach.security.entity.PeachConfigDO;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/1 13:18
 */
public interface IConfigService {

    /**
     * 获取页面配置信息
     * @return
     */
    PeachConfigDO getConfigInfo();


    /**
     * 修改页面配置信息
     */
    void modifyConfigInfo(PeachConfigDO configInfo);

}
