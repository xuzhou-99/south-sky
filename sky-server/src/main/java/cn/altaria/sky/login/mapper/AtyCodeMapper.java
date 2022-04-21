package cn.altaria.sky.login.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.altaria.sky.login.web.vo.RegionVo;

/**
 * AtyCodeMapper
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2021/11/17 14:45
 */
@Mapper
public interface AtyCodeMapper {

    /**
     * 获取省市区
     *
     * @return 行政区列表
     */
    @Select("SELECT c_code id,c_name as name,c_dmjp dmjp, " +
            " CASE " +
            " WHEN array_length( string_to_array( c_levelinfo, '.' ), 1 ) = 1 THEN '0'  " +
            " WHEN array_length( string_to_array( c_levelinfo, '.' ), 1 ) = 2 THEN split_part( c_levelinfo, '.', 1 ) " +
            " ELSE split_part( c_levelinfo, '.', 2 )  " +
            " END pid,  " +
            " CASE " +
            " WHEN array_length( string_to_array( c_levelinfo, '.' ), 1 ) = 1 THEN '1'  " +
            " WHEN array_length( string_to_array( c_levelinfo, '.' ), 1 ) = 2 THEN '2' " +
            " ELSE '3'  " +
            " END tier  " +
            "FROM t_aty_code WHERE c_pid = '1032' and n_valid = '1';")
    List<RegionVo> getRegionData();
}
