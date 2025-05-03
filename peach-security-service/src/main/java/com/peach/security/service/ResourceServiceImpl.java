package com.peach.security.service;

import com.google.common.collect.Lists;
import com.peach.common.constant.PubCommonConst;
import com.peach.common.exception.ValidateException;
import com.peach.common.response.Response;
import com.peach.common.util.*;
import com.peach.security.api.IResourceService;
import com.peach.security.common.ResourceTypeEnum;
import com.peach.security.dao.PeachAppResourceDao;
import com.peach.security.dao.PeachAuthFunctionDao;
import com.peach.security.dao.PeachAuthResourceDao;
import com.peach.security.entity.PeachAppResourceDO;
import com.peach.security.entity.PeachAuthFunctionDO;
import com.peach.security.entity.PeachAuthResourceDO;
import com.peach.security.qo.PeachAuthResourceQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public Response delResource(List<String> resourceIdList) {
        if (PeachCollectionUtil.isEmpty(resourceIdList)) {
            throw new ValidateException("params resourceIdList is empty");
        }
        List<PeachAppResourceDO> appResourceDOList = peachAppResourceDao.selectByIds(resourceIdList);
        if (appResourceDOList == null || appResourceDOList.isEmpty()) {
            throw new ValidateException("需要删除的资源已被删除");
        }
        List<String> resourceCodeList = appResourceDOList.stream().filter(Objects::nonNull).map(PeachAppResourceDO::getResourceCode).collect(Collectors.toList());
        List<String> funcCodeList = appResourceDOList.stream().filter(Objects::nonNull).map(PeachAppResourceDO::getFuncCode).collect(Collectors.toList());
        PeachAuthResourceQO authResourceQO = new PeachAuthResourceQO();
        authResourceQO.setResourceCodeList(resourceCodeList);
        authResourceQO.setFuncCodeList(funcCodeList);
        // 根据这些资源编码和功能编码查询是否存在已授权的资源
        List<PeachAuthResourceDO> authResourceDOList = peachAuthResourceDao.selectByQO(authResourceQO);
        if (!PeachCollectionUtil.isEmpty(authResourceDOList)) {
            List<String> authResourceIdList = appResourceDOList.stream().filter(Objects::nonNull).map(PeachAppResourceDO::getResourceId).collect(Collectors.toList());
            // 删除已授权的资源
            peachAuthResourceDao.delByIds(authResourceIdList);
        }
        // 删除原始资源
        peachAppResourceDao.delByIds(resourceIdList);
        return Response.success();
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
    @Transactional(rollbackFor = Exception.class)
    public Response updateResource(PeachAppResourceDO resourceDO) {
       try {
           // 校验必填参数是否为空
           InputParamChecker.of(resourceDO)
                   .checkField("resourceId")
                   .checkField("resourceCode")
                   .checkField("resourceName")
                   .checkField("resourceType");
       }catch (Exception e) {
           return Response.fail().setMsg(e.getMessage());
       }
       // 校验相同funcCode、resourceCode、resourceType 唯一
        PeachAppResourceDO existResourceDo = peachAppResourceDao.selectById(resourceDO.getResourceId());
        if (existResourceDo == null) {
            throw new ValidateException("该资源已被删除");
        }

        PeachAppResourceDO query = new PeachAppResourceDO();
        query.setFuncCode(resourceDO.getFuncCode());
        query.setResourceCode(resourceDO.getResourceCode());
        query.setResourceType(resourceDO.getResourceType());
        List<PeachAppResourceDO> existResourceList = peachAppResourceDao.select(query);
        if (PeachCollectionUtil.isEmpty(existResourceList)) {
            String errorMsg = String.format("funcCode:[%s],resourceCode:[%s],resouorceType:[%s] has been exist,can't be updated",
                    resourceDO.getFuncCode(), resourceDO.getResourceCode(), resourceDO.getResourceType());
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }

        peachAppResourceDao.updateById(resourceDO);
        // 同步修改已授权已授权的资源
        PeachAuthResourceDO authResourceDO = new PeachAuthResourceDO();
        authResourceDO.setFuncCode(resourceDO.getFuncCode());
        authResourceDO.setResourceCode(resourceDO.getResourceCode());
        List<PeachAuthResourceDO> authResourceDOList = peachAuthResourceDao.select(authResourceDO);
        if (!PeachCollectionUtil.isEmpty(authResourceDOList)) {
            List<String> authResourceIdList = authResourceDOList.stream().map(PeachAuthResourceDO::getResourceId).collect(Collectors.toList());
            PeachAuthResourceQO authResourceQO = new PeachAuthResourceQO();
            authResourceQO.setResourceIdList(authResourceIdList);
            peachAuthResourceDao.updateByQO(authResourceQO);
        }
        return Response.success();
    }

    @Override
    public PeachAppResourceDO getByFuncCode(String funcCode,Integer isDeleted) {
        if (StringUtil.isBlank(funcCode)) {
            throw new ValidateException("param func is null");
        }
        return peachAppResourceDao.findByFuncCode(funcCode,isDeleted);
    }
}
