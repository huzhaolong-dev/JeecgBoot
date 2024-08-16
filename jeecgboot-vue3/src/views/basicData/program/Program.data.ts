import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
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
    title: '要求的雅思成绩',
    align: "center",
    dataIndex: 'reqIelts'
  },
  {
    title: '要求的托福成绩',
    align: "center",
    dataIndex: 'reqToefl'
  },
  {
    title: '要求的GRE成绩',
    align: "center",
    dataIndex: 'reqGre'
  },
  {
    title: '要求的GMAT成绩',
    align: "center",
    dataIndex: 'reqGmat'
  },
  {
    title: '所属大学',
    align: "center",
    dataIndex: 'universityCode_dictText'
  },
  {
    title: '开放状态',
    align: "center",
    dataIndex: 'openStatus_dictText'
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
  {
    label: "大学编码",
    field: 'universityCode',
    component: 'JSearchSelect',
    componentProps:{
      dict: "university,ch_name,code"
    },
    //colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
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
    label: '要求的雅思成绩',
    field: 'reqIelts',
    component: 'InputNumber',
  },
  {
    label: '要求的托福成绩',
    field: 'reqToefl',
    component: 'InputNumber',
  },
  {
    label: '要求的GRE成绩',
    field: 'reqGre',
    component: 'InputNumber',
  },
  {
    label: '要求的GMAT成绩',
    field: 'reqGmat',
    component: 'InputNumber',
  },
  {
    label: '要求的专业分类类型[1-全部符合,2-部分符合,3-部分不符合]',
    field: 'reqMajorCategoryType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "req_major_category_type"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入要求的专业分类类型[1-全部符合,2-部分符合,3-部分不符合]!'},
             ];
    },
  },
  {
    label: '要求的专业分类编码集合，如[0101,0102,0202]',
    field: 'reqMajorCategoryCodes',
    component: 'JSelectMultiple',
    componentProps:{
        dictCode:"ch_major_category where ch_major_category_type = '2',name,code"
     },
  },
  {
    label: '所属大学',
    field: 'universityCode',
    component: 'JSearchSelect',
    componentProps:{
      dict: "university,ch_name,code"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入所属大学!'},
             ];
    },
  },
  {
    label: '开放状态',
    field: 'openStatus',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode: "open_status"
    },
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入开放状态!'},
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
  name: {title: '名称',order: 0,view: 'text', type: 'string',},
  chName: {title: '中文名称',order: 1,view: 'text', type: 'string',},
  reqIelts: {title: '要求的雅思成绩',order: 2,view: 'number', type: 'number',},
  reqToefl: {title: '要求的托福成绩',order: 3,view: 'number', type: 'number',},
  reqGre: {title: '要求的GRE成绩',order: 4,view: 'number', type: 'number',},
  reqGmat: {title: '要求的GMAT成绩',order: 5,view: 'number', type: 'number',},
  universityCode: {title: '大学编码',order: 8,view: 'sel_search', type: 'string',dictTable: "university", dictCode: 'code', dictText: 'ch_name',},
  openStatus: {title: '开放状态',order: 9,view: 'list', type: 'string',dictCode: 'open_status',},
};
