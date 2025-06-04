package com.peach.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peach.common.response.PageResult;
import com.peach.common.util.IDGeneratorUtil;
import com.peach.common.util.InputParamChecker;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.StringUtil;
import com.peach.security.api.IOptionService;
import com.peach.security.dao.PeachOptionDao;
import com.peach.security.entity.PeachOptionDO;
import com.peach.security.qo.PeachOptionQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/4 23:23
 */
@Slf4j
@Indexed
@Service
public class OptionServiceImpl implements IOptionService {

    @Resource
    private PeachOptionDao peachOptionDao;

    @Override
    public PageResult<PeachOptionDO> selectByPage(PeachOptionQO peachOptionQO) {
        PageInfo<PeachOptionDO> pageInfo = PageHelper.startPage(peachOptionQO.getPageNum(),peachOptionQO.getPageSize())
                .doSelectPageInfo(() -> {
                    peachOptionDao.selectByQO(peachOptionQO);
                });
        return new PageResult<PeachOptionDO>()
                .setTotal(pageInfo.getTotal())
                .setResult(pageInfo.getList());
    }

    @Override
    public void insert(PeachOptionDO peachOptionD) {
        try {
            InputParamChecker.of(peachOptionD);
        }catch (Exception e){
            log.error("params error"+e.getMessage(),e);
            throw new RuntimeException("params error");
        }
        peachOptionD.setId(IDGeneratorUtil.UUID());
        peachOptionDao.insert(peachOptionD);
    }

    @Override
    public void update(PeachOptionDO peachOptionD) {
        try {
            InputParamChecker.of(peachOptionD).checkFields("id");
        }catch (Exception e) {
            log.error("params error", e);
            throw new RuntimeException("params error");
        }
        PeachOptionDO existOptionDO = peachOptionDao.selectById(peachOptionD.getId());
        if (existOptionDO == null) {
            log.error("id: [{}] is not exist", existOptionDO.getId());
            throw new RuntimeException("id: [{}] is not exist");
        }
        peachOptionDao.update(peachOptionD);
    }

    @Override
    public void deleteByIds(List<String> idList) {
        if (PeachCollectionUtil.isEmpty(idList)) {
            log.error("params idList is empty");
            return;
        }
        peachOptionDao.delByIds(idList);
    }

    @Override
    public PeachOptionDO selectById(String id) {
        if (StringUtil.isBlank(id)) {
            log.error("id is null or empty");
            throw new RuntimeException("id is null or empty");
        }
        PeachOptionDO peachOptionDO = peachOptionDao.selectById(id);
        return peachOptionDO == null ? new PeachOptionDO() : peachOptionDO;
    }
}
