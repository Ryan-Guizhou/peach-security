package com.peach.security.config.auth;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.strategy.SaStrategy;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.peach.common.IRedisDao;
import com.peach.common.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/30 19:56
 */
@Slf4j
@Component
@AutoConfigureAfter(RedisConfig.class)
@ConditionalOnProperty(name = "sa-token.store-type", havingValue = "redis",matchIfMissing = true)
public class SaTokenRedisDaoImpl implements SaTokenDao {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static final String MAPPER_FIELD = "mapper";



    /**
     * ObjectMapper 对象 (以 public 作用域暴露出此对象，方便开发者二次更改配置)
     *
     * <p> 例如：
     * <pre>
     *      SaTokenDaoRedisJackson redisJackson = (SaTokenDaoRedisJackson) SaManager.getSaTokenDao();
     *      redisJackson.objectMapper.xxx = xxx;
     * 	</pre>
     * </p>
     */
    public ObjectMapper objectMapper;

    /**
     * String 读写专用
     */
    public StringRedisTemplate stringRedisTemplate;

    /**
     * Object 读写专用
     */
    public RedisTemplate<String, Object> objectRedisTemplate;

    /**
     * 标记：是否已初始化成功
     */
    public boolean isInit;

    @Autowired
    public void init(JedisConnectionFactory connectionFactory) {
        // 如果已经初始化成功了，就立刻退出，不重复初始化
        if (this.isInit) {
            return;
        }
        // 指定相应的序列化方案
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

        // 通过反射获取Mapper对象, 增加一些配置, 增强兼容性
        try {
            Field field = GenericJackson2JsonRedisSerializer.class.getDeclaredField(MAPPER_FIELD);
            field.setAccessible(true);
            this.objectMapper = (ObjectMapper) field.get(valueSerializer);

            // 配置[忽略未知字段]
            this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // 配置[时间类型转换]
            JavaTimeModule timeModule = new JavaTimeModule();

            // LocalDateTime序列化与反序列化
            timeModule.addSerializer(new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
            timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));

            // LocalDate序列化与反序列化
            timeModule.addSerializer(new LocalDateSerializer(DATE_FORMATTER));
            timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));

            // LocalTime序列化与反序列化
            timeModule.addSerializer(new LocalTimeSerializer(TIME_FORMATTER));
            timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));

            this.objectMapper.registerModule(timeModule);

            // 重写 SaSession 生成策略
            SaStrategy.me.createSession = SaSessionForJacksonCustomized::new;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 构建StringRedisTemplate
        StringRedisTemplate stringTemplate = new StringRedisTemplate();
        stringTemplate.setConnectionFactory(connectionFactory);
        stringTemplate.afterPropertiesSet();

        // 构建RedisTemplate
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();

        // 开始初始化相关组件
        this.stringRedisTemplate = stringTemplate;
        this.objectRedisTemplate = template;

        // 打上标记，表示已经初始化成功，后续无需再重新初始化
        this.isInit = true;
    }


    /**
     * 获取Value，如无返空
     */
    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     */
    @Override
    public void set(String key, String value, long timeout) {
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            stringRedisTemplate.opsForValue().set(key, value);
        } else {
            stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 修修改指定key-value键值对 (过期时间不变)
     */
    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    /**
     * 删除Value
     */
    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getTimeout(String key) {
        Long  expire = stringRedisTemplate.getExpire(key);
        return expire == null ? 0 : expire;
    }

    /**
     * 修改Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
                log.info("key: [{}] is already set to never expire.",key);
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }


    /**
     * 获取Object，如无返空
     */
    @Override
    public Object getObject(String key) {
        return objectRedisTemplate.opsForValue().get(key);
    }

    /**
     * 写入Object，并设定存活时间 (单位: 秒)
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            objectRedisTemplate.opsForValue().set(key, object);
        } else {
            objectRedisTemplate.opsForValue().set(key, object, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 更新Object (过期时间不变)
     */
    @Override
    public void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.setObject(key, object, expire);
    }

    /**
     * 删除Object
     */
    @Override
    public void deleteObject(String key) {
        objectRedisTemplate.delete(key);
    }

    /**
     * 获取Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getObjectTimeout(String key) {
        Long  expire = objectRedisTemplate.getExpire(key);
        return expire  == null ? 0 : expire;
    }

    /**
     * 修改Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getObjectTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
                log.info("key: [{}] is already set to never expire.",key);
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.setObject(key, this.getObject(key), timeout);
            }
            return;
        }
        objectRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }


    /**
     * 搜索数据
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        IRedisDao redisDao = SpringUtil.getBean(IRedisDao.class);
        Set<Object> sets = redisDao.keys(prefix + "*" + keyword + "*");
        List<Object> list = new ArrayList<>(sets);
        if (!sortType) {
            Collections.reverse(list);
        }
        if (start < 0) {
            start = 0;
        }
        int end;
        if (size == -1) {
            end = list.size();
        } else {
            end = start + size;
        }
        List<String> list2 = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (i >= list.size()) {
                return list2;
            }
            list2.add(String.valueOf(list.get(i)));
        }
        return list2;
    }




    @JsonIgnoreProperties({"timeout"})
    public static class SaSessionForJacksonCustomized extends SaSession implements Serializable {

        private static final long serialVersionUID = -7600983549653130681L;

        public SaSessionForJacksonCustomized() {
            super();
        }

        /**
         * 构建一个Session对象
         * @param id Session的id
         */
        public SaSessionForJacksonCustomized(String id) {
            super(id);
        }
    }
}
