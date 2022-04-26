package cn.altaria.sky.login.event.register;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.altaria.sky.login.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

/**
 * RegisterUserEmailListener
 *
 * @author xuzhou
 * @version 1.0.0
 * @date 2021/4/9 17:28
 */
@Component
@Slf4j
public class AnnotationRegisterUserEmailListener {
    /**
     * 使用注解{@linkplain EventListener}的方式来监听用户注册事件 UserRegisterEvent
     * {@linkplain Order}来标记同步时的执行顺序
     *
     * @param userRegisterEvent 用户注册事件
     */
    @EventListener
    @Order(value = 2)
    public void sendMail(UserRegisterEvent userRegisterEvent) {
        UserVO userVO = userRegisterEvent.getUser();


        log.info("用户注册成功，发送邮件: {}。", userVO.getEmail());
    }
}
