package cn.altaria.sky.login.event.register;


import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import cn.altaria.sky.login.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;


/**
 * UserRegisterSendEmailListener
 *
 * @author xuzhou
 */
@Component
@Slf4j
public class UserRegisterSendEmailListener implements SmartApplicationListener {
    /**
     * 该方法返回true&supportsSourceType返回结果也是true时，才会调用该监听器中的onApplicationEvent方法
     *
     * @param aClass 监听器接收到的事件类型
     * @return boolean
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        // 只对UserRegisterEvent事件类型做处理
        return aClass == UserRegisterEvent.class;
    }

    /**
     * 该方法返回true&supportsEventType返回结果也是true时，才会调用该监听器中的onApplicationEvent方法
     *
     * @param sourceType 发起事件的source的类型
     * @return boolean
     */
    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        // 只对 UserServiceImpl 发布的事件做处理
        return sourceType == UserServiceImpl.class;
    }

    /**
     * 监听器对事件做处理的执行优先度
     *
     * @return int
     */
    @Override
    public int getOrder() {
        // 0 is highest level
        return 1;
    }

    @Override
    public String getListenerId() {
        return null;
    }

    /**
     * supportsEventType & supportsSourceType 返回值为true时调用的对事件的处理方法
     *
     * @param applicationEvent 事件
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        // 转换事件类型为制定类型
        UserRegisterEvent event = (UserRegisterEvent) applicationEvent;

        // do something

        // 打印日志
        log.info("注册监听器：邮件002：执行顺序：" + getOrder());
        log.info("用户注册成功，发送邮件通知");

    }
}
