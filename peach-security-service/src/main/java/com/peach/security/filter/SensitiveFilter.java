package com.peach.security.filter;

import com.alibaba.fastjson.JSON;
import com.peach.common.IRedisDao;
import com.peach.common.constant.RedisConstant;
import com.peach.common.request.AbstractWrapperFilter;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.security.api.ISensitiveService;
import com.peach.security.entity.PeachSensitiveDO;
import com.peach.security.listener.SensitiveWordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description // 敏感词校验过滤器
 * @CreateTime 2025/6/11 22:33
 */
@Slf4j
public class SensitiveFilter extends AbstractWrapperFilter implements Ordered {
    
    @Resource
    private IRedisDao redisDao;
    
    @Resource
    private ISensitiveService sensitiveService;

    @Resource
    private SensitiveWordUtil sensitiveWordUtil;


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            List<String> sensitiveWordList = (List<String>) redisDao.vGet(RedisConstant.PRE_SENSITIVE_REDIS_KEY);

            if (PeachCollectionUtil.isEmpty(sensitiveWordList)) {
                List<PeachSensitiveDO> peachSensitiveDOList = sensitiveService.selectAll();
                sensitiveWordList = peachSensitiveDOList.stream()
                        .map(PeachSensitiveDO::getWord)
                        .collect(Collectors.toList());
                if (PeachCollectionUtil.isNotEmpty(sensitiveWordList)) {
                    redisDao.vSet(RedisConstant.PRE_SENSITIVE_REDIS_KEY, sensitiveWordList, RedisConstant.EXPIRE_VALIDATE_DAY);
                    log.info("敏感词配置缓存加载成功,已配置的敏感词配置为:[{}]", JSON.toJSONString(sensitiveWordList));
                }
            }

            // 如果敏感词为空，直接放行
            if (PeachCollectionUtil.isEmpty(sensitiveWordList)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 拦截提交的请求
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            String text = new String(sb);
            sensitiveWordUtil.init(sensitiveWordList);
            boolean isContains = sensitiveWordUtil.containsSensitiveWord(text);
            if (isContains) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"message\": \"包含敏感词:["+text+"]\"}");
            }
            filterChain.doFilter(request, response);
            
        }catch (Exception e){
            log.error("敏感词过滤器执行异常: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"服务器内部错误，敏感词过滤器异常\"}");
        }
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
