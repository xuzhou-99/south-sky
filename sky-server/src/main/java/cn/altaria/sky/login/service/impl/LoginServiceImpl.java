package cn.altaria.sky.login.service.impl;


import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.altaria.sky.login.cache.SystemRegister;
import cn.altaria.sky.login.exception.LoginException;
import cn.altaria.sky.login.pojo.UserPojo;
import cn.altaria.sky.login.service.ILoginService;
import cn.altaria.sky.login.service.IUserService;
import lombok.extern.slf4j.Slf4j;

/**
 * LoginServiceImpl
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 13:57
 */
@Service
@Slf4j
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
    public void logout(String token) {
        if (null != token) {
            SystemRegister.getINSTANCE().remove(token);
            log.info("【SSO单点登录】移除token");
        }

    }

    @Override
    public String verify(HttpServletRequest request, String token) {
        if (SystemRegister.getINSTANCE().containsKey(token)) {
            String systemUrl = request.getParameter("service");
            if(systemUrl == null){
                systemUrl = request.getRequestURL().toString();
            }
            // 注册系统 systemUrl
            SystemRegister.getINSTANCE().put(token, systemUrl);
            log.info("【SSO单点登录】系统上线：{}", systemUrl);
            return "true";
        } else {
            return "fail";
        }
    }

}
