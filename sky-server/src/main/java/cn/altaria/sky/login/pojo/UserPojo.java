package cn.altaria.sky.login.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Account
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/5/31 9:56
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class UserPojo {

    @NonNull
    private String id;

    private String name;

    private String username;

    private String password;

    private String email;

    private String mobile;

    private String landLine;

    private Date createTime;

    private Date updateTime;
}
