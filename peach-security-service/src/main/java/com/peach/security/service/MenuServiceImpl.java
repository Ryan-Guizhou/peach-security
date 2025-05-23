package com.peach.security.service;

import com.peach.common.constant.PubCommonConst;
import com.peach.common.exception.ValidateException;
import com.peach.common.response.Response;
import com.peach.common.util.IDGenerator;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.StringUtil;
import com.peach.security.api.IMenuService;
import com.peach.security.dao.PeachMenuDao;
import com.peach.security.entity.PeachMenuDO;
import com.peach.security.qo.PeachMenuQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 20:22
 */
@Slf4j
@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    private PeachMenuDao peachMenuDao;

    @Override
    public List<PeachMenuDO> selectByQO(PeachMenuQO qo) {
        List<PeachMenuDO> menuDOList = peachMenuDao.selectByQO(qo);
        return PeachCollectionUtil.isEmpty(menuDOList) ? Collections.emptyList() : menuDOList;
    }

    @Override
    public List<PeachMenuDO> selectByRoleCodeList(List<String> roleCodeList) {
        if (PeachCollectionUtil.isEmpty(roleCodeList)) {
            throw new ValidateException("method:selectByRoleCodeList 参数为空");
        }
        List<PeachMenuDO> menuDOList = peachMenuDao.selectByRoleCodeList(roleCodeList);
        return PeachCollectionUtil.isEmpty(menuDOList) ? Collections.emptyList() : menuDOList;
    }

    @Override
    public Response deleteById(String menuId) {
        if (StringUtil.isBlank(menuId)) {
            log.error("菜单ID:[{}],不存在,不允许删除", menuId);
            return Response.fail().setMsg(String.format("菜单ID:[%s],不存在,不允许删除",menuId));
        }
        // 校验数据库中是否存在,如果不存在直接返回失败
        PeachMenuDO peachMenuDO = new PeachMenuDO();
        peachMenuDO.setMenuId(menuId);
        int existMenuCount = peachMenuDao.count(peachMenuDO);
        if (existMenuCount == PubCommonConst.LOGIC_FLASE) {
            log.error("菜单ID:[{}],不存在,不允许删除", menuId);
            return Response.fail().setMsg(String.format("菜单ID:[%s],不存在,不允许删除",menuId));
        }
        // 删除菜单
        peachMenuDao.delById(menuId);
        return Response.success();
    }

    @Override
    public Response modify(PeachMenuDO menuDO) {
        if(Objects.isNull(menuDO)) {
            String errMsg = String.format("参数错误,传参为空");
            log.error(errMsg);
            throw new ValidateException(errMsg);
        }

        String menuCode = menuDO.getMenuCode();
        if(StringUtil.isBlank(menuCode)) {
            log.error("必填参数menuCode:[{}]", menuCode);
            throw new ValidateException("必填参数为空");
        }

        PeachMenuDO existMenuDO = peachMenuDao.selectById(menuDO.getMenuId());
        if(existMenuDO == null) {
            log.error("菜单ID:[{}],已被删除", menuDO.getMenuId());
            throw new ValidateException(String.format("菜单ID:[%s],已被删除", menuDO.getMenuId()));
        }

        String existMenuCode = existMenuDO.getMenuCode();
        if(menuCode == null || !menuCode.equals(existMenuCode)) {
            log.error("菜单编码不允许修改");
            throw new ValidateException("菜单编码不允许修改");
        }

        peachMenuDao.update(menuDO);
        return Response.success();
    }

    @Override
    public Response insert(PeachMenuDO menuDO) {
        if(Objects.isNull(menuDO)) {
            String errMsg = String.format("参数错误,传参为空");
            log.error(errMsg);
            throw new ValidateException(errMsg);
        }

        String menuCode = menuDO.getMenuCode();
        if(StringUtil.isBlank(menuCode)) {
            log.error("必填参数menuCode:[{}]", menuCode);
            throw new ValidateException("必填参数为空");
        }
        PeachMenuDO peachMenuDO = new PeachMenuDO();
        peachMenuDO.setMenuCode(menuCode);
        int menuExistCount = peachMenuDao.count(peachMenuDO);
        if(menuExistCount > PubCommonConst.LOGIC_FLASE) {
            log.error("menuCode:[{}],已存在,不允许添加",menuCode);
            throw new ValidateException(String.format("menuCode:[%s],已存在,不允许添加",menuCode));
        }
        menuDO.setMenuId(IDGenerator.UUID());
        peachMenuDao.insert(menuDO);
        return Response.success();
    }

    @Override
    public PeachMenuDO selectById(String menuId) {
        if (StringUtil.isBlank(menuId)) {
            log.error("菜单ID为空");
            throw new RuntimeException("菜单ID为空");
        }
        return peachMenuDao.selectById(menuId);
    }
}
