package org.jeecg.modules.businessProcess.service.impl;

import org.jeecg.modules.businessProcess.entity.StudentEduBackground;
import org.jeecg.modules.businessProcess.mapper.StudentEduBackgroundMapper;
import org.jeecg.modules.businessProcess.service.IStudentEduBackgroundService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 学生教育信息模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Service
public class StudentEduBackgroundServiceImpl extends ServiceImpl<StudentEduBackgroundMapper, StudentEduBackground> implements IStudentEduBackgroundService {
	
	@Autowired
	private StudentEduBackgroundMapper studentEduBackgroundMapper;
	
	@Override
	public List<StudentEduBackground> selectByMainId(String mainId) {
		return studentEduBackgroundMapper.selectByMainId(mainId);
	}
}
