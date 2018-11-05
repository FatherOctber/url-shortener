package com.fatheroctober.urlshortener.dao;

import com.fatheroctober.urlshortener.dao.model.IUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Repository
@Transactional
public class UrlDao implements Dao<IUrl> {
    private static Logger logger = LoggerFactory.getLogger(UrlDao.class);
    private static final String ID_KEY = "ID";
    private static final String URL_KEY = "URL:";


    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, IUrl> hashOps;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, IUrl> incrOps;


    @Override
    public Optional<IUrl> get(Long id) {
        logger.info("Get original url for id=" + id);
        Optional<IUrl> url = Optional.ofNullable(id).map(i -> Optional.ofNullable(hashOps.get(URL_KEY, i)))
                .orElseThrow(() -> new RuntimeException("Can not perform operation for id: " + id));
        return url;
    }

    @Override
    public Long save(IUrl value) {
        if (value != null) {
            logger.info("Save short url=" + value);
            Long id = generateId();
            hashOps.put(URL_KEY, id, value);
            return id;
        } else {
            throw new RuntimeException("Can not save null object");
        }
    }

    private Long generateId() {
        Long id = incrOps.increment(ID_KEY, 1L);
        return id;
    }
}
