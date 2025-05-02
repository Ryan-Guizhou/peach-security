package com.peach.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peach.common.enums.StatusEnum;
import com.peach.common.response.PageResult;
import com.peach.common.response.Response;
import com.peach.common.util.IDGenerator;
import com.peach.common.util.StringUtil;
import com.peach.common.util.ThrowUtil;
import com.peach.security.entity.PeachUserDO;
import com.peach.security.api.IUserService;
import com.peach.security.dao.PeachUserDao;
import com.peach.security.dto.UserDTO;
import com.peach.security.qo.UserQO;
import com.peach.security.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public UserVO getUserInfo(String userId) {
        PeachUserDO userDO = peachUserDao.selectById(userId);
        return null;
    }

    @Override
    public PageResult<PeachUserDO> getUserList(UserQO userQO) {
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
    public PeachUserDO checkLogin(String userAccount, String password) {
        ThrowUtil.throwIf(StringUtil.isBlank(userAccount), StatusEnum.PARAM_ERROR,"账号不能为空");
        ThrowUtil.throwIf(StringUtil.isBlank(password), StatusEnum.PARAM_ERROR,"用户名密码不能为空");
//        PeachUserDO userDO = peachUserDao.selectByAccountPassword(userAccount, password);
//        return userDO == null ? new PeachUserDO() : userDO;
        return null;
    }

//    private final Validator validator;
    @Override
    public String validateUserDTO(UserDTO userDTO) {
//        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
//        // 如果有错误，返回错误信息
//        if (!violations.isEmpty()) {
//            return violations.stream()
//                    .map(ConstraintViolation::getMessage)
//                    .collect(Collectors.joining("; "));
//        }

        // 校验通过，返回成功消息
        return "校验通过，用户信息有效";
    }

//    public UserServiceImpl() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        this.validator = factory.getValidator();
//    }

    @Override
    public Response insert(UserDTO userDTO) {
        String msg = validateUserDTO(userDTO);
        if (StringUtil.isNotBlank(msg)){
            return Response.fail().setMsg(msg);
        }

        return Response.success();
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
        String uniqueId = IDGenerator.UUID();
        PeachUserDO peachUserDO = new PeachUserDO();
        peachUserDO.setId(uniqueId);
        peachUserDO.setUserAccount(userAccount);
        peachUserDO.setPassword(password);
        peachUserDO.setUserName(userAccount);
        peachUserDao.insert(peachUserDO);
        return peachUserDao.selectById(uniqueId);
    }
}
