package com.peach.security.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.peach.common.IRedisDao;
import com.peach.common.constant.EncryptConstant;
import com.peach.common.constant.PubCommonConst;
import com.peach.common.response.Response;
import com.peach.common.util.*;
import com.peach.common.util.encrypt.EncryptAbstract;
import com.peach.common.util.encrypt.EncryptFactory;
import com.peach.security.LoginInfo;
import com.peach.security.LoginRequestInfo;
import com.peach.security.RegisterRequestInfo;
import com.peach.security.api.*;
import com.peach.security.common.SecurityStatusEnum;
import com.peach.security.common.UserEnum;
import com.peach.security.constant.LanguageConstant;
import com.peach.security.constant.SecurityCaffineConstant;
import com.peach.security.constant.UserStatusConstant;
import com.peach.security.entity.PeachLanguageDO;
import com.peach.security.entity.PeachMenuDO;
import com.peach.security.entity.PeachRoleDO;
import com.peach.security.entity.PeachUserDO;
import com.peach.security.exception.AuthorityException;
import com.peach.security.exception.ExpiredPasswordException;
import com.peach.security.exception.RegisterException;
import com.peach.security.vo.LoginConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.time.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 14:56
 */
@Slf4j
@Indexed
@Service
public class LoginServiceImpl implements ILoginService {

    /**
     * 用户名校验正则表达式
     */
    private final String userAccountReg = "^[a-zA-Z0-9\\u4e00-\\u9fa5_-]{2,100}$";

    /**
     * 用户密码校验正则表达式
     */
    private final String userPasswordReg = "^.{9,}$";

    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IMenuService menuService;

    @Resource
    private IRedisDao redisDao;

    @Resource
    private IPeachLanguage peachLanguage;


    @Resource
    private IImageValidateService iImageValidateService;


    @Override
    public Response logout(HttpServletRequest request) {
        return Response.success();
    }

    @Override
    public Response init(String uniqueKey) {

        LoginConfigVO loginConfigVO = new LoginConfigVO();
        //1、获取语言类型
        PeachLanguageDO peachLanguageDO = CaffeineUtil.get(SecurityCaffineConstant.SECURITY_CAFFINE_KEY_LANGUAGE,()->{
            return peachLanguage.getPeachLanguage();
        });
        String language = StringUtil.isNotEmpty(peachLanguageDO.getLanguage()) ? peachLanguageDO.getLanguage() : LanguageConstant.DEFAULT_LANGUAGE;
        loginConfigVO.setLanguage(language);

        // 获取公钥
        EncryptAbstract instance = EncryptFactory.getInstance(EncryptConstant.RSA);

        String publicKey = StringUtil.EMPTY;
        try{
            publicKey = instance.getRsaInfo().get(EncryptConstant.PUBLIC_KEY);
        }catch (Exception e){
            throw new RuntimeException("获取RSA 公钥失败");
        }
        loginConfigVO.setPublicKey(publicKey);

        if (loginConfigVO.getValidateType() != null && PubCommonConst.VALIDATE_TYPE_IMAGE == loginConfigVO.getValidateType()){
            // 如果启用了滑块验证 获取滑块相关信息
            iImageValidateService.initCaptcha(uniqueKey,loginConfigVO);
        }
        return Response.success().setData(loginConfigVO);
    }


    @Override
    public Response login(LoginRequestInfo loginRequestInfo) {

        log.info("登录信息:[{}]", JSONUtil.toJsonStr(loginRequestInfo));

        // 1、校验用户名密码
        decryptLoginInfo(loginRequestInfo);

        // 2、根据是否配置验证码、二因子验证 校验

        // 3、登录
        PeachUserDO peachUserDO = getPeachUserDO(loginRequestInfo);

        // 4、登录成功,获取菜单、路由、角色等信息
        List<PeachRoleDO> roleList = roleService.selectByUserCode(loginRequestInfo.getUserAccount());
        List<String> roleCodeList = roleList.stream().map(PeachRoleDO::getRoleCode).collect(Collectors.toList());
//        List<PeachMenuDO> menuList = menuService.selectByRoleCodeList(roleCodeList);
        // 5、生成token

        // 6、返回登录信息

        StpUtil.login(peachUserDO.getId());
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserAccount(peachUserDO.getUserAccount());
        loginInfo.setUserName(peachUserDO.getUserName());
        loginInfo.setEmail(peachUserDO.getEmail());
        loginInfo.setPhone(peachUserDO.getPhone());
        loginInfo.setStatus(peachUserDO.getStatus());
        loginInfo.setToken(StpUtil.getTokenValue());
        return Response.success().setData(loginInfo);
    }

    private List<PeachMenuDO> getMenuAndRouterList(List<PeachRoleDO> roleList) {
        if (PeachCollectionUtil.isEmpty(roleList)) {

        }
        List<String> roleIdList = roleList.stream()
                .filter(o -> Objects.nonNull(o) && StringUtil.isNotBlank(o.getRoleId()))
                .sorted(Comparator.comparing(PeachRoleDO::getRoleId))
                .map(PeachRoleDO::getRoleId)
                .collect(Collectors.toList());
//        menuService.getMenuList()
        return new ArrayList<>();

    }

    @Override
    public Response register(RegisterRequestInfo registerRequestInfo) throws ValidationException {
        // 参数统一校验 都不能为空
        InputParamChecker.of(registerRequestInfo).checkAnnotatedFields();
        String userAccount = registerRequestInfo.getUserAccount();
        String password = registerRequestInfo.getPassword();
        String repeatPassword = registerRequestInfo.getRepeatPassword();

        // 校验密码是否符合规范
        checkUserAccountAndPassword(userAccount, password);
        if (!repeatPassword.equals(password)) {
            log.error("两次输入的密码不一致");
            throw new RegisterException("两次输入的密码不一致");
        }

        // 用户名是否存在 不能存在重复的用户
        PeachUserDO existUser = userService.getUserByUserAccount(userAccount);
        if (existUser != null) {
            log.error("用户账号:[{}]已存在,请重新输入用户账号", userAccount);
            throw new RegisterException(String.format("用户账号:[ %s ]已存在,请重新输入用户账号", userAccount));
        }

        // 校验通过 注册
        String encryptPassword = DigestUtil.md5Hex(password);
        PeachUserDO register = userService.register(userAccount, encryptPassword);

        return Response.success().setData(register);
    }

    /**
     * 解密并校验用户名及密码是否合规 将密码加密存储
     *
     * @param loginRequestInfo 登录请求信息
     */
    private void decryptLoginInfo(LoginRequestInfo loginRequestInfo) {
        try {
            String userAccount = loginRequestInfo.getUserAccount();
            String password = loginRequestInfo.getPassword();
            if (StringUtil.isBlank(userAccount) || StringUtil.isBlank(password)) {
                log.error("登录信息校验,用户名或密码为空! 用户名:[{}],密码:[{}]", userAccount, password);
                throw new AuthorityException(SecurityStatusEnum.INVALID_ACCOUNT);
            }

            //检验用户名是否合规
            String decryptPassword = RsaPasswordUtil.realPassword(password);
            String decryptUserAccount = RsaPasswordUtil.realPassword(userAccount);

            checkUserAccountAndPassword(decryptUserAccount, decryptPassword);
            loginRequestInfo.setUserAccount(decryptUserAccount);
            loginRequestInfo.setPassword(RsaPasswordUtil.md5HexPasswd(password));
        } catch (IllegalArgumentException e) {
            log.error("=====>登录信息校验错误, 用户名:[{}], 异常信息:{}", loginRequestInfo.getUserAccount(), e.getMessage());
            throw new AuthorityException(SecurityStatusEnum.INVALID_ACCOUNT);
        }
    }

    /**
     * 校验账号密码是否符合规范
     *
     * @param userAccount 用户名
     * @param password 密码
     */
    private void checkUserAccountAndPassword(String userAccount,String password) {
        boolean userAccountMetches = Pattern.compile(userAccountReg).matcher(userAccount).matches();
        if (!userAccountMetches) {
            log.error("=====>登录信息校验,用户名格式不正确! 用户名:[{}]", userAccount);
            throw new AuthorityException(SecurityStatusEnum.INVALID_ACCOUNT);
        }

        // 校验密码是否合规
        boolean userPasswordMetches = Pattern.compile(userPasswordReg).matcher(password).matches();
        if (!userPasswordMetches) {
            log.error("=====>登录信息校验,密码格式不对! 用户名:[{}],密码:[{}]", userAccount,password);
            throw new AuthorityException(SecurityStatusEnum.INVALID_ACCOUNT);
        }
    }

    /**
     * 通过登录请求信息构建返回的信息
     *
     * @param loginRequestInfo 登录请求信息
     * @return
     */
    private LoginInfo buildLoginInfo(LoginRequestInfo loginRequestInfo) {

        PeachUserDO peachUserDO = getPeachUserDO(loginRequestInfo);
        // 补充参数

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserAccount(peachUserDO.getUserAccount());
        loginInfo.setUserName(peachUserDO.getUserName());
        loginInfo.setStatus(peachUserDO.getStatus());
        loginInfo.setEmail(peachUserDO.getEmail());
        loginInfo.setPhone(peachUserDO.getPhone());
        return loginInfo;
    }


    /**
     * 根据用户名获取用户信息
     * @param userAccount 用户名
     * @return
     * @throws AuthorityException
     */
    private PeachUserDO getUserByUserAccount(String userAccount) throws AuthorityException {

        ThrowUtil.throwIf(StringUtil.isBlank(userAccount), new AuthorityException(SecurityStatusEnum.INVALID_ACCOUNT));
        PeachUserDO userDO = userService.getUserByUserAccount(userAccount);
        if (userDO == null) {
            log.error("用户名:[{}],不存", userAccount);
            throw new AuthorityException(SecurityStatusEnum.INVALID_ACCOUNT);
        }
        return userDO;
    }

    /**
     * 获取用户信息
     * @param loginRequestInfo 登录请求信息
     * @return
     * @throws AuthorityException
     */
    private PeachUserDO getPeachUserDO(LoginRequestInfo loginRequestInfo) throws AuthorityException {
        try{
            if (Objects.isNull(loginRequestInfo)) {
                log.error("loginRequestInfo is null");
                throw new AuthorityException(SecurityStatusEnum.INVALID_ACCOUNT);
            }

            PeachUserDO userDO = getUserByUserAccount(loginRequestInfo.getUserAccount());
            // 校验状态 是否被禁用
            checkUserStatus(userDO);
            // 校验密码是否过期
            checkUserPasswordInvlidate(userDO);

            String inputPassword = loginRequestInfo.getPassword();
            checkUserPassword(userDO,inputPassword);
            return userDO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 校验用户状态
     *
     * @param userDO
     */
    private void checkUserStatus(PeachUserDO userDO) {
        UserEnum.UserStatus userStatus = UserEnum.UserStatus.USER_STATUS_MAP.get(userDO.getStatus());
        if (userStatus == null) {
            log.error("用户:[{}] 的登录状态不存在,不允许登录", userDO.getUserAccount());
            throw new AuthorityException(SecurityStatusEnum.NOT_ACTIVE_ACCOUNT);
        }
        if (userStatus.getValue() != userDO.getStatus()) {
            log.error("用户:[{}] 的登录状态已被锁定,不允许登录,请联系管理员", userDO.getUserAccount());
            throw new AuthorityException(SecurityStatusEnum.LOCKED_ACCOUNT);
        }
    }

    /**
     * 校验密码是否有效
     *
     * @param userDO 用户密码
     */
    private void checkUserPasswordInvlidate(PeachUserDO userDO) {
        if (Objects.nonNull(userDO.getInvlidate())) {
            LocalDate invalidDate = LocalDateTime
                    .ofInstant(Instant.ofEpochMilli(userDO.getInvlidate().getTime()), ZoneId.systemDefault())
                    .toLocalDate();
            if (invalidDate.compareTo(LocalDate.now()) < 0) {
                log.error("=====>登录用户名:{}, 密码失效日期:{}, 用户密码已失效", userDO.getUserAccount(), DateUtil.formatDate(userDO.getInvlidate()));
                throw new ExpiredPasswordException(SecurityStatusEnum.EXPIRED_PASSWORD);
            }
        }
    }

    /**
     * 校验密码是否正确
     *
     * @param userDO 数据库中存在的密码
     * @param inputPassword 用户输入的密码
     * @param inputPassword 用户名
     */
    private void checkUserPassword(PeachUserDO userDO,String inputPassword) {
        String userAccount = userDO.getUserAccount();
        String existPassword = userDO.getPassword();

        String lockedCountKey = buildLockedCountUniqueKey(userAccount);
        String lockedLevelKey = buildLockedLevelUniqueKey(userAccount);

        Integer lockedLevel = Optional.ofNullable((Integer) redisDao.vGet(lockedLevelKey)).orElse(null);
        boolean isLocked = lockedLevel != null;

        if (isLocked) {
            // 用户仍在锁定期内，禁止登录（无论密码是否正确）
            int remainingMinutes = redisDao.ttlKey(lockedLevelKey);
            log.warn("用户 [{}] 正在锁定中，剩余锁定时间 [{}] 分钟", userAccount, remainingMinutes);
            throw new AuthorityException(SecurityStatusEnum.LOCKED_ACCOUNT,
                    String.format("账号正在锁定中，%d 分钟后可再次尝试", remainingMinutes));
        }

        // 判断密码是否正确
        if (inputPassword.equals(existPassword)) {
            // 密码正确，清除错误次数和锁定等级
            redisDao.delete(lockedCountKey);
            redisDao.delete(lockedLevelKey);
            return;
        }

        // 密码错误
        log.error("用户名:[{}], 登录密码:[{}], 已存在密码:[{}] 不一致，登录失败", userAccount, inputPassword, existPassword);

        int errorCount = Optional.ofNullable((Integer) redisDao.vGet(lockedCountKey)).orElse(0);
        errorCount++;

        if (errorCount >= UserStatusConstant.MAX_ERROR_COUNT) {
            // 需要锁定账户
            lockedLevel = Optional.ofNullable((Integer) redisDao.vGet(lockedLevelKey)).orElse(0);
            if (lockedLevel < UserStatusConstant.PASSWORD_ERROR_LOCKED_LEVEL.length - 1) {
                lockedLevel++;
            }

            int lockMinutes = UserStatusConstant.PASSWORD_ERROR_LOCKED_LEVEL[lockedLevel];

            // 更新用户状态为锁定
            PeachUserDO user = new PeachUserDO();
            user.setId(userDO.getId());
            user.setUserAccount(userAccount);
            user.setStatus(UserStatusConstant.LOCKED);
            userService.updateUserInfo(user);

            // 设置锁定等级和锁定时间
            redisDao.vSet(lockedLevelKey, lockedLevel, Duration.ofMinutes(lockMinutes));
            redisDao.delete(lockedCountKey);

            log.error("用户 [{}] 被锁定，锁定等级 [{}]，锁定时长 [{}] 分钟", userAccount, lockedLevel, lockMinutes);
            throw new AuthorityException(SecurityStatusEnum.LOCKED_ACCOUNT,
                    String.format("账号已被锁定，%d 分钟后可重试", lockMinutes));
        }

        // 没达到锁定阈值，记录错误次数
        redisDao.vSet(lockedCountKey, errorCount, Duration.ofMinutes(15));
        int remain = UserStatusConstant.MAX_ERROR_COUNT - errorCount;
        String errorMsg = String.format(
                "密码错误，第 [%d] 次，剩余尝试次数 [%d]，15 分钟后重置",
                errorCount, Math.max(remain, 0)
        );

        throw new AuthorityException(SecurityStatusEnum.INVALID_PASSWORD, errorMsg);
    }

    /**
     * 构建密码错误次数的惟一键
     *
     * @param userAccount 用户名
     * @return
     */
    private String buildLockedCountUniqueKey(String userAccount) {
        return UserStatusConstant.PASSWORD_ERROR_LOCKED_COUNT_KEY_PRIX  + userAccount;
    }

    /**
     * 构建密码错误等级的惟一键
     *
     * @param userAccount 用户名
     * @return
     */
    private String buildLockedLevelUniqueKey(String userAccount) {
        return UserStatusConstant.PASSWORD_ERROR_LOCKED_LEVEL_KEY_PRIX + userAccount;
    }

}
