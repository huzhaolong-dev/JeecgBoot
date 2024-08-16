package org.jeecg.modules.businessProcess.service;

import org.jeecg.modules.businessProcess.entity.StudentMaterial;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 学生材料模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
public interface IStudentMaterialService extends IService<StudentMaterial> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<StudentMaterial>
   */
	public List<StudentMaterial> selectByMainId(String mainId);
}
