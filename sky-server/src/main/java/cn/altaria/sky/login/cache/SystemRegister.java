package cn.altaria.sky.login.cache;

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
public class SystemRegister {

    private static final Cache<String, String> SYSTEM_REGISTER = Caffeine.newBuilder()
            .initialCapacity(1000)
            .maximumSize(2000L)
            .expireAfterWrite(30L, TimeUnit.MINUTES)
            .weakValues()
            .build();

    private static final SystemRegister INSTANCE = new SystemRegister();

    private SystemRegister() {
    }

    public static SystemRegister getINSTANCE() {
        return INSTANCE;
    }

    public String get(final String key) {
        return SYSTEM_REGISTER.getIfPresent(key);
    }

    public void put(final String key, final String value) {
        SYSTEM_REGISTER.put(key, value);
    }

    public void remove(final String key) {
        SYSTEM_REGISTER.invalidate(key);
    }

    public boolean containsKey(final String key) {
        return Objects.nonNull(SYSTEM_REGISTER.getIfPresent(key));
    }

    public long size() {
        return SYSTEM_REGISTER.estimatedSize();
    }

}
