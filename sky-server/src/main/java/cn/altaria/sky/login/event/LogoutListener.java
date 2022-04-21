package cn.altaria.sky.login.event;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * LogoutListener
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 14:01
 */
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
        // TODO: Implement
    }
}
