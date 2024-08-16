import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '编码',
    align: "center",
    dataIndex: 'code'
  },
  {
    title: '名称',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: '中文名称',
    align: "center",
    dataIndex: 'chName'
  },
  {
    title: '国家编码',
    align: "center",
    dataIndex: 'countryCode_dictText'
  },
  {
    title: '图片Url地址',
    align: "center",
    dataIndex: 'picUrl',
    customRender: render.renderImage,
  },
  {
    title: '网站Url地址',
    align: "center",
    dataIndex: 'webUrl'
  },
  {
    title: 'QS世界排名',
    align: "center",
    sorter: true,
    dataIndex: 'qsWorldRank'
  },
  {
    title: 'QS国家排名',
    align: "center",
    sorter: true,
    dataIndex: 'qsCountryRank'
  },
  {
    title: '描述',
    align: "center",
    dataIndex: 'description'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'remark'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "名称",
    field: 'name',
    component: 'Input',
    //colProps: {span: 6},
  },
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
    label: '国家编码',
    field: 'countryCode',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "country,name,code"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入国家编码!'},
             ];
    },
  },
  {
    label: '图片Url地址',
    field: 'picUrl',
    component: 'JImageUpload',
    componentProps:{
    },
  },
  {
    label: '网站Url地址',
    field: 'webUrl',
    component: 'Input',
  },
  {
    label: 'QS世界排名',
    field: 'qsWorldRank',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入QS世界排名!'},
             ];
    },
  },
  {
    label: 'QS国家排名',
    field: 'qsCountryRank',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入QS国家排名!'},
             ];
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
    show: false,
  },
];

// 高级查询数据
export const superQuerySchema = {
  code: {title: '编码',order: 0,view: 'text', type: 'string',},
  name: {title: '名称',order: 1,view: 'text', type: 'string',},
  chName: {title: '中文名称',order: 2,view: 'text', type: 'string',},
  countryCode: {title: '国家编码',order: 3,view: 'list', type: 'string',dictTable: "country", dictCode: 'code', dictText: 'name',},
  picUrl: {title: '图片Url地址',order: 4,view: 'image', type: 'string',},
  webUrl: {title: '网站Url地址',order: 5,view: 'text', type: 'string',},
  qsWorldRank: {title: 'QS世界排名',order: 6,view: 'number', type: 'number',},
  qsCountryRank: {title: 'QS国家排名',order: 7,view: 'number', type: 'number',},
  description: {title: '描述',order: 8,view: 'text', type: 'string',},
  remark: {title: '备注',order: 9,view: 'text', type: 'string',},
};
