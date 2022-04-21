package cn.altaria.sky.login.cache;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * TokenRegister
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 16:47
 */
public class TokenRegister {
    private static final Cache<String, String> TOKEN_REGISTER = Caffeine.newBuilder()
            .initialCapacity(1000)
            .maximumSize(2000L)
            .expireAfterWrite(30L, TimeUnit.MINUTES)
            .weakKeys()
            .build();

    private static final TokenRegister INSTANCE = new TokenRegister();

    private TokenRegister() {
    }

    public static TokenRegister getINSTANCE() {
        return INSTANCE;
    }

    public String get(final String key) {
        return TOKEN_REGISTER.getIfPresent(key);
    }

    public void put(final String key, final String value) {
        TOKEN_REGISTER.put(key, value);
    }

    public void remove(final String key) {
        TOKEN_REGISTER.invalidate(key);
    }

    public boolean containsKey(final String key) {
        return Objects.nonNull(TOKEN_REGISTER.getIfPresent(key));
    }

    public long size() {
        return TOKEN_REGISTER.estimatedSize();
    }

}
