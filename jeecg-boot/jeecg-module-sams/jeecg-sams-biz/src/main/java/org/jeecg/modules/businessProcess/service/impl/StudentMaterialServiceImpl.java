package org.jeecg.modules.businessProcess.service.impl;

import org.jeecg.modules.businessProcess.entity.StudentMaterial;
import org.jeecg.modules.businessProcess.mapper.StudentMaterialMapper;
import org.jeecg.modules.businessProcess.service.IStudentMaterialService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 学生材料模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Service
public class StudentMaterialServiceImpl extends ServiceImpl<StudentMaterialMapper, StudentMaterial> implements IStudentMaterialService {
	
	@Autowired
	private StudentMaterialMapper studentMaterialMapper;
	
	@Override
	public List<StudentMaterial> selectByMainId(String mainId) {
		return studentMaterialMapper.selectByMainId(mainId);
	}
}
