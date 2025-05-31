package com.peach.security.api;

import com.peach.security.entity.PeachLanguageDO;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/31 18:17
 */
public interface IPeachLanguage {

    /**
     * 获取语言信息
     * @return
     */
    PeachLanguageDO getPeachLanguage();

    /**
     * 设置语言信息
     * @param peachLanguage
     */
    void updatePeachLanguage(PeachLanguageDO peachLanguage);
}
