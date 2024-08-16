package org.jeecg.modules.businessProcess.service.impl;

import org.jeecg.modules.businessProcess.entity.StudentWorkExperience;
import org.jeecg.modules.businessProcess.mapper.StudentWorkExperienceMapper;
import org.jeecg.modules.businessProcess.service.IStudentWorkExperienceService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 学生工作经历模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Service
public class StudentWorkExperienceServiceImpl extends ServiceImpl<StudentWorkExperienceMapper, StudentWorkExperience> implements IStudentWorkExperienceService {
	
	@Autowired
	private StudentWorkExperienceMapper studentWorkExperienceMapper;
	
	@Override
	public List<StudentWorkExperience> selectByMainId(String mainId) {
		return studentWorkExperienceMapper.selectByMainId(mainId);
	}
}
