package cn.altaria.sky.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * SSOLoginConfig
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/21 20:48
 */
@Component
@Getter
@Setter
public class SSOLoginConfig {

    @Value("${sso.url}")
    private String url;

    @Value("${sso.enable}")
    private Boolean enable;
}
