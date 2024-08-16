package org.jeecg.modules.businessProcess.service.impl;

import org.jeecg.modules.businessProcess.entity.StudentGrade;
import org.jeecg.modules.businessProcess.mapper.StudentGradeMapper;
import org.jeecg.modules.businessProcess.service.IStudentGradeService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 学生成绩模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Service
public class StudentGradeServiceImpl extends ServiceImpl<StudentGradeMapper, StudentGrade> implements IStudentGradeService {
	
	@Autowired
	private StudentGradeMapper studentGradeMapper;
	
	@Override
	public List<StudentGrade> selectByMainId(String mainId) {
		return studentGradeMapper.selectByMainId(mainId);
	}
}
