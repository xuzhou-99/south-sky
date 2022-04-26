package cn.altaria.sky.login.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

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
     * @return 登录成功
     * @throws LoginException 登录异常
     */
    boolean login(final String email, final String password) throws LoginException;


    /**
     * 单点登录
     *
     * @param request  请求
     * @param response 响应
     * @param email    邮箱
     * @param password 密码
     * @return 登录结果
     * @throws LoginException 登录异常
     */
    ResponseEntity<?> loginSso(HttpServletRequest request, HttpServletResponse response, String email, String password) throws LoginException;

    /**
     * 登出
     *
     * @param request 请求
     * @param token   token
     */
    void logout(HttpServletRequest request, String token);

    /**
     * 令牌校验
     *
     * @param request    请求
     * @param token      令牌
     * @param jsessionid 会话ID
     * @return 令牌校验结果
     */
    String verify(HttpServletRequest request, String token, String jsessionid);
}
