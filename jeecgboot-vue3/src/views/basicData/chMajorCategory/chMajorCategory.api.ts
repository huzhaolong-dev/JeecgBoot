import { defHttp } from '/@/utils/http/axios';
import { useMessage } from '/@/hooks/web/useMessage';

const { createConfirm } = useMessage();

export enum Api {
  searchBy = '/basicData/chMajorCategory/searchBy',
  queryDepartTreeSync = '/basicData/chMajorCategory/queryChMajorCategoryTreeSync',
  save = '/basicData/chMajorCategory/add',
  edit = '/basicData/chMajorCategory/edit',
  delete = '/basicData/chMajorCategory/delete',
  deleteBatch = '/basicData/chMajorCategory/deleteBatch',
  exportXlsUrl = '/basicData/chMajorCategory/exportXls',
  importExcelUrl = '/basicData/chMajorCategory/importExcel',
}

/**
 * 根据关键字搜索中国专业分类
 */
export const searchByKeywords = (params) => defHttp.get({ url: Api.searchBy, params });

/**
 * 获取中国专业分类树列表
 */
export const queryChMajorCategoryTreeSync = (params?) => defHttp.get({ url: Api.queryDepartTreeSync, params });

/**
 * 保存或者更新中国专业分类
 */
export const saveOrUpdateChMajorCategory = (params, isUpdate) => {
  if (isUpdate) {
    return defHttp.put({ url: Api.edit, params });
  } else {
    return defHttp.post({ url: Api.save, params });
  }
};

/**
 * 批量删除部门角色
 */
export const deleteBatchChMajorCategory = (params, confirm = false) => {
  return new Promise((resolve, reject) => {
    const doDelete = () => {
      resolve(defHttp.delete({ url: Api.deleteBatch, params }, { joinParamsToUrl: true }));
    };
    if (confirm) {
      createConfirm({
        iconType: 'warning',
        title: '删除',
        content: '确定要删除吗？',
        onOk: () => doDelete(),
        onCancel: () => reject(),
      });
    } else {
      doDelete();
    }
  });
};
