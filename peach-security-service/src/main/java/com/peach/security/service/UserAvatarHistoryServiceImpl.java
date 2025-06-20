package com.peach.security.service;

import com.google.common.collect.Lists;
import com.peach.common.util.InputParamChecker;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.StringUtil;
import com.peach.fileservice.impl.AbstractFileStorageService;
import com.peach.security.api.IUserAvatarHistory;
import com.peach.security.dao.PeachUserAvatarHistoryDao;
import com.peach.security.entity.PeachUserAvatarHistoryDO;
import com.peach.security.qo.PeachUserAvatarHistoryQO;
import com.peach.security.vo.UserAvatarHistoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/20 23:59
 */
@Slf4j
@Indexed
@Service
public class UserAvatarHistoryServiceImpl implements IUserAvatarHistory {

    @Resource
    private PeachUserAvatarHistoryDao peachUserAvatarHistoryDao;

    @Resource
    private AbstractFileStorageService abstractFileStorageService;

    @Override
    public List<UserAvatarHistoryVO> selectByUserId(String userId) {
        if (StringUtil.isBlank(userId)) {
            log.error("param userId is null or empty");
            return Lists.newArrayList();
        }
        List<UserAvatarHistoryVO> avatarHistoryVOList = peachUserAvatarHistoryDao.selectByUserId(userId);
        return PeachCollectionUtil.isNotEmpty(avatarHistoryVOList) ? avatarHistoryVOList : Lists.newArrayList();
    }

    @Override
    public void insert(PeachUserAvatarHistoryDO peachWitheListDO) {
        try{
            InputParamChecker.of(peachWitheListDO).checkFields("avatarUrl","id","userId");
        }catch (Exception e){
            log.error("params error", e);
            throw new RuntimeException(e);
        }
        PeachUserAvatarHistoryDO peachUserAvatarHistoryDO = new PeachUserAvatarHistoryDO();
        peachUserAvatarHistoryDO.setFileMd5(peachWitheListDO.getFileMd5());
        peachUserAvatarHistoryDO.setUserId(peachWitheListDO.getUserId());
        List<PeachUserAvatarHistoryDO> avatarHistoryDOList = peachUserAvatarHistoryDao.select(peachUserAvatarHistoryDO);
        if (PeachCollectionUtil.isNotEmpty(avatarHistoryDOList)) {
            log.error("该文件已存在,无需重复上传");
            return;
        }
        peachUserAvatarHistoryDao.insert(peachWitheListDO);
        // 保留最新的十条数据
        PeachUserAvatarHistoryQO peachUserAvatarHistoryQO = new PeachUserAvatarHistoryQO();
        peachUserAvatarHistoryQO.setUserId(peachWitheListDO.getUserId());
        peachUserAvatarHistoryDao.deleteByQO(peachUserAvatarHistoryQO);
    }

    @Override
    public void deleteById(String id) {
        if (StringUtil.isBlank(id)) {
            log.error("param id is null or empty");
            throw new RuntimeException("param id is null or empty");
        }
        PeachUserAvatarHistoryDO peachUserAvatarHistoryDO = peachUserAvatarHistoryDao.selectById(id);
        if (peachUserAvatarHistoryDO == null) {
            log.error("id:{} avatar is not exist", id);
            throw new RuntimeException("this avatar is not exist");
        }
        String avatarUrl = peachUserAvatarHistoryDO.getAvatarUrl();
        abstractFileStorageService.delete(avatarUrl);
        peachUserAvatarHistoryDao.delById(id);
    }

    @Override
    public boolean existAvatar(PeachUserAvatarHistoryDO peachWitheListDO) {
        List<PeachUserAvatarHistoryDO> avatarHistoryDOList = peachUserAvatarHistoryDao.select(peachWitheListDO);
        return PeachCollectionUtil.isNotEmpty(avatarHistoryDOList) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public void deleteByQO(PeachUserAvatarHistoryQO peachWitheListQO) {

    }
}
