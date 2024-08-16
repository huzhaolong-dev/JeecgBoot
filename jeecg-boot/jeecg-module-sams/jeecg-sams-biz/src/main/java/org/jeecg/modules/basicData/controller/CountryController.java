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
import org.jeecg.modules.basicData.entity.Country;
import org.jeecg.modules.basicData.service.ICountryService;

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
 * @Description: country
 * @Author: jeecg-boot
 * @Date:   2024-03-26
 * @Version: V1.0
 */
@Api(tags="country")
@RestController
@RequestMapping("/basicData/country")
@Slf4j
public class CountryController extends JeecgController<Country, ICountryService> {
	@Autowired
	private ICountryService countryService;
	
	/**
	 * 分页列表查询
	 *
	 * @param country
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "country-分页列表查询")
	@ApiOperation(value="country-分页列表查询", notes="country-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Country>> queryPageList(Country country,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Country> queryWrapper = QueryGenerator.initQueryWrapper(country, req.getParameterMap());
		Page<Country> page = new Page<Country>(pageNo, pageSize);
		IPage<Country> pageList = countryService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param country
	 * @return
	 */
	@AutoLog(value = "country-添加")
	@ApiOperation(value="country-添加", notes="country-添加")
	@RequiresPermissions("basicData:country:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Country country) {
		countryService.save(country);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param country
	 * @return
	 */
	@AutoLog(value = "country-编辑")
	@ApiOperation(value="country-编辑", notes="country-编辑")
	@RequiresPermissions("basicData:country:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Country country) {
		countryService.updateById(country);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "country-通过id删除")
	@ApiOperation(value="country-通过id删除", notes="country-通过id删除")
	@RequiresPermissions("basicData:country:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		countryService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "country-批量删除")
	@ApiOperation(value="country-批量删除", notes="country-批量删除")
	@RequiresPermissions("basicData:country:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.countryService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "country-通过id查询")
	@ApiOperation(value="country-通过id查询", notes="country-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Country> queryById(@RequestParam(name="id",required=true) String id) {
		Country country = countryService.getById(id);
		if(country==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(country);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param country
    */
    @RequiresPermissions("basicData:country:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Country country) {
        return super.exportXls(request, country, Country.class, "country");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("basicData:country:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Country.class);
    }

}
