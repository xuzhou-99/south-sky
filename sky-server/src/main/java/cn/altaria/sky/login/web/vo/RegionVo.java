package cn.altaria.sky.login.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * RegionVo
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2021/11/17 14:47
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionVo {
    private String id;

    private String name;

    private String pid;

    private String dmjp;

    private String tier;
}
