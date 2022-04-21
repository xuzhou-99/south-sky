package cn.altaria.sky.login.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.altaria.sky.login.pojo.AccountPojo;
import cn.altaria.sky.login.pojo.UserPojo;

/**
 * AccountMapper
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/7/22 14:31
 */
@Mapper
public interface AccountMapper {
    /**
     * 插入账户
     *
     * @param account 账户信息
     */
    void insertAccount(@Param("account") AccountPojo account);

    /**
     * 根据邮箱查询账户
     *
     * @param email {@link UserPojo#getEmail()}
     * @return {@linkplain UserPojo}
     */
    AccountPojo selectAccountByEmail(String email);

    AccountPojo selectAccountById(String id);

    AccountPojo selectAccountByUsername(String username);

    AccountPojo selectAccountByMobile(String mobile);
}
