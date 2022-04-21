package cn.altaria.sky.login.pojo.node;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Region
 * 地区
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/5/20 17:46
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Region {
    /**
     * 节点ID
     */
    private Integer id;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 父节点ID
     */
    private Integer parentId;
    /**
     * Code
     */
    private String code;
    /**
     * 排序
     */
    private Integer order;
    /**
     * 后缀（行政级别）
     */
    private String suffix;
    /**
     * 节点类型
     */
    private String type;
    /**
     * 是否为父节点
     */
    private Boolean isParent = false;
    /**
     * 是否打开
     */
    private Boolean open = false;
    /**
     * 子节点
     */
    private List<Region> children;
    /**
     * 标题
     */
    private String title;
    /**
     * 图标
     */
    private String icon;
    /**
     * 扩展
     */
    private Map<String, Object> ext;

    private Integer levelInfo;

}
