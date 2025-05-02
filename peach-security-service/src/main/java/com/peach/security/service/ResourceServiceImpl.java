package com.peach.security.service;

import com.google.common.collect.Lists;
import com.peach.common.constant.PubCommonConst;
import com.peach.common.exception.ValidateException;
import com.peach.common.util.DateUtil;
import com.peach.common.util.IDGenerator;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.security.api.IResourceService;
import com.peach.security.common.ResourceTypeEnum;
import com.peach.security.dao.PeachAppResourceDao;
import com.peach.security.dao.PeachAuthFunctionDao;
import com.peach.security.dao.PeachAuthResourceDao;
import com.peach.security.entity.PeachAppResourceDO;
import com.peach.security.entity.PeachAuthFunctionDO;
import com.peach.security.entity.PeachAuthResourceDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description // TODO
 * @CreateTime 2025/5/2 0:51
 */
@Slf4j
@Indexed
@Service
public class ResourceServiceImpl implements IResourceService {

    /**
     * 默认资源是不开启的
     */
    private final String RESOURCE_DEFAULT_STATE = "hide";

    /**
     * 默认API资源是展示的
     */
    private final String API_DEFAULT_STATE = "show";

    @Resource
    private PeachAppResourceDao peachAppResourceDao;

    @Resource
    private PeachAuthResourceDao peachAuthResourceDao;

    @Resource
    private PeachAuthFunctionDao peachAuthFunctionDao;

    @Override
    public Boolean delResource(List<String> resourceIdList) {
        return null;
    }

    @Override
    public Boolean addResource(PeachAppResourceDO resourceDO) {
        PeachAppResourceDO query = new PeachAppResourceDO();
        query.setFuncCode(resourceDO.getFuncCode());
        query.setResourceCode(resourceDO.getResourceCode());
        List<PeachAppResourceDO> existResourceList = peachAppResourceDao.select(query);
        if (PeachCollectionUtil.isNotEmpty(existResourceList) && existResourceList.size() > 0) {
            log.error("资源编码:[｛｝],资源名称:[{}]已存在", resourceDO.getResourceCode(),resourceDO.getFuncCode());
            throw new ValidateException("资源已存在");
        }
        resourceDO.setResourceId(IDGenerator.UUID());
        resourceDO.setCreateTime(DateUtil.nowTime());
        resourceDO.setModifyTime(DateUtil.nowTime());
        resourceDO.setIsDelete(0);
//        resourceDO.setCreator();
//        resourceDO.setCreatorCode();
        peachAppResourceDao.insert(resourceDO);

        PeachAuthFunctionDO authFunctionDO = new PeachAuthFunctionDO();
        authFunctionDO.setFuncCode(resourceDO.getFuncCode());
        List<PeachAuthFunctionDO> functionDOList = peachAuthFunctionDao.select(authFunctionDO);
        if (PeachCollectionUtil.isEmpty(functionDOList)) {
            return Boolean.TRUE;
        }
        List<PeachAuthResourceDO> authResourceDOList = Lists.newArrayList();
        functionDOList.forEach(authFunction ->{
            PeachAuthResourceDO authResourceDO = new PeachAuthResourceDO();
            authResourceDO.setResourceId(IDGenerator.id());
            authResourceDO.setPartyCode(authFunction.getPartyCode());
            authResourceDO.setFuncCode(resourceDO.getFuncCode());
            authResourceDO.setResourceCode(resourceDO.getResourceCode());
            authResourceDO.setResourceName(resourceDO.getResourceName());
            authResourceDO.setModifyTime(DateUtil.nowTime());
            authResourceDO.setOpType(RESOURCE_DEFAULT_STATE);
            authResourceDO.setIsDelete(PubCommonConst.LOGIC_FLASE);
//            authResourceDO.setCreator();
//            authResourceDO.setCreatorCode();
            if (ResourceTypeEnum.API.getCode().equals(resourceDO.getResourceType())) {
                authResourceDO.setOpType(API_DEFAULT_STATE);
            }
            authResourceDOList.add(authResourceDO);
        });
        if(PeachCollectionUtil.isNotEmpty(authResourceDOList)) {
            peachAuthResourceDao.batchInsert(authResourceDOList);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateResource(PeachAppResourceDO resourceDO) {
        return null;
    }
}
