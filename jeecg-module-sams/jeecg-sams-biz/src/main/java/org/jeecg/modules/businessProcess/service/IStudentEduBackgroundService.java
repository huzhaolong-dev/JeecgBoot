package org.jeecg.modules.businessProcess.service;

import org.jeecg.modules.businessProcess.entity.StudentEduBackground;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 学生教育信息模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
public interface IStudentEduBackgroundService extends IService<StudentEduBackground> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<StudentEduBackground>
   */
	public List<StudentEduBackground> selectByMainId(String mainId);
}
