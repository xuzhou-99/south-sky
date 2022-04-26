package cn.altaria.sky.login.event.register;

import org.springframework.context.ApplicationEvent;

import cn.altaria.sky.login.web.vo.UserVO;


/**
 * UserRegisterEvent
 *
 * @author xuzhou
 * @version 1.0.0
 * @date 2021/4/9 16:58
 */
public class UserRegisterEvent extends ApplicationEvent {

    /**
     * serial Version UID
     */
    private static final long serialVersionUID = -5058858385060733297L;

    private final UserVO userVO;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserRegisterEvent(Object source, UserVO userVO) {
        super(source);
        this.userVO = userVO;
    }

    public UserVO getUser() {
        return userVO;
    }
}
