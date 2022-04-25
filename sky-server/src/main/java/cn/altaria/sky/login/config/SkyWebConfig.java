package cn.altaria.sky.login.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SkyWebConfig
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/25 11:56
 */
@Configuration
public class SkyWebConfig {

    @Bean
    public FilterRegistrationBean<SkyFilter> skyFilter() {
        FilterRegistrationBean<SkyFilter> filter = new FilterRegistrationBean<>();
        filter.setName("skyfilter");
        filter.setFilter(new SkyFilter());
        filter.setOrder(-1);
        return filter;
    }
}
