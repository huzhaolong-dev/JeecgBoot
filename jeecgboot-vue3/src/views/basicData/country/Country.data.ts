import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '编码',
    align:"center",
    dataIndex: 'code'
   },
   {
    title: '名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '中文名称',
    align:"center",
    dataIndex: 'chName'
   },
   {
    title: '电话编码',
    align:"center",
    dataIndex: 'phoneCode'
   },
   {
    title: '图片Url地址',
    align:"center",
    dataIndex: 'picUrl',
    customRender:render.renderImage,
   },
   {
    title: '描述',
    align:"center",
    dataIndex: 'description'
   },
   {
    title: '备注',
    align:"center",
    dataIndex: 'remark'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '编码',
    field: 'code',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入编码!'},
          ];
     },
  },
  {
    label: '名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入名称!'},
          ];
     },
  },
  {
    label: '中文名称',
    field: 'chName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入中文名称!'},
          ];
     },
  },
  {
    label: '电话编码',
    field: 'phoneCode',
    component: 'Input',
  },
  {
    label: '图片Url地址',
    field: 'picUrl',
     component: 'JImageUpload',
     componentProps:{
      },
  },
  {
    label: '描述',
    field: 'description',
    component: 'Input',
  },
  {
    label: '备注',
    field: 'remark',
    component: 'Input',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];



/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}