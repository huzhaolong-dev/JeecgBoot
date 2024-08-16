package org.jeecg.modules.businessProcess.mapper;

import java.util.List;
import org.jeecg.modules.businessProcess.entity.StudentWorkExperience;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 学生工作经历模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
public interface StudentWorkExperienceMapper extends BaseMapper<StudentWorkExperience> {

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
    * @return List<StudentWorkExperience>
    */
	public List<StudentWorkExperience> selectByMainId(@Param("mainId") String mainId);

}
