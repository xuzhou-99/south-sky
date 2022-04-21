package cn.altaria.sky.login.register;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.altaria.sky.login.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

/**
 * AnnotationRegisterListener
 *
 * @author xuzhou
 * @version 1.0.0
 * @date 2021/4/9 17:15
 */
@Component
@Slf4j
public class AnnotationRegisterListener {

    /**
     * 使用注解{@linkplain EventListener}的方式来监听用户注册事件 UserRegisterEvent
     * {@linkplain Order}来标记同步时的执行顺序
     *
     * @param userRegisterEvent 用户注册事件
     */
    @EventListener
    @Order(value = 3)
    public void register(UserRegisterEvent userRegisterEvent) {

        // get register user
        UserVO userVO = userRegisterEvent.getUser();

        // do something


        log.info("@EventListener注册信息，用户名：" + userVO.getUsername() + "，密码：" + userVO.getPassword());
    }
}
