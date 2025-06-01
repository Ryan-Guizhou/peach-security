package com.peach.security.config.auth;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.session.SaSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peach.common.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "sa-token.store-type", havingValue = "mongo")
public class SaTokenMongoDaoImpl implements SaTokenDao {

    private static final String COLLECTION_STRING = "sa_token_string";
    private static final String COLLECTION_OBJECT = "sa_token_object";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String get(String key) {
        TokenData data = mongoTemplate.findById(key, TokenData.class, COLLECTION_STRING);
        if (data != null && !data.isExpired()) {
            return StringUtil.getStringValue(data.getValue());
        }
        return null;
    }

    @Override
    public void set(String key, String value, long timeout) {
        if (timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        Date expireAt = (timeout == SaTokenDao.NEVER_EXPIRE) ? null : Date.from(Instant.now().plusSeconds(timeout));
        TokenData data = new TokenData(key, value, expireAt);
        mongoTemplate.save(data, COLLECTION_STRING);
    }

    @Override
    public void update(String key, String value) {
        long timeout = getTimeout(key);
        set(key, value, timeout);
    }

    @Override
    public void delete(String key) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(key)), COLLECTION_STRING);
    }

    @Override
    public long getTimeout(String key) {
        TokenData data = mongoTemplate.findById(key, TokenData.class, COLLECTION_STRING);
        if (data == null || data.getExpireAt() == null) return SaTokenDao.NEVER_EXPIRE;
        long remain = (data.getExpireAt().getTime() - System.currentTimeMillis()) / 1000;
        return Math.max(remain, 0);
    }

    @Override
    public void updateTimeout(String key, long timeout) {
        if (timeout <= SaTokenDao.NOT_VALUE_EXPIRE) return;
        Date expireAt = (timeout == SaTokenDao.NEVER_EXPIRE) ? null : Date.from(Instant.now().plusSeconds(timeout));
        Update update = new Update().set("expireAt", expireAt);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(key)), update, COLLECTION_STRING);
    }

    @Override
    public Object getObject(String key) {
        TokenData data = mongoTemplate.findById(key, TokenData.class, COLLECTION_OBJECT);
        if (data != null && !data.isExpired()) {
            return data.getValue();
        }
        return null;
    }

    @Override
    public void setObject(String key, Object object, long timeout) {
        if (timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        Date expireAt = (timeout == SaTokenDao.NEVER_EXPIRE) ? null : Date.from(Instant.now().plusSeconds(timeout));
        TokenData data = new TokenData(key, object, expireAt);
        mongoTemplate.save(data, COLLECTION_OBJECT);
    }

    @Override
    public void updateObject(String key, Object object) {
        long timeout = getObjectTimeout(key);
        setObject(key, object, timeout);
    }

    @Override
    public void deleteObject(String key) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(key)), COLLECTION_OBJECT);
    }

    @Override
    public long getObjectTimeout(String key) {
        TokenData data = mongoTemplate.findById(key, TokenData.class, COLLECTION_OBJECT);
        if (data == null || data.getExpireAt() == null) return SaTokenDao.NEVER_EXPIRE;
        long remain = (data.getExpireAt().getTime() - System.currentTimeMillis()) / 1000;
        return Math.max(remain, 0);
    }

    @Override
    public void updateObjectTimeout(String key, long timeout) {
        if (timeout <= SaTokenDao.NOT_VALUE_EXPIRE) return;
        Date expireAt = (timeout == SaTokenDao.NEVER_EXPIRE) ? null : Date.from(Instant.now().plusSeconds(timeout));
        Update update = new Update().set("expireAt", expireAt);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(key)), update, COLLECTION_OBJECT);
    }

    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        // 简单模拟，非全文搜索
        Query query = new Query(Criteria.where("_id").regex(prefix + ".*" + keyword + ".*"));
        if (size > 0) {
            query.skip(start).limit(size);
        }
        List<TokenData> results = mongoTemplate.find(query, TokenData.class, COLLECTION_STRING);
        List<String> ids = new ArrayList<>();
        for (TokenData item : results) {
            ids.add(item.getId());
        }
        return ids;
    }

    // 内部 TokenData 文档模型
    @Data
    public static class TokenData implements Serializable {
        private String id;
        private Object value;
        private Date expireAt;

        public TokenData() {}

        public TokenData(String id, Object value, Date expireAt) {
            this.id = id;
            this.value = value;
            this.expireAt = expireAt;
        }

        public boolean isExpired() {
            return expireAt != null && System.currentTimeMillis() > expireAt.getTime();
        }
    }

    @JsonIgnoreProperties({"timeout"})
    public static class SaSessionForMongo extends SaSession implements Serializable {
        private static final long serialVersionUID = 1L;

        public SaSessionForMongo() {
            super();
        }

        public SaSessionForMongo(String id) {
            super(id);
        }
    }
}
