package com.peach.security.rest;

import com.peach.common.response.Response;
import com.peach.security.api.IMenuService;
import com.peach.security.entity.PeachMenuDO;
import com.peach.security.qo.PeachMenuQO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 1:35
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @PostMapping("/insert")
    @ApiOperation("新增菜单")
    public Response insert(@RequestBody PeachMenuDO menuDO) {
        return menuService.insert(menuDO);
    }

    @PostMapping("/modify")
    @ApiOperation("修改菜单")
    public Response modify(@RequestBody PeachMenuDO menuDO) {
        return menuService.modify(menuDO);
    }

    @PostMapping("/delById/{menuId}")
    @ApiOperation("单个删除菜单")
    public Response delMenu(@PathVariable("menuId") String menuId) {
        return menuService.deleteById(menuId);
    }

    @PostMapping("/batchDelMenu")
    @ApiOperation("批量删除菜单")
    public Response batchDelMenu(PeachMenuQO menuQO) {
        return Response.success();
    }

    @GetMapping("/getById/{menuId}")
    @ApiOperation("根据ID获取菜单")
    public Response getById(@PathVariable("menuId") String menuId) {
        PeachMenuDO peachMenuDO = menuService.selectById(menuId);
        return Response.success().setData(peachMenuDO);
    }
}
