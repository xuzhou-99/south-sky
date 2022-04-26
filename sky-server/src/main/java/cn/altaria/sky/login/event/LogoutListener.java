package cn.altaria.sky.login.event;

import java.io.IOException;
import java.util.List;


import javax.servlet.annotation.WebListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.altaria.sky.login.cache.ClientSessionVo;
import cn.altaria.sky.login.cache.SystemSessionRegister;
import lombok.extern.slf4j.Slf4j;

/**
 * LogoutListener
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 14:01
 */
@Slf4j
@WebListener
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

        log.info("【登出】下线登出其他系统，token = {}", token);
        List<ClientSessionVo> clients = SystemSessionRegister.getINSTANCE().get(token);
        for (ClientSessionVo client : clients) {
            log.info("【登出】下线登出其他系统，clientUrl = {}", client.getClientUrl());
            doGet(client.getClientUrl() + "/sso-logout", client.getJSessionId());
        }
        log.info("【登出】{} 登录的系统全部登出", token);
    }

    private String doGet(String url, String jsessionid) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            log.info("【HTTP】Get请求 url = {}", url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Cookie", "JSESSIONID=" + jsessionid);
            // ... do something with response
            return httpClient.execute(httpGet, httpResponse -> {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status < 200 || status >= 300) {
                    // ... handle unsuccessful request
                    return null;
                }
                HttpEntity entity = httpResponse.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            });
        } catch (IOException e) {
            // ... handle IO exception
        }
        return "";
    }
}
