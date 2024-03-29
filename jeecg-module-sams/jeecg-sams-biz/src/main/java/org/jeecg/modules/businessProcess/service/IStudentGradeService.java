package org.jeecg.modules.businessProcess.service;

import org.jeecg.modules.businessProcess.entity.StudentGrade;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 学生成绩模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
public interface IStudentGradeService extends IService<StudentGrade> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<StudentGrade>
   */
	public List<StudentGrade> selectByMainId(String mainId);
}
