package com.jdcloud.jmsf.demo.springcloud.provider.cache;

import com.github.benmanes.caffeine.cache.LoadingCache;
import io.meshware.cache.ihc.AbstractStringSynchronousCache;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * DemoLocalCache
 * <p>
 * 本地缓存实现示例，泛型内定义要缓存对象类型
 * </p>
 *
 * @author Zhiguo.Chen
 * @since 20211206
 */
@Component
public class DemoLocalCache extends AbstractStringSynchronousCache<String> {

    // @Autowired
    // RedisCache redisCache;

    /**
     * Init cache config
     */
    @Override
    public void initConfig() {
        this.setExpireTimeUnit(TimeUnit.MINUTES);
        this.setExpireDurationAfterAccess(5);
    }

    /**
     * Init cache
     *
     * @param cache cache
     */
    @Override
    public void initCache(LoadingCache<String, String> cache) {

    }

    /**
     * Get data when old data expired
     *
     * @param key key
     * @return V Not null
     * @throws Exception exception
     */
    @Override
    protected String getValueWhenExpired(String key) throws Exception {
        //如果本地缓存没有数据或数据失效，此方法体内实现从远端获取数据

        // return redisCache.get(key);
        return null;
    }
}
