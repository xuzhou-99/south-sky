package cn.altaria.sky.login.cache;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * SystemRegister
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 16:35
 */
public class SystemSessionRegister {

    private static final Cache<String, List<ClientSessionVo>> SYSTEM_SESSION_REGISTER = Caffeine.newBuilder()
            .initialCapacity(1000)
            .maximumSize(2000L)
            .expireAfterWrite(30L, TimeUnit.MINUTES)
            .weakValues()
            .build();

    private static final SystemSessionRegister INSTANCE = new SystemSessionRegister();

    private SystemSessionRegister() {
    }

    public static SystemSessionRegister getINSTANCE() {
        return INSTANCE;
    }

    public List<ClientSessionVo> get(final String key) {
        return SYSTEM_SESSION_REGISTER.getIfPresent(key);
    }

    public void put(final String key, final List<ClientSessionVo> value) {
        SYSTEM_SESSION_REGISTER.put(key, value);
    }

    public void remove(final String key) {
        SYSTEM_SESSION_REGISTER.invalidate(key);
    }

    public boolean containsKey(final String key) {
        return Objects.nonNull(SYSTEM_SESSION_REGISTER.getIfPresent(key));
    }

    public long size() {
        return SYSTEM_SESSION_REGISTER.estimatedSize();
    }

}
