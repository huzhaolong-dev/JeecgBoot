package org.jeecg.modules.businessProcess.service;

import org.jeecg.modules.businessProcess.entity.StudentWorkExperience;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 学生工作经历模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
public interface IStudentWorkExperienceService extends IService<StudentWorkExperience> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<StudentWorkExperience>
   */
	public List<StudentWorkExperience> selectByMainId(String mainId);
}
