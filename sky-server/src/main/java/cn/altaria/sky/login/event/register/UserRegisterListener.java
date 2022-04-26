package cn.altaria.sky.login.event.register;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import cn.altaria.sky.login.service.impl.UserServiceImpl;
import cn.altaria.sky.login.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;


/**
 * UserRegisterListener
 *
 * @author xuzhou
 * @version 1.0.0
 * @date 2021/4/9 17:31
 */
@Component
@Slf4j
public class UserRegisterListener implements SmartApplicationListener {

    /**
     * Determine whether this listener actually supports the given event type.
     *
     * @param eventType the event type (never {@code null})
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        // support UserRegisterEvent.class
        return eventType == UserRegisterEvent.class;
    }

    /**
     * Determine whether this listener actually supports the given source type.
     * <p>The default implementation always returns {@code true}.
     *
     * @param sourceType the source type, or {@code null} if no source
     */
    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        // support UserServiceImpl which publish event
        return sourceType == UserServiceImpl.class;
    }

    /**
     * Determine this listener's order in a set of listeners for the same event.
     * <p>The default implementation returns {@link #LOWEST_PRECEDENCE}.
     */
    @Override
    public int getOrder() {
        // 0 is highest priority
        return 0;
    }

    @Override
    public String getListenerId() {
        return null;
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // change event type into target event
        UserRegisterEvent userRegisterEvent = (UserRegisterEvent) event;

        // get user
        UserVO userVO = userRegisterEvent.getUser();

        // do something

        // print
        log.info("注册监听器001：执行顺序：" + getOrder());
        log.info("注册信息，用户名：" + userVO.getUsername() + "，密码：" + userVO.getPassword());


    }
}
