package org.jeecg.modules.basicData.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.basicData.entity.ChMajorCategory;
import org.jeecg.modules.basicData.model.ChMajorCategoryTreeModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * <p>
 * 
 * @Author: Hu
 * @Since： 2019-01-22
 */
public interface IChMajorCategoryService extends IService<ChMajorCategory>{

    String CH_MAJOR_CATEGORY_KEY_CODE = "code";

    String HAS_CHILD = "1";

    String HAS_NO_CHILD = "0";

    /**
     * 获取我的部门下级所有部门
     * @param parentId 父id
     * @param ids 多个部门id
     * @param primaryKey 主键字段（id或者orgCode）
     * @return
     */
    List<ChMajorCategoryTreeModel> queryTreeListByPid(String parentId, String ids, String primaryKey);

    /**
     * 保存中国专业分类数据
     * @param chMajorCategory
     * @param username 用户名
     */
    void saveChMajorCategoryData(ChMajorCategory chMajorCategory, String username);

    /**
     * 删除中国专业分类数据
     * @param id
     */
    void deleteChMajorCategory(String id);


    /**
     * 根据中国专业分类数据id批量删除并删除其可能存在的子级中国专业分类
     * @param ids
     */
    void deleteBatchWithChildren(List<String> ids);

    /**
     * 根据关键字搜索相关的部中国专业分类数据
     * @param keyWord
     * @return
     */
    List<ChMajorCategoryTreeModel> searchByKeyWord(String keyWord);
}
