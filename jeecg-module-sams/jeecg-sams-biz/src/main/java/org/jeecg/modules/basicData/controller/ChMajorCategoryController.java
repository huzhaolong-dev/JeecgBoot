package org.jeecg.modules.basicData.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.ImportExcelUtil;
import org.jeecg.modules.basicData.entity.ChMajorCategory;
import org.jeecg.modules.basicData.model.ChMajorCategoryTreeModel;
import org.jeecg.modules.basicData.service.IChMajorCategoryService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.jeecg.modules.basicData.service.IChMajorCategoryService.HAS_CHILD;
import static org.jeecg.modules.basicData.service.IChMajorCategoryService.HAS_NO_CHILD;

/**
 * <p>
 * 中国专业分类表 前端控制器
 * <p>
 * 
 * @Author: Hu
 * @Since： 2023-12-30
 */
@RestController
@RequestMapping("/basicData/chMajorCategory")
@Slf4j
@Api(tags="中国专业分类")
public class ChMajorCategoryController {

	@Autowired
	private IChMajorCategoryService chMajorCategoryService;
	@Autowired
	public RedisTemplate<String, Object> redisTemplate;

	/**
	 * 异步查询中国专业分类list
	 * @param parentId 父节点 异步加载时传递
	 * @param ids 前端回显是传递
	 * @param primaryKey 主键字段（id或者code）
	 * @return
	 */
	@ApiOperation("异步查询中国专业分类list接口")
	@RequestMapping(value = "/queryChMajorCategoryTreeSync", method = RequestMethod.GET)
	public Result<List<ChMajorCategoryTreeModel>> queryChMajorCategoryTreeSync(@ApiParam("父Id") @RequestParam(name = "pid", required = false) String parentId, @ApiParam("id列表") @RequestParam(name = "ids", required = false) String ids,
																			   @ApiParam("主键key") @RequestParam(name = "primaryKey", required = false) String primaryKey) {
		Result<List<ChMajorCategoryTreeModel>> result = new Result<>();
		try {
			List<ChMajorCategoryTreeModel> list = chMajorCategoryService.queryTreeListByPid(parentId,ids, primaryKey);
			result.setResult(list);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 添加新数据 添加用户新建的中国专业分类对象数据,并保存到数据库
	 *
	 * @param chMajorCategory
	 * @return
	 */
	@RequiresPermissions("basicData:ch_major_category:add")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result<ChMajorCategory> add(@RequestBody ChMajorCategory chMajorCategory, HttpServletRequest request) {
		Result<ChMajorCategory> result = new Result<ChMajorCategory>();
		String username = JwtUtil.getUserNameByToken(request);
		try {
			chMajorCategory.setCreateBy(username);
			chMajorCategoryService.saveChMajorCategoryData(chMajorCategory, username);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
	 * 编辑数据 编辑中国专业分类的部分数据,并保存到数据库
	 * @param chMajorCategory
	 * @param request
	 * @return
	 */
	@RequiresPermissions("basicData:ch_major_category:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<ChMajorCategory> edit(@RequestBody ChMajorCategory chMajorCategory, HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		chMajorCategory.setUpdateBy(username);
		Result<ChMajorCategory> result = new Result<ChMajorCategory>();
		ChMajorCategory chMajorCategoryEntity = chMajorCategoryService.getById(chMajorCategory.getId());
		if (chMajorCategoryEntity == null) {
			result.error500("未找到对应实体");
		} else {
			boolean ok = chMajorCategoryService.updateById(chMajorCategory);
			// TODO 返回false说明什么？
			if (ok) {
				result.success("修改成功!");
			}
		}
		return result;
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@RequiresPermissions("basicData:ch_major_category:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<ChMajorCategory> delete(@RequestParam(name="id",required=true) String id) {

		Result<ChMajorCategory> result = new Result<ChMajorCategory>();
		ChMajorCategory chMajorCategory = chMajorCategoryService.getById(id);
		if(chMajorCategory == null) {
			result.error500("未找到对应实体");
		}else {
			if (HAS_CHILD.equals(chMajorCategory.getHasChild())) {
				result.error500("此节点有子节点，请先删除其所有子节点后再删除此节点");
			} else if (HAS_NO_CHILD.equals(chMajorCategory.getHasChild())) {
				chMajorCategoryService.deleteChMajorCategory(id);
				result.success("删除成功!");
			}
		}
		return result;
	}

	/**
	 * 批量删除 根据前端请求的多个ID,对数据库执行删除相关中国专业分类数据的操作
	 *
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("basicData:ch_major_category:deleteBatch")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<ChMajorCategory> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {

		Result<ChMajorCategory> result = new Result<ChMajorCategory>();
		if (ids == null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		} else {
			chMajorCategoryService.deleteBatchWithChildren(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}

	/**
	 * <p>
	 * 中国专业分类搜索功能方法,根据关键字模糊搜索相关中国专业分类
	 * </p>
	 *
	 * @param keyWord
	 * @return
	 */
	@RequestMapping(value = "/searchBy", method = RequestMethod.GET)
	public Result<List<ChMajorCategoryTreeModel>> searchBy(@RequestParam(name = "keyWord", required = true) String keyWord) {
		Result<List<ChMajorCategoryTreeModel>> result = new Result<List<ChMajorCategoryTreeModel>>();
		List<ChMajorCategoryTreeModel> treeList = chMajorCategoryService.searchByKeyWord(keyWord);
		if (treeList == null || treeList.size() == 0) {
			result.setSuccess(false);
			result.setMessage("未查询匹配数据！");
			return result;
		}
		result.setResult(treeList);
		return result;
	}


	/**
	 * 导出excel
	 *
	 * @param request
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(ChMajorCategory chMajorCategory, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<ChMajorCategory> queryWrapper = QueryGenerator.initQueryWrapper(chMajorCategory, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<ChMajorCategory> pageList = chMajorCategoryService.list(queryWrapper);
		//按字典排序
		Collections.sort(pageList, new Comparator<ChMajorCategory>() {
			@Override
			public int compare(ChMajorCategory arg0, ChMajorCategory arg1) {
				return arg0.getMajorOrder().compareTo(arg1.getMajorOrder());
			}
		});
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME, "中国专业分类列表");
		mv.addObject(NormalExcelConstants.CLASS, ChMajorCategory.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("中国专业分类列表数据", "导出人:"+user.getRealname(), "导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		return mv;
	}

	/**
	 * 通过excel导入数据
	 * 导入方案2: 你也可以改造下程序,编码直接导入,先不设置父ID;全部导入后,写一个sql,补下父ID;
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("basicData:ch_major_category:importExcel")
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<String> errorMessageList = new ArrayList<>();
		List<ChMajorCategory> chMajorCategoryList = null;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				chMajorCategoryList = ExcelImportUtil.importExcel(file.getInputStream(), ChMajorCategory.class, params);
				//按长度排序
				Collections.sort(chMajorCategoryList, new Comparator<ChMajorCategory>() {
					@Override
					public int compare(ChMajorCategory arg0, ChMajorCategory arg1) {
						return arg0.getMajorOrder() - arg1.getMajorOrder();
					}
				});

				int num = 0;
				for (ChMajorCategory chMajorCategory : chMajorCategoryList) {
					ImportExcelUtil.importDateSaveOne(chMajorCategory, IChMajorCategoryService.class, errorMessageList, num, CommonConstant.SQL_INDEX_UNIQ_DEPART_ORG_CODE);
					num++;
				}
				return ImportExcelUtil.imporReturnRes(errorMessageList.size(), chMajorCategoryList.size() - errorMessageList.size(), errorMessageList);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				return Result.error("文件导入失败:"+e.getMessage());
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
}
