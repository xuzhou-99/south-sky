package cn.altaria.sky.login.pojo.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * INode
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/5/20 17:27
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Node {
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

}
