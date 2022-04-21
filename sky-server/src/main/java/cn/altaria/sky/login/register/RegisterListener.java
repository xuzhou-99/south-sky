package cn.altaria.sky.login.register;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.altaria.sky.login.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

/**
 * RegisterListener
 *
 * @author xuzhou
 * @version 1.0.0
 * @date 2021/4/9 17:23
 */
@Component
@Slf4j
@Order(0)
public class RegisterListener implements ApplicationListener<UserRegisterEvent> {

    /**
     * 使用接口实现的方式来监听事件
     *
     * @param event 用户注册事件
     */
    @Override
    public void onApplicationEvent(UserRegisterEvent event) {

        UserVO userVO = event.getUser();

        // do something

        log.info("ApplicationLister<UserRegisterEvent>注册信息，用户名：" + userVO.getUsername() + "，密码：" + userVO.getPassword());

    }
}
