package com.peach.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.peach.common.IRedisDao;
import com.peach.common.constant.RedisConstant;
import com.peach.common.response.PageResult;
import com.peach.common.util.IDGeneratorUtil;
import com.peach.common.util.InputParamChecker;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.StringUtil;
import com.peach.security.api.ISensitiveService;
import com.peach.security.dao.PeachSensitiveDao;
import com.peach.security.entity.PeachSensitiveDO;
import com.peach.security.qo.PeachSensitiveQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/11 22:16
 */
@Slf4j
@Indexed
@Service
public class SensitiveServiceImpl implements ISensitiveService {

    @Resource
    private PeachSensitiveDao peachSensitiveDao;

    @Resource
    private IRedisDao redisDao;

    @Override
    public PageResult<PeachSensitiveDO> selectByPage(PeachSensitiveQO peachSensitiveQO) {
        PageInfo<PeachSensitiveDO> pageInfo = PageHelper.startPage(peachSensitiveQO.getPageNum(), peachSensitiveQO.getPageSize())
                .doSelectPageInfo(() -> peachSensitiveDao.selectByQO(peachSensitiveQO));
        return new PageResult<PeachSensitiveDO>()
                .setResult(pageInfo.getList())
                .setTotal(pageInfo.getTotal());
    }

    @Override
    public void insert(PeachSensitiveDO peachSensitiveDO) {
        try{
            InputParamChecker.of(peachSensitiveDO).checkFields("");
        }catch (Exception e){
            log.error("params error", e);
            throw new RuntimeException("params error");
        }
        // 补充参数
        peachSensitiveDO.setId(IDGeneratorUtil.UUID());
        peachSensitiveDao.insert(peachSensitiveDO);
        redisDao.delete(RedisConstant.PRE_SENSITIVE_REDIS_KEY);
    }

    @Override
    public void update(PeachSensitiveDO peachSensitiveDO) {
        try{
            InputParamChecker.of(peachSensitiveDO).checkFields("id");
        }catch (Exception e){
            log.error("params error", e);
            throw new RuntimeException("params error");
        }
        PeachSensitiveDO existSensitiveDO = peachSensitiveDao.selectById(peachSensitiveDO.getId());
        if (existSensitiveDO == null) {
            log.error("id: [{}] is not exist", peachSensitiveDO.getId());
            throw new RuntimeException("id: [{}] is not exist");
        }
        peachSensitiveDao.update(peachSensitiveDO);
        redisDao.delete(RedisConstant.PRE_SENSITIVE_REDIS_KEY);
    }

    @Override
    public void deleteByIds(List<String> idList) {
        if(PeachCollectionUtil.isEmpty(idList)){
            log.error("idList is empty");
            return;
        }
        peachSensitiveDao.delByIds(idList);
        redisDao.delete(RedisConstant.PRE_SENSITIVE_REDIS_KEY);
    }

    @Override
    public PeachSensitiveDO selectById(String id) {
        if (StringUtil.isBlank(id)){
            log.error("id is empty");
            return new PeachSensitiveDO();
        }
        PeachSensitiveDO peachSensitiveDO = peachSensitiveDao.selectById(id);
        return peachSensitiveDO == null ? new PeachSensitiveDO() : peachSensitiveDO;
    }

    @Override
    public List<PeachSensitiveDO> selectAll() {
        PeachSensitiveDO peachSensitiveDO = new PeachSensitiveDO();
        List<PeachSensitiveDO> allSensitiveList = peachSensitiveDao.select(peachSensitiveDO);
        return PeachCollectionUtil.isEmpty(allSensitiveList) ? Lists.newArrayList() : allSensitiveList;
    }
}
