package org.jeecg.modules.businessProcess.controller;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.basicData.entity.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.businessProcess.entity.StudentGrade;
import org.jeecg.modules.businessProcess.entity.StudentEduBackground;
import org.jeecg.modules.businessProcess.entity.StudentWorkExperience;
import org.jeecg.modules.businessProcess.entity.StudentMaterial;
import org.jeecg.modules.businessProcess.entity.Student;
import org.jeecg.modules.businessProcess.service.IStudentService;
import org.jeecg.modules.businessProcess.service.IStudentGradeService;
import org.jeecg.modules.businessProcess.service.IStudentEduBackgroundService;
import org.jeecg.modules.businessProcess.service.IStudentWorkExperienceService;
import org.jeecg.modules.businessProcess.service.IStudentMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: student
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Api(tags="student")
@RestController
@RequestMapping("/businessProcess/student")
@Slf4j
public class StudentController extends JeecgController<Student, IStudentService> {

	@Autowired
	private IStudentService studentService;

	@Autowired
	private IStudentGradeService studentGradeService;

	@Autowired
	private IStudentEduBackgroundService studentEduBackgroundService;

	@Autowired
	private IStudentWorkExperienceService studentWorkExperienceService;

	@Autowired
	private IStudentMaterialService studentMaterialService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param student
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "student-分页列表查询")
	@ApiOperation(value="student-分页列表查询", notes="student-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Student>> queryPageList(Student student,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Student> queryWrapper = QueryGenerator.initQueryWrapper(student, req.getParameterMap());
		Page<Student> page = new Page<Student>(pageNo, pageSize);
		IPage<Student> pageList = studentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param student
     * @return
     */
    @AutoLog(value = "student-添加")
    @ApiOperation(value="student-添加", notes="student-添加")
    @RequiresPermissions("businessProcess:student:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody Student student) {
        studentService.save(student);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param student
     * @return
     */
    @AutoLog(value = "student-编辑")
    @ApiOperation(value="student-编辑", notes="student-编辑")
    @RequiresPermissions("businessProcess:student:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
    public Result<String> edit(@RequestBody Student student) {
        studentService.updateById(student);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "student-通过id删除")
    @ApiOperation(value="student-通过id删除", notes="student-通过id删除")
    @RequiresPermissions("businessProcess:student:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name="id",required=true) String id) {
        studentService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "student-批量删除")
    @ApiOperation(value="student-批量删除", notes="student-批量删除")
    @RequiresPermissions("businessProcess:student:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.studentService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequiresPermissions("businessProcess:student:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Student student) {
        return super.exportXls(request, student, Student.class, "student");
    }

    /**
     * 导入
     * @return
     */
    @RequiresPermissions("businessProcess:student:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Student.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-学生成绩模块-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "学生成绩模块-通过主表ID查询")
	@ApiOperation(value="学生成绩模块-通过主表ID查询", notes="学生成绩模块-通过主表ID查询")
	@GetMapping(value = "/listStudentGradeByMainId")
    public Result<IPage<StudentGrade>> listStudentGradeByMainId(StudentGrade studentGrade,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<StudentGrade> queryWrapper = QueryGenerator.initQueryWrapper(studentGrade, req.getParameterMap());
        Page<StudentGrade> page = new Page<StudentGrade>(pageNo, pageSize);
        IPage<StudentGrade> pageList = studentGradeService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param studentGrade
	 * @return
	 */
	@AutoLog(value = "学生成绩模块-添加")
	@ApiOperation(value="学生成绩模块-添加", notes="学生成绩模块-添加")
	@PostMapping(value = "/addStudentGrade")
	public Result<String> addStudentGrade(@RequestBody StudentGrade studentGrade) {
		studentGradeService.save(studentGrade);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param studentGrade
	 * @return
	 */
	@AutoLog(value = "学生成绩模块-编辑")
	@ApiOperation(value="学生成绩模块-编辑", notes="学生成绩模块-编辑")
	@RequestMapping(value = "/editStudentGrade", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editStudentGrade(@RequestBody StudentGrade studentGrade) {
		studentGradeService.updateById(studentGrade);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生成绩模块-通过id删除")
	@ApiOperation(value="学生成绩模块-通过id删除", notes="学生成绩模块-通过id删除")
	@DeleteMapping(value = "/deleteStudentGrade")
	public Result<String> deleteStudentGrade(@RequestParam(name="id",required=true) String id) {
		studentGradeService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生成绩模块-批量删除")
	@ApiOperation(value="学生成绩模块-批量删除", notes="学生成绩模块-批量删除")
	@DeleteMapping(value = "/deleteBatchStudentGrade")
	public Result<String> deleteBatchStudentGrade(@RequestParam(name="ids",required=true) String ids) {
	    this.studentGradeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportStudentGrade")
    public ModelAndView exportStudentGrade(HttpServletRequest request, StudentGrade studentGrade) {
		 // Step.1 组装查询条件
		 QueryWrapper<StudentGrade> queryWrapper = QueryGenerator.initQueryWrapper(studentGrade, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<StudentGrade> pageList = studentGradeService.list(queryWrapper);
		 List<StudentGrade> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "学生成绩模块");
		 mv.addObject(NormalExcelConstants.CLASS, StudentGrade.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("学生成绩模块报表", "导出人:" + sysUser.getRealname(), "学生成绩模块"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importStudentGrade/{mainId}")
    public Result<?> importStudentGrade(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
       // 获取上传文件对象
			 MultipartFile file = entity.getValue();
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<StudentGrade> list = ExcelImportUtil.importExcel(file.getInputStream(), StudentGrade.class, params);
				 for (StudentGrade temp : list) {
                    temp.setStudentId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 studentGradeService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-学生成绩模块-end----------------------------------------------*/

    /*--------------------------------子表处理-学生教育信息模块-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "学生教育信息模块-通过主表ID查询")
	@ApiOperation(value="学生教育信息模块-通过主表ID查询", notes="学生教育信息模块-通过主表ID查询")
	@GetMapping(value = "/listStudentEduBackgroundByMainId")
    public Result<IPage<StudentEduBackground>> listStudentEduBackgroundByMainId(StudentEduBackground studentEduBackground,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<StudentEduBackground> queryWrapper = QueryGenerator.initQueryWrapper(studentEduBackground, req.getParameterMap());
        Page<StudentEduBackground> page = new Page<StudentEduBackground>(pageNo, pageSize);
        IPage<StudentEduBackground> pageList = studentEduBackgroundService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param studentEduBackground
	 * @return
	 */
	@AutoLog(value = "学生教育信息模块-添加")
	@ApiOperation(value="学生教育信息模块-添加", notes="学生教育信息模块-添加")
	@PostMapping(value = "/addStudentEduBackground")
	public Result<String> addStudentEduBackground(@RequestBody StudentEduBackground studentEduBackground) {
		studentEduBackgroundService.save(studentEduBackground);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param studentEduBackground
	 * @return
	 */
	@AutoLog(value = "学生教育信息模块-编辑")
	@ApiOperation(value="学生教育信息模块-编辑", notes="学生教育信息模块-编辑")
	@RequestMapping(value = "/editStudentEduBackground", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editStudentEduBackground(@RequestBody StudentEduBackground studentEduBackground) {
		studentEduBackgroundService.updateById(studentEduBackground);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生教育信息模块-通过id删除")
	@ApiOperation(value="学生教育信息模块-通过id删除", notes="学生教育信息模块-通过id删除")
	@DeleteMapping(value = "/deleteStudentEduBackground")
	public Result<String> deleteStudentEduBackground(@RequestParam(name="id",required=true) String id) {
		studentEduBackgroundService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生教育信息模块-批量删除")
	@ApiOperation(value="学生教育信息模块-批量删除", notes="学生教育信息模块-批量删除")
	@DeleteMapping(value = "/deleteBatchStudentEduBackground")
	public Result<String> deleteBatchStudentEduBackground(@RequestParam(name="ids",required=true) String ids) {
	    this.studentEduBackgroundService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportStudentEduBackground")
    public ModelAndView exportStudentEduBackground(HttpServletRequest request, StudentEduBackground studentEduBackground) {
		 // Step.1 组装查询条件
		 QueryWrapper<StudentEduBackground> queryWrapper = QueryGenerator.initQueryWrapper(studentEduBackground, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<StudentEduBackground> pageList = studentEduBackgroundService.list(queryWrapper);
		 List<StudentEduBackground> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "学生教育信息模块");
		 mv.addObject(NormalExcelConstants.CLASS, StudentEduBackground.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("学生教育信息模块报表", "导出人:" + sysUser.getRealname(), "学生教育信息模块"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importStudentEduBackground/{mainId}")
    public Result<?> importStudentEduBackground(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
       // 获取上传文件对象
			 MultipartFile file = entity.getValue();
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<StudentEduBackground> list = ExcelImportUtil.importExcel(file.getInputStream(), StudentEduBackground.class, params);
				 for (StudentEduBackground temp : list) {
                    temp.setStudentId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 studentEduBackgroundService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-学生教育信息模块-end----------------------------------------------*/

    /*--------------------------------子表处理-学生工作经历模块-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "学生工作经历模块-通过主表ID查询")
	@ApiOperation(value="学生工作经历模块-通过主表ID查询", notes="学生工作经历模块-通过主表ID查询")
	@GetMapping(value = "/listStudentWorkExperienceByMainId")
    public Result<IPage<StudentWorkExperience>> listStudentWorkExperienceByMainId(StudentWorkExperience studentWorkExperience,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<StudentWorkExperience> queryWrapper = QueryGenerator.initQueryWrapper(studentWorkExperience, req.getParameterMap());
        Page<StudentWorkExperience> page = new Page<StudentWorkExperience>(pageNo, pageSize);
        IPage<StudentWorkExperience> pageList = studentWorkExperienceService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param studentWorkExperience
	 * @return
	 */
	@AutoLog(value = "学生工作经历模块-添加")
	@ApiOperation(value="学生工作经历模块-添加", notes="学生工作经历模块-添加")
	@PostMapping(value = "/addStudentWorkExperience")
	public Result<String> addStudentWorkExperience(@RequestBody StudentWorkExperience studentWorkExperience) {
		studentWorkExperienceService.save(studentWorkExperience);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param studentWorkExperience
	 * @return
	 */
	@AutoLog(value = "学生工作经历模块-编辑")
	@ApiOperation(value="学生工作经历模块-编辑", notes="学生工作经历模块-编辑")
	@RequestMapping(value = "/editStudentWorkExperience", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editStudentWorkExperience(@RequestBody StudentWorkExperience studentWorkExperience) {
		studentWorkExperienceService.updateById(studentWorkExperience);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生工作经历模块-通过id删除")
	@ApiOperation(value="学生工作经历模块-通过id删除", notes="学生工作经历模块-通过id删除")
	@DeleteMapping(value = "/deleteStudentWorkExperience")
	public Result<String> deleteStudentWorkExperience(@RequestParam(name="id",required=true) String id) {
		studentWorkExperienceService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生工作经历模块-批量删除")
	@ApiOperation(value="学生工作经历模块-批量删除", notes="学生工作经历模块-批量删除")
	@DeleteMapping(value = "/deleteBatchStudentWorkExperience")
	public Result<String> deleteBatchStudentWorkExperience(@RequestParam(name="ids",required=true) String ids) {
	    this.studentWorkExperienceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportStudentWorkExperience")
    public ModelAndView exportStudentWorkExperience(HttpServletRequest request, StudentWorkExperience studentWorkExperience) {
		 // Step.1 组装查询条件
		 QueryWrapper<StudentWorkExperience> queryWrapper = QueryGenerator.initQueryWrapper(studentWorkExperience, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<StudentWorkExperience> pageList = studentWorkExperienceService.list(queryWrapper);
		 List<StudentWorkExperience> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "学生工作经历模块");
		 mv.addObject(NormalExcelConstants.CLASS, StudentWorkExperience.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("学生工作经历模块报表", "导出人:" + sysUser.getRealname(), "学生工作经历模块"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importStudentWorkExperience/{mainId}")
    public Result<?> importStudentWorkExperience(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
       // 获取上传文件对象
			 MultipartFile file = entity.getValue();
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<StudentWorkExperience> list = ExcelImportUtil.importExcel(file.getInputStream(), StudentWorkExperience.class, params);
				 for (StudentWorkExperience temp : list) {
                    temp.setStudentId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 studentWorkExperienceService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-学生工作经历模块-end----------------------------------------------*/

    /*--------------------------------子表处理-学生材料模块-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "学生材料模块-通过主表ID查询")
	@ApiOperation(value="学生材料模块-通过主表ID查询", notes="学生材料模块-通过主表ID查询")
	@GetMapping(value = "/listStudentMaterialByMainId")
    public Result<IPage<StudentMaterial>> listStudentMaterialByMainId(StudentMaterial studentMaterial,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
		QueryWrapper<StudentMaterial> queryWrapper = new QueryWrapper<>();
		String name = studentMaterial.getName();
		if (StringUtils.isNotBlank(name)) {
			queryWrapper.like("name", name);
		}
        Page<StudentMaterial> page = new Page<StudentMaterial>(pageNo, pageSize);
        IPage<StudentMaterial> pageList = studentMaterialService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param studentMaterial
	 * @return
	 */
	@AutoLog(value = "学生材料模块-添加")
	@ApiOperation(value="学生材料模块-添加", notes="学生材料模块-添加")
	@PostMapping(value = "/addStudentMaterial")
	public Result<String> addStudentMaterial(@RequestBody StudentMaterial studentMaterial) {
		studentMaterialService.save(studentMaterial);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param studentMaterial
	 * @return
	 */
	@AutoLog(value = "学生材料模块-编辑")
	@ApiOperation(value="学生材料模块-编辑", notes="学生材料模块-编辑")
	@RequestMapping(value = "/editStudentMaterial", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editStudentMaterial(@RequestBody StudentMaterial studentMaterial) {
		studentMaterialService.updateById(studentMaterial);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生材料模块-通过id删除")
	@ApiOperation(value="学生材料模块-通过id删除", notes="学生材料模块-通过id删除")
	@DeleteMapping(value = "/deleteStudentMaterial")
	public Result<String> deleteStudentMaterial(@RequestParam(name="id",required=true) String id) {
		studentMaterialService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生材料模块-批量删除")
	@ApiOperation(value="学生材料模块-批量删除", notes="学生材料模块-批量删除")
	@DeleteMapping(value = "/deleteBatchStudentMaterial")
	public Result<String> deleteBatchStudentMaterial(@RequestParam(name="ids",required=true) String ids) {
	    this.studentMaterialService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportStudentMaterial")
    public ModelAndView exportStudentMaterial(HttpServletRequest request, StudentMaterial studentMaterial) {
		 // Step.1 组装查询条件
		 QueryWrapper<StudentMaterial> queryWrapper = QueryGenerator.initQueryWrapper(studentMaterial, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<StudentMaterial> pageList = studentMaterialService.list(queryWrapper);
		 List<StudentMaterial> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "学生材料模块");
		 mv.addObject(NormalExcelConstants.CLASS, StudentMaterial.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("学生材料模块报表", "导出人:" + sysUser.getRealname(), "学生材料模块"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importStudentMaterial/{mainId}")
    public Result<?> importStudentMaterial(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
       // 获取上传文件对象
			 MultipartFile file = entity.getValue();
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<StudentMaterial> list = ExcelImportUtil.importExcel(file.getInputStream(), StudentMaterial.class, params);
				 for (StudentMaterial temp : list) {
                    temp.setStudentId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 studentMaterialService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-学生材料模块-end----------------------------------------------*/




}
