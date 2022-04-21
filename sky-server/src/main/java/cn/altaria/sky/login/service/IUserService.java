package cn.altaria.sky.login.service;


import cn.altaria.sky.login.exception.RegisterException;
import cn.altaria.sky.login.pojo.UserPojo;
import cn.altaria.sky.login.web.vo.UserVO;

/**
 * IUserService
 *
 * @author xuzhou
 * @version 1.0.0
 * @date 2021/4/9 17:06
 */
public interface IUserService {


    /**
     * register
     *
     * @param userVO {@linkplain UserVO}
     * @throws RegisterException {@linkplain RegisterException}
     */
    void register(UserVO userVO) throws RegisterException;

    /**
     * 获取用户
     *
     * @param id       用户ID
     * @param email    邮箱
     * @param username 用户名
     * @param mobile   手机号
     * @return {@link UserPojo}
     */
    UserPojo load(String id, String email, String username, String mobile);
}
