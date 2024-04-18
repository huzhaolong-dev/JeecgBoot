package org.jeecg.modules.basicData.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.basicData.entity.Program;
import org.jeecg.modules.basicData.service.IProgramService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: program
 * @Author: jeecg-boot
 * @Date:   2024-04-18
 * @Version: V1.0
 */
@Api(tags="program")
@RestController
@RequestMapping("/basicData/program")
@Slf4j
public class ProgramController extends JeecgController<Program, IProgramService> {
	@Autowired
	private IProgramService programService;
	
	/**
	 * 分页列表查询
	 *
	 * @param program
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "program-分页列表查询")
	@ApiOperation(value="program-分页列表查询", notes="program-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Program>> queryPageList(Program program,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Program> queryWrapper = QueryGenerator.initQueryWrapper(program, req.getParameterMap());
		Page<Program> page = new Page<Program>(pageNo, pageSize);
		IPage<Program> pageList = programService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param program
	 * @return
	 */
	@AutoLog(value = "program-添加")
	@ApiOperation(value="program-添加", notes="program-添加")
	@RequiresPermissions("basicData:program:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Program program) {
		programService.save(program);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param program
	 * @return
	 */
	@AutoLog(value = "program-编辑")
	@ApiOperation(value="program-编辑", notes="program-编辑")
	@RequiresPermissions("basicData:program:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Program program) {
		programService.updateById(program);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "program-通过id删除")
	@ApiOperation(value="program-通过id删除", notes="program-通过id删除")
	@RequiresPermissions("basicData:program:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		programService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "program-批量删除")
	@ApiOperation(value="program-批量删除", notes="program-批量删除")
	@RequiresPermissions("basicData:program:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.programService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "program-通过id查询")
	@ApiOperation(value="program-通过id查询", notes="program-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Program> queryById(@RequestParam(name="id",required=true) String id) {
		Program program = programService.getById(id);
		if(program==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(program);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param program
    */
    @RequiresPermissions("basicData:program:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Program program) {
        return super.exportXls(request, program, Program.class, "program");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("basicData:program:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Program.class);
    }

}
