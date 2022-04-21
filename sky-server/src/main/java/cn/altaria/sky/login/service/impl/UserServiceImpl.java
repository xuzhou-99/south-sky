package cn.altaria.sky.login.service.impl;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.altaria.sky.login.exception.RegisterException;
import cn.altaria.sky.login.mapper.AccountMapper;
import cn.altaria.sky.login.mapper.UserMapper;
import cn.altaria.sky.login.pojo.AccountPojo;
import cn.altaria.sky.login.pojo.UserPojo;
import cn.altaria.sky.login.register.UserRegisterEvent;
import cn.altaria.sky.login.service.IUserService;
import cn.altaria.sky.login.sid.SIDUtils;
import cn.altaria.sky.login.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

/**
 * UserServiceImpl
 *
 * @author xuzhou
 * @version 1.0.0
 * @date 2021/4/9 17:07
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AccountMapper accountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserVO userVO) throws RegisterException {
        if (Objects.isNull(userVO)) {
            throw new RegisterException("用户信息不能为空！");
        }

        if (StringUtils.isEmpty(userVO.getUsername())) {
            throw new RegisterException("Username不能为空！");
        }

        if (StringUtils.isEmpty(userVO.getPassword())) {
            throw new RegisterException("Password不能为空！");
        }

        // do something
        UserPojo userPojo1 = userMapper.selectUserByEmail(userVO.getEmail());
        if (userPojo1 != null) {
            throw new RegisterException("E-mail 已经注册过！");
        }


        String id = SIDUtils.getSidStr();
        UserPojo userPojo = UserPojo.builder()
                .id(id)
                .username(userVO.getUsername())
                .password(userVO.getPassword())
                .email(userVO.getEmail())
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        String accountId = SIDUtils.getSidStr();
        AccountPojo accountPojo = AccountPojo.builder()
                .id(accountId)
                .uid(id)
                .username(userVO.getUsername())
                .password(userVO.getPassword())
                .email(userVO.getEmail())
                .createTime(new Date())
                .updateTime(new Date())
                .build();

//        userMapper.insertUser(userPojo);
        accountMapper.insertAccount(accountPojo);

        // publish register event
        applicationContext.publishEvent(new UserRegisterEvent(this, userVO));
    }

    @Override
    public UserPojo load(String id, String email, String username, String mobile) {
        UserPojo userPojo = null;
        if (!StringUtils.isEmpty(id)) {
            userPojo = userMapper.selectUserById(id);
        }
        if (Objects.isNull(userPojo) && !StringUtils.isEmpty(email)) {
            userPojo = userMapper.selectUserByEmail(email);
        }
        if (Objects.isNull(userPojo) && !StringUtils.isEmpty(username)) {
            userPojo = userMapper.selectUserByUsername(username);
        }
        if (Objects.isNull(userPojo) && !StringUtils.isEmpty(mobile)) {
            userPojo = userMapper.selectUserByMobile(mobile);
        }
        return userPojo;
    }
}
