package com.peach.security.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peach.common.response.PageResult;
import com.peach.common.util.IDGenerator;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.StringUtil;
import com.peach.security.api.IRouterService;
import com.peach.security.dao.PeachRouterDao;
import com.peach.security.entity.PeachRouterDO;
import com.peach.security.qo.PeachRouterQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/23 23:00
 */
@Slf4j
@Service
@Indexed
public class RouterServiceImpl implements IRouterService {

    @Resource
    private PeachRouterDao peachRouterDao;

    @Override
    public List<PeachRouterDO> selectAll() {
        PeachRouterDO peachRouterDO = new PeachRouterDO();
        List<PeachRouterDO> allPeachRouter = peachRouterDao.select(peachRouterDO);
        return PeachCollectionUtil.isEmpty(allPeachRouter) ? Collections.emptyList() : allPeachRouter;
    }

    @Override
    public PageResult<PeachRouterDO> selectByPage(PeachRouterQO peachRouterQO) {
        PageInfo<PeachRouterDO> pageInfo = PageHelper.startPage(peachRouterQO.getPageNum(), peachRouterQO.getPageSize())
                .doSelectPageInfo(() -> {
                    peachRouterDao.selectByQO(peachRouterQO);
                });
        return new PageResult<PeachRouterDO>()
                .setTotal(pageInfo.getTotal())
                .setResult(pageInfo.getList());
    }

    @Override
    public void insert(PeachRouterDO peachRouterDO) {
        if (ObjectUtil.isNull(peachRouterDO)) {
            throw new RuntimeException("peachRouterDO is null");
        }
        peachRouterDO.setRouterId(IDGenerator.UUID());
        String routerCode = peachRouterDO.getRouterCode();
        String routerName = peachRouterDO.getRouterName();
        String routerUrl = peachRouterDO.getRouterUrl();
        if (StringUtil.isBlank(routerCode)) {
            throw new RuntimeException("router code is empty");
        }
        if (StringUtil.isBlank(routerName)) {
            throw new RuntimeException("router name is empty");
        }
        if (StringUtil.isBlank(routerUrl)) {
            throw new RuntimeException("router url is empty");
        }
        peachRouterDao.insert(peachRouterDO);
    }

    @Override
    public void update(PeachRouterDO peachRouterDO) {
        if (ObjectUtil.isNull(peachRouterDO)) {
            throw new RuntimeException("peachRouterDO is null");
        }
        String routerId = peachRouterDO.getRouterId();
        if (StringUtil.isBlank(routerId)) {
            throw new RuntimeException("router id is empty");
        }
        peachRouterDao.update(peachRouterDO);
    }

    @Override
    public void deleteByIds(List<String> idList) {
        if (ObjectUtil.isNull(idList)) {
            throw new RuntimeException("idList is null");
        }
        List<PeachRouterDO> peachRouterDOList = peachRouterDao.selectByIds(idList);
        if (PeachCollectionUtil.isEmpty(peachRouterDOList)) {
            throw new RuntimeException("peachRouterDOList is empty,can not delete");
        }
        List<String> includeIdList = peachRouterDOList.stream()
                .map(PeachRouterDO::getRouterId)
                .filter(id -> idList.contains(id))
                .collect(Collectors.toList());
        peachRouterDao.selectByIds(includeIdList);
    }

    @Override
    public PeachRouterDO selectById(String id) {
        if (StringUtil.isBlank(id)) {
            throw new RuntimeException("id is null");
        }
        return peachRouterDao.selectById(id);
    }
}
