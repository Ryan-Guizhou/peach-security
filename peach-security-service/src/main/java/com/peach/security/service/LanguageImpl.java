package com.peach.security.service;

import com.peach.common.util.InputParamChecker;
import com.peach.security.api.IPeachLanguage;
import com.peach.security.dao.PeachLanguageDao;
import com.peach.security.entity.PeachLanguageDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/31 18:19
 */
@Slf4j
@Indexed
@Service
public class LanguageImpl implements IPeachLanguage {

    @Resource
    private PeachLanguageDao peachLanguageDao;

    @Override
    public PeachLanguageDO getPeachLanguage() {
        PeachLanguageDO peachLanguageDO = new PeachLanguageDO();
        List<PeachLanguageDO> peachLanguageDOList = peachLanguageDao.select(peachLanguageDO);
        return peachLanguageDOList.size() > 0 ? peachLanguageDOList.get(0) : new PeachLanguageDO();
    }

    @Override
    public void updatePeachLanguage(PeachLanguageDO peachLanguage) {
        try {
            InputParamChecker.of(peachLanguage).checkFields("id");
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        peachLanguageDao.update(peachLanguage);
    }
}
