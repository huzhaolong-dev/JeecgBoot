package org.jeecg.modules.businessProcess.mapper;

import java.util.List;
import org.jeecg.modules.businessProcess.entity.StudentEduBackground;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 学生教育信息模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
public interface StudentEduBackgroundMapper extends BaseMapper<StudentEduBackground> {

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
    * @return List<StudentEduBackground>
    */
	public List<StudentEduBackground> selectByMainId(@Param("mainId") String mainId);

}
