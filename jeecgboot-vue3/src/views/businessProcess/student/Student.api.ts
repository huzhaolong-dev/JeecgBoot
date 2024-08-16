import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/businessProcess/student/list',
  save= '/businessProcess/student/add',
  edit= '/businessProcess/student/edit',
  deleteOne = '/businessProcess/student/delete',
  deleteBatch = '/businessProcess/student/deleteBatch',
  importExcel = '/businessProcess/student/importExcel',
  exportXls = '/businessProcess/student/exportXls',
  studentGradeList = '/businessProcess/student/listStudentGradeByMainId',
  studentGradeSave= '/businessProcess/student/addStudentGrade',
  studentGradeEdit= '/businessProcess/student/editStudentGrade',
  studentGradeDelete = '/businessProcess/student/deleteStudentGrade',
  studentGradeDeleteBatch = '/businessProcess/student/deleteBatchStudentGrade',
  studentEduBackgroundList = '/businessProcess/student/listStudentEduBackgroundByMainId',
  studentEduBackgroundSave= '/businessProcess/student/addStudentEduBackground',
  studentEduBackgroundEdit= '/businessProcess/student/editStudentEduBackground',
  studentEduBackgroundDelete = '/businessProcess/student/deleteStudentEduBackground',
  studentEduBackgroundDeleteBatch = '/businessProcess/student/deleteBatchStudentEduBackground',
  studentWorkExperienceList = '/businessProcess/student/listStudentWorkExperienceByMainId',
  studentWorkExperienceSave= '/businessProcess/student/addStudentWorkExperience',
  studentWorkExperienceEdit= '/businessProcess/student/editStudentWorkExperience',
  studentWorkExperienceDelete = '/businessProcess/student/deleteStudentWorkExperience',
  studentWorkExperienceDeleteBatch = '/businessProcess/student/deleteBatchStudentWorkExperience',
  studentMaterialList = '/businessProcess/student/listStudentMaterialByMainId',
  studentMaterialSave= '/businessProcess/student/addStudentMaterial',
  studentMaterialEdit= '/businessProcess/student/editStudentMaterial',
  studentMaterialDelete = '/businessProcess/student/deleteStudentMaterial',
  studentMaterialDeleteBatch = '/businessProcess/student/deleteBatchStudentMaterial',
}
/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;

/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;

/**
 * 列表接口
 * @param params
 */
export const list = (params) =>
  defHttp.get({ url: Api.list, params });

/**
 * 删除单个
 */
export const deleteOne = (params,handleSuccess) => {
  return defHttp.delete({ url: Api.deleteOne, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
}

/**
 * 批量删除
 * @param params
 */
export const batchDelete = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({ url: Api.deleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({ url: url, params },{ isTransformResponse: false });
}

/**
 * 列表接口
 * @param params
 */
export const studentGradeList = (params) => {
  if(params['studentId']){
    return defHttp.get({ url: Api.studentGradeList, params });
  }
  return Promise.resolve({});
}

/**
 * 删除单个
 */
export const studentGradeDelete = (params,handleSuccess) => {
  return defHttp.delete({ url: Api.studentGradeDelete, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
}

/**
 * 批量删除
 * @param params
 */
export const studentGradeDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({ url: Api.studentGradeDeleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 保存或者更新
 * @param params
 */
export const  studentGradeSaveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.studentGradeEdit : Api.studentGradeSave;
  return defHttp.post({ url: url, params },{ isTransformResponse: false });
}

/**
 * 导入
 */
export const studentGradeImportUrl = '/businessProcess/student/importStudentGrade'

/**
 * 导出
 */
export const studentGradeExportXlsUrl = '/businessProcess/student/exportStudentGrade'

/**
 * 列表接口
 * @param params
 */
export const studentEduBackgroundList = (params) => {
  if(params['studentId']){
    return defHttp.get({ url: Api.studentEduBackgroundList, params });
  }
  return Promise.resolve({});
}

/**
 * 删除单个
 */
export const studentEduBackgroundDelete = (params,handleSuccess) => {
  return defHttp.delete({ url: Api.studentEduBackgroundDelete, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
}

/**
 * 批量删除
 * @param params
 */
export const studentEduBackgroundDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({ url: Api.studentEduBackgroundDeleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 保存或者更新
 * @param params
 */
export const  studentEduBackgroundSaveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.studentEduBackgroundEdit : Api.studentEduBackgroundSave;
  return defHttp.post({ url: url, params },{ isTransformResponse: false });
}

/**
 * 导入
 */
export const studentEduBackgroundImportUrl = '/businessProcess/student/importStudentEduBackground'

/**
 * 导出
 */
export const studentEduBackgroundExportXlsUrl = '/businessProcess/student/exportStudentEduBackground'

/**
 * 列表接口
 * @param params
 */
export const studentWorkExperienceList = (params) => {
  if(params['studentId']){
    return defHttp.get({ url: Api.studentWorkExperienceList, params });
  }
  return Promise.resolve({});
}

/**
 * 删除单个
 */
export const studentWorkExperienceDelete = (params,handleSuccess) => {
  return defHttp.delete({ url: Api.studentWorkExperienceDelete, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
}

/**
 * 批量删除
 * @param params
 */
export const studentWorkExperienceDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({ url: Api.studentWorkExperienceDeleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 保存或者更新
 * @param params
 */
export const  studentWorkExperienceSaveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.studentWorkExperienceEdit : Api.studentWorkExperienceSave;
  return defHttp.post({ url: url, params },{ isTransformResponse: false });
}

/**
 * 导入
 */
export const studentWorkExperienceImportUrl = '/businessProcess/student/importStudentWorkExperience'

/**
 * 导出
 */
export const studentWorkExperienceExportXlsUrl = '/businessProcess/student/exportStudentWorkExperience'

/**
 * 列表接口
 * @param params
 */
export const studentMaterialList = (params) => {
  if(params['studentId']){
    return defHttp.get({ url: Api.studentMaterialList, params });
  }
  return Promise.resolve({});
}

/**
 * 删除单个
 */
export const studentMaterialDelete = (params,handleSuccess) => {
  return defHttp.delete({ url: Api.studentMaterialDelete, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
}

/**
 * 批量删除
 * @param params
 */
export const studentMaterialDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({ url: Api.studentMaterialDeleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    }
  });
}

/**
 * 保存或者更新
 * @param params
 */
export const  studentMaterialSaveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.studentMaterialEdit : Api.studentMaterialSave;
  return defHttp.post({ url: url, params },{ isTransformResponse: false });
}

/**
 * 导入
 */
export const studentMaterialImportUrl = '/businessProcess/student/importStudentMaterial'

/**
 * 导出
 */
export const studentMaterialExportXlsUrl = '/businessProcess/student/exportStudentMaterial'
