package org.jeecg.modules.businessProcess.mapper;

import java.util.List;
import org.jeecg.modules.businessProcess.entity.StudentMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 学生材料模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
public interface StudentMaterialMapper extends BaseMapper<StudentMaterial> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

   /**
    * 通过主表id查询子表数据
    *
    * @param mainId 主表id
    * @return List<StudentMaterial>
    */
	public List<StudentMaterial> selectByMainId(@Param("mainId") String mainId);

}
