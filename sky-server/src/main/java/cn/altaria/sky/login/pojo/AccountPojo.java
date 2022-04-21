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
 * AccountPojo
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/7/22 14:27
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class AccountPojo {

    @NonNull
    private String id;

    private String uid;

    private String username;

    private String password;

    private String email;

    private String mobile;

    private Date createTime;

    private Date updateTime;
}
