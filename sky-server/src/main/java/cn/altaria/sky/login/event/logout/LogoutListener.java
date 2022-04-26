package cn.altaria.sky.login.event.logout;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationEvent;

import cn.altaria.sky.login.cache.ClientSessionVo;
import cn.altaria.sky.login.cache.SystemSessionRegister;
import cn.altaria.sky.login.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * LogoutListener
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 14:01
 * @deprecated 实现单点登出时，需要将client的局部session与sso服务的全局session相关联，为了避免client的冗余，所以采用
 * {@link  ApplicationEvent} 的方式实现子系统全局注销。当注销SSO时，也需要进行  {@link  HttpSession#invalidate()}
 * 如果采用监听session的方式， * 会出现嵌套使用，所以注销消息的通知全部采用 {@link  ApplicationEvent}
 */
@Slf4j
//@WebListener
@Deprecated
public class LogoutListener implements HttpSessionListener {
    /**
     * Notification that a session was created.
     *
     * @param se the notification event
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    /**
     * Notification that a session is about to be invalidated.
     *
     * @param se the notification event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String token = (String) session.getAttribute("token");

        log.info("【登出】SSO服务用户注销，通知其他系统下线登出，token = {}", token);
        if (token != null) {
            List<ClientSessionVo> clients = SystemSessionRegister.getINSTANCE().get(token);
            for (ClientSessionVo client : clients) {
                log.info("【登出】下线登出系统，clientUrl = {}", client.getClientUrl());
                HttpUtil.doGet(client.getClientUrl() + "/sso-logout", client.getJSessionId());
            }
        }
        log.info("【登出】{} 登录的系统全部登出", token);
    }
}
