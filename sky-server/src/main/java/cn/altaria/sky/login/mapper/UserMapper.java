package cn.altaria.sky.login.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.altaria.sky.login.pojo.UserPojo;

/**
 * UserMapper
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/5/31 10:03
 */
@Mapper
public interface UserMapper {

    /**
     * 插入账户
     *
     * @param userPojo 账户信息
     */
    void insertUser(@Param("userPojo") UserPojo userPojo);

    /**
     * 根据邮箱查询账户
     *
     * @param email {@link UserPojo#getEmail()}
     * @return {@linkplain UserPojo}
     */
    UserPojo selectUserByEmail(String email);

    UserPojo selectUserById(String id);

    UserPojo selectUserByUsername(String username);

    UserPojo selectUserByMobile(String mobile);
}
