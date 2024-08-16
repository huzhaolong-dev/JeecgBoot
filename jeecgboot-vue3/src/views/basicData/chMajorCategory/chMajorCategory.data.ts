import { FormSchema } from '/@/components/Form';

// 中国专业分类基础表单
export function useBasicFormSchema() {
  const basicFormSchema: FormSchema[] = [
    {
      field: 'name',
      label: '名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入名称',
      },
      rules: [{ required: true, message: '名称不能为空' }],
    },
    {
      field: 'pcode',
      label: '父编码',
      component: 'TreeSelect',
      componentProps: {
        treeData: [],
        placeholder: '无',
        dropdownStyle: { maxHeight: '200px', overflow: 'auto' },
      },
    },
    {
      field: 'code',
      label: '编码',
      component: 'Input',
      componentProps: {
        placeholder: '请输入编码',
      },
    },
    {
      field: 'chMajorCategoryType',
      label: '中国专业分类类型',
      component: 'RadioButtonGroup',
      componentProps: { options: [] },
    },
    {
      field: 'majorOrder',
      label: '排序',
      component: 'InputNumber',
      componentProps: {},
      rules: [
        { required: true, message: '排序不能为空' },
        { pattern: new RegExp('^[0-9]+'), message: '排序不能小于0' },
      ],
    },
    {
      field: 'durationOfYear',
      label: '修业年限',
      component: 'Input',
      componentProps: {
        placeholder: '请输入修业年限',
      },
    },
    {
      field: 'open_year',
      label: '开设年份',
      component: 'Input',
      componentProps: {
        placeholder: '请输入开设年份',
      },
    },
    {
      field: 'description',
      label: '学位授予门类',
      component: 'Input',
      componentProps: {
        placeholder: '请输入学位授予门类',
      },
    },
    {
      field: 'remark',
      label: '备注',
      component: 'InputTextArea',
      componentProps: {
        placeholder: '请输入备注',
      },
    },
  ];
  return { basicFormSchema };
}

// 中国专业分类类型选项
export const chMajorCategoryTypeOptions = {
  // 一级部门
  root: [{ value: '1', label: '一级专业分类' }],
  // 子级部门
  child: [
    { value: '2', label: '二级专业分类' },
    { value: '3', label: '专业' },
  ],
};
