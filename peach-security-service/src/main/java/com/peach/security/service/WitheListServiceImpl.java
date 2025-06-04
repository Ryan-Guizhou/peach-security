package com.peach.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peach.common.response.PageResult;
import com.peach.common.util.IDGeneratorUtil;
import com.peach.common.util.InputParamChecker;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.StringUtil;
import com.peach.security.api.IWitheListService;
import com.peach.security.dao.PeachWitheListDao;
import com.peach.security.entity.PeachWitheListDO;
import com.peach.security.qo.PeachWitheListQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/4 23:14
 */
@Slf4j
@Indexed
@Service
public class WitheListServiceImpl implements IWitheListService {

    @Resource
    private PeachWitheListDao peachWitheListDao;


    @Override
    public List<PeachWitheListDO> selectAll() {
        PeachWitheListDO peachWitheListDO = new PeachWitheListDO();
        List<PeachWitheListDO> allWitheListDOList = peachWitheListDao.select(peachWitheListDO);
        return PeachCollectionUtil.isEmpty(allWitheListDOList) ? Collections.emptyList() : allWitheListDOList;
    }

    @Override
    public PageResult<PeachWitheListDO> selectByPage(PeachWitheListQO peachWitheListQO) {
        PageInfo<PeachWitheListDO> pageInfo = PageHelper.startPage(peachWitheListQO.getPageNum(), peachWitheListQO.getPageSize())
                .doSelectPageInfo(() -> {
                    peachWitheListDao.selectByQO(peachWitheListQO);
                });
        return new PageResult<PeachWitheListDO>()
                .setTotal(pageInfo.getTotal())
                .setResult(pageInfo.getList());

    }

    @Override
    public void insert(PeachWitheListDO peachWitheListDO) {
        try{
            InputParamChecker.of(peachWitheListDO);
        }catch(Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("params error");
        }
        peachWitheListDO.setId(IDGeneratorUtil.UUID());
        peachWitheListDao.insert(peachWitheListDO);
    }

    @Override
    public void update(PeachWitheListDO peachWitheListDO) {
        try{
            InputParamChecker.of(peachWitheListDO).checkFields("id");
        }catch(Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("params error");
        }
        PeachWitheListDO existWitheListDO = peachWitheListDao.selectById(peachWitheListDO.getId());
        if (existWitheListDO == null) {
            log.error("id: [{}] is not exist", peachWitheListDO.getId());
            throw new RuntimeException("id: [{}] is not exist");
        }
        peachWitheListDao.update(peachWitheListDO);
    }

    @Override
    public void deleteByIds(List<String> idList) {
        if(PeachCollectionUtil.isEmpty(idList)){
            return;
        }
        peachWitheListDao.delByIds(idList);
    }

    @Override
    public PeachWitheListDO selectById(String id) {
        if (StringUtil.isBlank(id)){
            log.error("id is null");
            throw new RuntimeException("id is null");
        }
        PeachWitheListDO peachWitheListDO = peachWitheListDao.selectById(id);
        return peachWitheListDO == null ? new PeachWitheListDO() : peachWitheListDO;
    }
}
