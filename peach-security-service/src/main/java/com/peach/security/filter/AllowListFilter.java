package com.peach.security.filter;

import com.alibaba.fastjson.JSON;
import com.peach.common.IRedisDao;
import com.peach.common.constant.RedisConstant;
import com.peach.common.request.AbstractWrapperFilter;
import com.peach.common.util.IpUtil;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.StringUtil;
import com.peach.security.api.IWitheListService;
import com.peach.security.common.WitheListEnum;
import com.peach.security.entity.PeachWitheListDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description 白名单过滤器
 * @CreateTime 2025/6/11 21:00
 */
@Slf4j
public class AllowListFilter extends AbstractWrapperFilter implements Ordered {

    @Resource
    private IWitheListService witheListService;

    @Resource
    private IRedisDao redisDao;


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            List<String> witheList = (List<String>) redisDao.vGet(RedisConstant.PRE_HEAT_REDIS_KEY);

            if (PeachCollectionUtil.isEmpty(witheList)) {
                List<PeachWitheListDO> peachWitheListDOS = witheListService.selectAll();
                witheList = peachWitheListDOS.stream()
                        .filter(peachWitheListDO -> WitheListEnum.WitheListTypeEnum.IPV4.equals(peachWitheListDO.getType()))
                        .map(PeachWitheListDO::getIpAddress)
                        .collect(Collectors.toList());
                if (PeachCollectionUtil.isNotEmpty(witheList)) {
                    redisDao.vSet(RedisConstant.PRE_HEAT_REDIS_KEY, witheList, RedisConstant.EXPIRE_VALIDATE_DAY);
                    log.info("白名单配置缓存加载成功,已配置的白名单配置为:[{}]", JSON.toJSONString(witheList));
                }
            }

            // 如果白名单为空，直接放行
            if (PeachCollectionUtil.isEmpty(witheList)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 获取请求 IP
            String ipAddr = IpUtil.getIpAddr(request);
            log.debug("请求来源IP: [{}]", ipAddr);

            if (StringUtil.isNotBlank(ipAddr)) {

                if (PeachCollectionUtil.isEmpty(witheList) || witheList.contains("*")) {
                    // 无 IPv4 白名单配置或包含 * ，放行
                    filterChain.doFilter(request, response);
                    return;
                }

                // 本地请求通行
                if ("127.0.0.1".equals(ipAddr) || "0:0:0:0:0:0:0:1".equals(ipAddr) || "localhost".equalsIgnoreCase(ipAddr)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                // 在白名单中，放行
                if (witheList.contains(ipAddr)) {
                    filterChain.doFilter(request, response);
                } else {
                    // 不在白名单中，拦截
                    log.warn("IP [{}] 未在白名单中，访问被拒绝", ipAddr);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"message\": \"非法访问，IP未在白名单中\"}");
                }
            } else {
                // 获取不到 IP，默认拒绝
                log.warn("无法获取请求IP，拒绝访问");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"message\": \"非法访问，无法识别请求IP\"}");
            }
        }catch (Exception e){
            log.error("白名单过滤器执行异常: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"服务器内部错误，白名单过滤器异常\"}");
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
