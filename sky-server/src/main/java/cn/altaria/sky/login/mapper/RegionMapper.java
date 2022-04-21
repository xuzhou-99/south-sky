package cn.altaria.sky.login.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.altaria.sky.login.pojo.node.Region;

/**
 * Region
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/5/21 10:11
 */
@Mapper
public interface RegionMapper {
    /**
     * 获取全部
     *
     * @return List<Region>
     */
    List<Region> selectAll();

    /**
     * 获取指定地区的下级所有
     *
     * @param region 指定地区
     * @return List<Region>
     */
    List<Region> selectChildList(@Param("region") Region region);

}
