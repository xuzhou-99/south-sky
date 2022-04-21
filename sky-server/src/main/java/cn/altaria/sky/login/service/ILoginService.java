package cn.altaria.sky.login.service;


import cn.altaria.sky.login.exception.LoginException;
import cn.altaria.sky.login.pojo.UserPojo;

/**
 * ILoginService
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 13:57
 */
public interface ILoginService {

    /**
     * 登录
     *
     * @param email    {@link UserPojo#getEmail()}
     * @param password {@link UserPojo#getPassword()}
     * @throws LoginException {@link LoginException}
     */
    boolean login(final String email, final String password) throws LoginException;

    /**
     * 登出
     */
    void logout();
}
