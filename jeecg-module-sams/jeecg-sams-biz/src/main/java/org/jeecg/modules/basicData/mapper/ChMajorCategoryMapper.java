package org.jeecg.modules.basicData.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.basicData.entity.ChMajorCategory;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 * 中国专业分类 Mapper 接口
 * <p>
 * 
 * @Author: Steve
 * @Since：   2019-01-22
 */
public interface ChMajorCategoryMapper extends BaseMapper<ChMajorCategory> {

    /**
     * 修改中国专业分类状态字段： 是否有子节点
     * @param code 编码
     * @param hasChild 是否有子节点
     * @return int
     */
    @Update("UPDATE ch_major_category SET has_child=#{hasChild} WHERE code = #{code}")
    int setHasChild(@Param("code") String code, @Param("hasChild") String hasChild);
}
