package cn.altaria.sky.login.web.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * User
 *
 * @author xuzhou
 * @version 1.0.0
 * @date 2021/4/9 16:59
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVO implements Serializable {

    private static final long serialVersionUID = -8967972309006473679L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * email
     */
    private String email;
}
