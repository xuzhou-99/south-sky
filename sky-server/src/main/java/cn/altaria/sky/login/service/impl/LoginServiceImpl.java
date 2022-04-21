package cn.altaria.sky.login.service.impl;


import java.util.Objects;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import cn.altaria.sky.login.exception.LoginException;
import cn.altaria.sky.login.pojo.UserPojo;
import cn.altaria.sky.login.service.ILoginService;
import cn.altaria.sky.login.service.IUserService;

/**
 * LoginServiceImpl
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 13:57
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private IUserService userService;

    @Override
    public boolean login(String email, String password) throws LoginException {

        if (email == null) {
            throw new LoginException("用户密码不能为空！");
        }
        UserPojo userPojo = userService.load(null, email, null, null);
        if (userPojo == null) {
            throw new LoginException("用户不存在！");
        }
        if (!Objects.equals(userPojo.getPassword(), password)) {
            throw new LoginException("用户名或密码错误！");
        }

        return true;
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (null != subject) {
            if (subject.isAuthenticated() || subject.isRemembered()) {
                subject.logout();
            }
        }
        afterLogout();
    }

    /**
     * 登出后事件
     */
    protected void afterLogout() {

    }
}
