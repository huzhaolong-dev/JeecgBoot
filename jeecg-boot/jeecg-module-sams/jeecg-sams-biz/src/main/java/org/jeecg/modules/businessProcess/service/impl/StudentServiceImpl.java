package org.jeecg.modules.businessProcess.service.impl;

import org.jeecg.modules.businessProcess.entity.Student;
import org.jeecg.modules.businessProcess.mapper.StudentGradeMapper;
import org.jeecg.modules.businessProcess.mapper.StudentEduBackgroundMapper;
import org.jeecg.modules.businessProcess.mapper.StudentWorkExperienceMapper;
import org.jeecg.modules.businessProcess.mapper.StudentMaterialMapper;
import org.jeecg.modules.businessProcess.mapper.StudentMapper;
import org.jeecg.modules.businessProcess.service.IStudentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: student
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private StudentGradeMapper studentGradeMapper;
	@Autowired
	private StudentEduBackgroundMapper studentEduBackgroundMapper;
	@Autowired
	private StudentWorkExperienceMapper studentWorkExperienceMapper;
	@Autowired
	private StudentMaterialMapper studentMaterialMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		studentGradeMapper.deleteByMainId(id);
		studentEduBackgroundMapper.deleteByMainId(id);
		studentWorkExperienceMapper.deleteByMainId(id);
		studentMaterialMapper.deleteByMainId(id);
		studentMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			studentGradeMapper.deleteByMainId(id.toString());
			studentEduBackgroundMapper.deleteByMainId(id.toString());
			studentWorkExperienceMapper.deleteByMainId(id.toString());
			studentMaterialMapper.deleteByMainId(id.toString());
			studentMapper.deleteById(id);
		}
	}
	
}
