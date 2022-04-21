package cn.altaria.sky.login.pojo.node;

/**
 * INode
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/5/20 17:38
 */
public interface INode {
    /**
     * 转为/获取节点
     *
     * @return {@link Node}
     */
    Node toNode();

    /**
     * 获取父节点ID
     *
     * @return 父级节点ID
     */
    String parentId();
}
