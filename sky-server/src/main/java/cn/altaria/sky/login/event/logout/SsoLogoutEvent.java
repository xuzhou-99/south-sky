package cn.altaria.sky.login.event.logout;


import org.springframework.context.ApplicationEvent;

/**
 * SSOLogoutEvent SSO全局注销事件
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/26 20:08
 */
public class SsoLogoutEvent extends ApplicationEvent {
    /**
     * token 凭证
     */
    private final String token;

    public SsoLogoutEvent(Object source, String token) {
        super(source);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
