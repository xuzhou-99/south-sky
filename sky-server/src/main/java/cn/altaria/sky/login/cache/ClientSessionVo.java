package cn.altaria.sky.login.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ClientSessionVo
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/26 11:14
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class ClientSessionVo {
    /**
     * 上线系统
     */
    private String clientUrl;
    /**
     * session Id
     */
    private String jSessionId;
}
