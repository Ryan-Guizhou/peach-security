package com.peach.security.service;

import com.peach.common.util.InputParamChecker;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.security.api.IConfigService;
import com.peach.security.dao.PeachConfigDao;
import com.peach.security.entity.PeachConfigDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/1 13:20
 */
@Slf4j
@Indexed
@Service
public class ConfigServiceImpl implements IConfigService {

    @Resource
    private PeachConfigDao peachConfigDao;

    @Override
    public PeachConfigDO getConfigInfo() {
        PeachConfigDO peachConfigDO = new PeachConfigDO();
        List<PeachConfigDO> peachConfigDOList = peachConfigDao.select(peachConfigDO);
        return PeachCollectionUtil.isNotEmpty(peachConfigDOList) ? peachConfigDOList.get(0) : new PeachConfigDO();
    }

    @Override
    public void modifyConfigInfo(PeachConfigDO configInfo) {
        try{
            InputParamChecker.of(configInfo).checkFields("id");
        }catch (Exception e){
            log.error("params error"+e.getMessage(), e);
        }
        PeachConfigDO peachConfigDO = peachConfigDao.selectById(configInfo.getId());
        if(peachConfigDO != null){
            log.error("peachConfigDO is not exist,id is:[{}]",configInfo.getId());
            throw new RuntimeException("peachConfigDO is not exist");
        }
        peachConfigDao.updateById(configInfo);
    }
}
