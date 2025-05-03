package com.peach.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peach.common.constant.PubCommonConst;
import com.peach.common.exception.ValidateException;
import com.peach.common.response.PageResult;
import com.peach.common.response.Response;
import com.peach.common.util.IDGenerator;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.StringUtil;
import com.peach.security.api.IUserService;
import com.peach.security.dao.PeachUserDao;
import com.peach.security.entity.PeachUserDO;
import com.peach.security.qo.PeachUserQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 11:34
 */
@Slf4j
@Indexed
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private PeachUserDao peachUserDao;

    @Override
    public PeachUserDO getUserInfo(String userId) {
        if (StringUtil.isBlank(userId)){
            log.error("用户ID为空");
            throw new ValidateException("用户ID为空");
        }
        PeachUserDO query = new PeachUserDO();
        query.setId(userId);
        int existUserCout = peachUserDao.count(query);
        if (existUserCout == PubCommonConst.LOGIC_FLASE){
            throw new ValidateException("用户已被删除");
        }
        return peachUserDao.selectById(userId);
    }

    @Override
    public PageResult<PeachUserDO> getUserList(PeachUserQO userQO) {
        PageInfo<PeachUserDO> pageInfo = PageHelper.startPage(userQO.getPageNum(), userQO.getPageSize())
                .doSelectPageInfo(() -> {
                    peachUserDao.selectByQO(userQO);
                });
        return new PageResult<PeachUserDO>()
                .setTotal(pageInfo.getTotal())
                .setResult(pageInfo.getList());
    }

    @Override
    public void updateUserInfo(PeachUserDO userDO) {
        peachUserDao.updateById(userDO);
    }

    @Override
    public PeachUserDO getUserByUserAccount(String userAccount) {
        if (StringUtil.isBlank(userAccount)){
            return null;
        }
        return peachUserDao.getUserByAccount(userAccount);
    }

    @Override
    public PeachUserDO register(String userAccount, String password) {
        if (StringUtil.isBlank(userAccount) || StringUtil.isBlank(password)){
            log.error("userAccount or password is empty");
            throw new ValidateException("userAccount or password is empty");
        }
        PeachUserDO peachUserDO = new PeachUserDO();
        peachUserDO.setUserAccount(userAccount);
        int existUserCount = peachUserDao.count(peachUserDO);
        if (existUserCount > PubCommonConst.LOGIC_FLASE){
            log.error(String.format("账号:[%s]已存在"));
            throw new RuntimeException(String.format("账号:[%s]已存在"));
        }
        String uniqueId = IDGenerator.UUID();
        peachUserDO.setId(uniqueId);
        peachUserDO.setPassword(password);
        peachUserDO.setUserName(userAccount);
        peachUserDao.insert(peachUserDO);
        return peachUserDao.selectById(uniqueId);
    }

    @Override
    public Response deleteById(String userId) {
        if (StringUtil.isBlank(userId)){
            return Response.fail().setMsg("用户ID为空,不允许删除");
        }
        PeachUserDO query = new PeachUserDO();
        query.setId(userId);
        int existUserCout = peachUserDao.count(query);
        if (existUserCout < 0){
            return Response.fail().setMsg(String.format("用户ID:[%s],不存在",userId));
        }
        peachUserDao.delById(userId);
        return Response.success();
    }


    @Override
    public Response updateUser(PeachUserDO userDO) {
        if (userDO == null || StringUtil.isBlank(userDO.getId())){
            log.error("userDO is null or ID is empty");
            return Response.fail().setMsg("userDO is null or ID is empty");
        }
        PeachUserDO query = new PeachUserDO();
        query.setId(userDO.getId());
        List<PeachUserDO> userDOList = peachUserDao.select(query);
        if (PeachCollectionUtil.isEmpty(userDOList)){
            return Response.fail().setMsg("更新失败,该用户已被删除");
        }
        PeachUserDO userByAccount = peachUserDao.getUserByAccount(userDO.getUserAccount());
        if (userByAccount != null && !userByAccount.getId().equals(userDO.getId())){
            return Response.fail().setMsg(String.format("用户名:[%s]已存在,不允许更改",userDO.getUserAccount()));
        }
        peachUserDao.updateById(userDO);
        return Response.success();
    }

    @Override
    public Response addUser(PeachUserDO userDO) {
        String errorMsg = checkParams(userDO);
        if (StringUtil.isBlank(errorMsg)){
            return Response.fail().setMsg(errorMsg);
        }
        String userAccount = userDO.getUserAccount();
        PeachUserDO query = new PeachUserDO();
        query.setUserAccount(userAccount);
        int userExistCount = peachUserDao.count(query);
        if (userExistCount > 0){
            return Response.fail().setMsg(String.format("账号:[%s],已存在",userAccount));
        }

        userDO.setId(IDGenerator.UUID());
        peachUserDao.insert(userDO);
        return Response.success();
    }

    @Override
    public PeachUserDO selectById(String userId) {
        if (StringUtil.isBlank(userId)){
            log.error("用户ID为空,不允许删除");
            throw new ValidateException("用户ID为空,不允许删除");
        }
        return peachUserDao.selectById(userId);
    }

    /**
     * 新增用户校验参数
     *
     * @param userDO
     * @return
     */
    private String checkParams(PeachUserDO userDO) {
        StringBuffer sb = new StringBuffer();
        if (userDO == null){
            sb.append("userDO is null");
        }
        if (StringUtil.isBlank(userDO.getUserAccount())){
            sb.append("userAccount is null");
        }
        if (StringUtil.isBlank(userDO.getPassword())){
            sb.append("password is null");
        }
        if (StringUtil.isBlank(userDO.getEmail())){
            sb.append("email is null");
        }
        if (StringUtil.isBlank(userDO.getPhone())){
            sb.append("phone is null");
        }
        return sb.toString();
    }
}
