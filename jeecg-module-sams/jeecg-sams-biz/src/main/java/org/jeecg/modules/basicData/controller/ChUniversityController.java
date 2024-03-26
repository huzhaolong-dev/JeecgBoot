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
import org.jeecg.modules.basicData.entity.ChUniversity;
import org.jeecg.modules.basicData.service.IChUniversityService;

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
 * @Description: ch_university
 * @Author: jeecg-boot
 * @Date:   2024-03-26
 * @Version: V1.0
 */
@Api(tags="ch_university")
@RestController
@RequestMapping("/basicData/ch_university")
@Slf4j
public class ChUniversityController extends JeecgController<ChUniversity, IChUniversityService> {
	@Autowired
	private IChUniversityService chUniversityService;
	
	/**
	 * 分页列表查询
	 *
	 * @param chUniversity
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ch_university-分页列表查询")
	@ApiOperation(value="ch_university-分页列表查询", notes="ch_university-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ChUniversity>> queryPageList(ChUniversity chUniversity,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ChUniversity> queryWrapper = QueryGenerator.initQueryWrapper(chUniversity, req.getParameterMap());
		Page<ChUniversity> page = new Page<ChUniversity>(pageNo, pageSize);
		IPage<ChUniversity> pageList = chUniversityService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param chUniversity
	 * @return
	 */
	@AutoLog(value = "ch_university-添加")
	@ApiOperation(value="ch_university-添加", notes="ch_university-添加")
	@RequiresPermissions("basicData:ch_university:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ChUniversity chUniversity) {
		chUniversityService.save(chUniversity);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param chUniversity
	 * @return
	 */
	@AutoLog(value = "ch_university-编辑")
	@ApiOperation(value="ch_university-编辑", notes="ch_university-编辑")
	@RequiresPermissions("basicData:ch_university:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ChUniversity chUniversity) {
		chUniversityService.updateById(chUniversity);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ch_university-通过id删除")
	@ApiOperation(value="ch_university-通过id删除", notes="ch_university-通过id删除")
	@RequiresPermissions("basicData:ch_university:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		chUniversityService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ch_university-批量删除")
	@ApiOperation(value="ch_university-批量删除", notes="ch_university-批量删除")
	@RequiresPermissions("basicData:ch_university:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.chUniversityService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ch_university-通过id查询")
	@ApiOperation(value="ch_university-通过id查询", notes="ch_university-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ChUniversity> queryById(@RequestParam(name="id",required=true) String id) {
		ChUniversity chUniversity = chUniversityService.getById(id);
		if(chUniversity==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(chUniversity);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param chUniversity
    */
    @RequiresPermissions("basicData:ch_university:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ChUniversity chUniversity) {
        return super.exportXls(request, chUniversity, ChUniversity.class, "ch_university");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("basicData:ch_university:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ChUniversity.class);
    }

}
