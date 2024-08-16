package org.jeecg.modules.basicData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.basicData.entity.ChMajorCategory;
import org.jeecg.modules.basicData.mapper.ChMajorCategoryMapper;
import org.jeecg.modules.basicData.model.ChMajorCategoryTreeModel;
import org.jeecg.modules.basicData.service.IChMajorCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 中国专业分类表 服务实现类
 * <p>
 * 
 * @Author Hu
 * @Since 2019-01-22
 */
@Service
public class ChMajorCategoryServiceImpl extends ServiceImpl<ChMajorCategoryMapper, ChMajorCategory> implements IChMajorCategoryService {

	@Autowired
	private ChMajorCategoryMapper chMajorCategoryMapper;

	/**
	 * 根据parentId查询部门树
	 * @param parentId
	 * @param ids 前端回显传递
	 * @param primaryKey 主键字段（id或者code）
	 * @return
	 */
	@Override
	public List<ChMajorCategoryTreeModel> queryTreeListByPid(String parentId, String ids, String primaryKey) {
		Consumer<LambdaQueryWrapper<ChMajorCategory>> square = i -> {
			if (oConvertUtils.isNotEmpty(ids)) {
				if (IChMajorCategoryService.CH_MAJOR_CATEGORY_KEY_CODE.equals(primaryKey)) {
					i.in(ChMajorCategory::getCode, ids.split(SymbolConstant.COMMA));
				} else {
					i.in(ChMajorCategory::getId, ids.split(SymbolConstant.COMMA));
				}
			} else {
				if(oConvertUtils.isEmpty(parentId)){
					i.and(q->q.isNull(true,ChMajorCategory::getPcode).or().eq(true,ChMajorCategory::getPcode,""));
				}else{
					i.eq(true,ChMajorCategory::getPcode,parentId);
				}
			}
		};
		LambdaQueryWrapper<ChMajorCategory> lqw=new LambdaQueryWrapper<>();
		lqw.func(square);
		lqw.orderByAsc(ChMajorCategory::getMajorOrder);
		List<ChMajorCategory> list = list(lqw);
		List<ChMajorCategoryTreeModel> records = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ChMajorCategory chMajorCategory = list.get(i);
			ChMajorCategoryTreeModel treeModel = new ChMajorCategoryTreeModel(chMajorCategory);
			//TODO 异步树加载key拼接__+时间戳,以便于每次展开节点会刷新数据
			//treeModel.setKey(treeModel.getKey()+"__"+System.currentTimeMillis());
			records.add(treeModel);
		}
		return records;
	}

	/**
	 * saveChMajorCategoryData 对应 add 保存用户在页面添加的新的部中国专业分类对象数据
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveChMajorCategoryData(ChMajorCategory chMajorCategory, String username) {
		if (chMajorCategory != null && username != null) {
			if (oConvertUtils.isEmpty(chMajorCategory.getPcode())) {
				chMajorCategory.setPcode("");
			}else{
				//将父部门的设成有子节点
				chMajorCategoryMapper.setHasChild(chMajorCategory.getPcode(), HAS_CHILD);
			}
			chMajorCategory.setId(IdWorker.getIdStr(chMajorCategory));
			chMajorCategory.setHasChild(HAS_NO_CHILD);
			this.save(chMajorCategory);
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteChMajorCategory(String id) {
		//删除中国专业分类设置父级的是否有子节点
		this.setHasChild(id);
		chMajorCategoryMapper.deleteById(id);
	}

	private void setHasChild(String id) {
		ChMajorCategory chMajorCategory = this.getById(id);
		String parentCode = chMajorCategory.getPcode();
		if(oConvertUtils.isNotEmpty(parentCode)){
			Long count = this.count(new QueryWrapper<ChMajorCategory>().lambda().eq(ChMajorCategory::getPcode, parentCode));
			if(count == 1){
				//若父节点无其他子节点，设置该父节点无子节点
				chMajorCategoryMapper.setHasChild(parentCode, HAS_NO_CHILD);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatchWithChildren(List<String> ids) {
		List<String> idList = new ArrayList<String>();
		for(String id: ids) {
			idList.add(id);
			this.checkChildrenExists(id, idList);
			//删除中国专业分类设置父级的是否有子节点
			this.setHasChild(id);
		}
		this.removeByIds(idList);
	}

	private void checkChildrenExists(String id, List<String> idList) {
		ChMajorCategory chMajorCategory = this.getById(id);
		LambdaQueryWrapper<ChMajorCategory> query = new LambdaQueryWrapper<ChMajorCategory>();
		query.eq(ChMajorCategory::getPcode,chMajorCategory.getCode());
		List<ChMajorCategory> childChMajorCategoryList = this.list(query);
		if(childChMajorCategoryList != null && childChMajorCategoryList.size() > 0) {
			for(ChMajorCategory childChMajorCategory : childChMajorCategoryList) {
				idList.add(childChMajorCategory.getId());
				this.checkChildrenExists(childChMajorCategory.getId(), idList);
			}
		}
	}

	/**
	 * <p>
	 * 根据关键字搜索相关的中国专业分类数据
	 * </p>
	 */
	@Override
	public List<ChMajorCategoryTreeModel> searchByKeyWord(String keyWord) {
		LambdaQueryWrapper<ChMajorCategory> query = new LambdaQueryWrapper<ChMajorCategory>();
		List<ChMajorCategoryTreeModel> newList = new ArrayList<>();
		query.like(ChMajorCategory::getName, keyWord);
		ChMajorCategoryTreeModel model = new ChMajorCategoryTreeModel();
		List<ChMajorCategory> chMajorCategoryList = this.list(query);
		if(chMajorCategoryList.size() > 0) {
			for(ChMajorCategory chMajorCategory : chMajorCategoryList) {
				model = new ChMajorCategoryTreeModel(chMajorCategory);
				model.setChildren(null);
				newList.add(model);
			}
			return newList;
		}
		return null;
	}
}
