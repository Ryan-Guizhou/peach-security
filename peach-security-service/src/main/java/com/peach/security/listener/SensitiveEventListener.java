package com.peach.security.listener;

import com.alibaba.fastjson.JSON;
import com.peach.common.IRedisDao;
import com.peach.common.constant.RedisConstant;
import com.peach.common.envent.ApplicationReadyListener;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.security.api.ISensitiveService;
import com.peach.security.entity.PeachSensitiveDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description 白名单缓存预热
 * @CreateTime 2025/6/11 21:30
 */
@Slf4j
@Component
public class SensitiveEventListener extends ApplicationReadyListener implements Ordered {

    @Resource
    private IRedisDao redisDao;

    @Resource
    private ISensitiveService sensitiveService;

    @Override
    protected void onApplicationReady(ApplicationReadyEvent event) {
        try {
            boolean existsKey = redisDao.existsKey(RedisConstant.PRE_SENSITIVE_REDIS_KEY);
            if (existsKey) {
                log.info("敏感词缓存已存在，跳过预热");
                return;
            }
            List<String> witheListDOList = (List<String>) redisDao.vGet(RedisConstant.PRE_SENSITIVE_REDIS_KEY);
            if (PeachCollectionUtil.isNotEmpty(witheListDOList)) {
                log.info("敏感词配置缓存已加载,无需重复加载");
                return;
            }
            List<PeachSensitiveDO> peachSensitiveDOList = sensitiveService.selectAll();
            if (PeachCollectionUtil.isEmpty(peachSensitiveDOList)) {
                log.info("没有配置敏感词配置，无需预热加载缓存");
                return;
            }
            List<String> sensitiveWordList = peachSensitiveDOList.stream()
                    .map(PeachSensitiveDO::getWord)
                    .collect(Collectors.toList());
            if (PeachCollectionUtil.isEmpty(sensitiveWordList)) {
                return;
            }
            redisDao.vSet(RedisConstant.PRE_SENSITIVE_REDIS_KEY, sensitiveWordList, RedisConstant.EXPIRE_VALIDATE_DAY);
            log.info("敏感词配置缓存加载成功,已配置的敏感词配置为:[{}]", JSON.toJSONString(sensitiveWordList));

        }catch (Exception e) {
            log.error("敏感词预加载失败"+e.getMessage(),e);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
