package cn.altaria.sky.login.event.logout;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cn.altaria.sky.login.cache.ClientSessionVo;
import cn.altaria.sky.login.cache.SystemSessionRegister;
import cn.altaria.sky.login.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * SsoLogoutListener SSO全局注销事件监听器
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/26 20:13
 */
@Component
@Slf4j
public class SsoLogoutListener implements ApplicationListener<SsoLogoutEvent> {

    /**
     * @param event 单点登录登出事件
     */
    @Override
    public void onApplicationEvent(SsoLogoutEvent event) {
        String token = event.getToken();

        log.info("【SSO登出】用户注销，通知其他系统下线登出，token = {}", token);
        if (token != null) {
            List<ClientSessionVo> clients = SystemSessionRegister.getINSTANCE().get(token);
            for (ClientSessionVo client : clients) {
                log.info("【SSO登出】下线登出系统，clientUrl = {}", client.getClientUrl());
                HttpUtil.doGet(client.getClientUrl() + "/sso-logout", client.getJSessionId());
            }
        }
        log.info("【SSO登出】{} 登录的系统全部登出", token);
    }
}
