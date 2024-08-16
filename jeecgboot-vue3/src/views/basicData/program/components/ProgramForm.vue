<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="名称" v-bind="validateInfos.name">
            <a-input v-model:value="formData.name" placeholder="请输入名称" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="中文名称" v-bind="validateInfos.chName">
            <a-input v-model:value="formData.chName" placeholder="请输入中文名称" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="要求的雅思成绩" v-bind="validateInfos.reqIelts">
	          <a-input-number v-model:value="formData.reqIelts" placeholder="请输入要求的雅思成绩" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="要求的托福成绩" v-bind="validateInfos.reqToefl">
	          <a-input-number v-model:value="formData.reqToefl" placeholder="请输入要求的托福成绩" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="要求的GRE成绩" v-bind="validateInfos.reqGre">
	          <a-input-number v-model:value="formData.reqGre" placeholder="请输入要求的GRE成绩" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="要求的GMAT成绩" v-bind="validateInfos.reqGmat">
	          <a-input-number v-model:value="formData.reqGmat" placeholder="请输入要求的GMAT成绩" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="专业分类条件" v-bind="validateInfos.reqMajorCategoryType">
	          <j-dict-select-tag v-model:value="formData.reqMajorCategoryType" dictCode="req_major_category_type" placeholder="请选择专业分类条件" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="要求的专业分类" v-bind="validateInfos.reqMajorCategoryCodes">
	          <j-select-multiple type="list_multi" v-model:value="formData.reqMajorCategoryCodes" dictCode="ch_major_category where ch_major_category_type = '2',name,code" placeholder="请选择要求的专业分类编码集合，如[0101,0102,0202]" :disabled="disabled" :triggerChange="false"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="所属大学" v-bind="validateInfos.universityCode">
	          <j-search-select v-model:value="formData.universityCode" dict="university,ch_name,code" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="开放状态" v-bind="validateInfos.openStatus">
	          <j-dict-select-tag v-model:value="formData.openStatus" dictCode="open_status" placeholder="请选择开放状态" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="描述" v-bind="validateInfos.description">
            <a-input v-model:value="formData.description" placeholder="请输入描述" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="备注" v-bind="validateInfos.remark">
            <a-input v-model:value="formData.remark" placeholder="请输入备注" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../Program.api';
  import { Form } from 'ant-design-vue';
  
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    name: '',   
    chName: '',   
    reqIelts: undefined,
    reqToefl: undefined,
    reqGre: undefined,
    reqGmat: undefined,
    reqMajorCategoryType: '',   
    reqMajorCategoryCodes: '',   
    universityCode: '',   
    openStatus: '1',   
    description: '',   
    remark: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    name: [{ required: true, message: '请输入名称!'},],
    chName: [{ required: true, message: '请输入中文名称!'},],
    reqMajorCategoryType: [{ required: true, message: '请输入专业分类条件!'},],
    universityCode: [{ required: true, message: '请输入所属大学!'},],
    openStatus: [{ required: true, message: '请输入开放状态!'},],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });

  // 表单禁用
  const disabled = computed(()=>{
    if(props.formBpm === true){
      if(props.formData.disabled === false){
        return false;
      }else{
        return true;
      }
    }
    return props.formDisabled;
  });

  
  /**
   * 新增
   */
  function add() {
    edit({});
  }

  /**
   * 编辑
   */
  function edit(record) {
    nextTick(() => {
      resetFields();
      const tmpData = {};
      Object.keys(formData).forEach((key) => {
        if(record.hasOwnProperty(key)){
          tmpData[key] = record[key]
        }
      })
      //赋值
      Object.assign(formData, tmpData);
    });
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    //循环数据
    for (let data in model) {
      //如果该数据是数组并且是字符串类型
      if (model[data] instanceof Array) {
        let valueType = getValueType(formRef.value.getProps, data);
        //如果是字符串类型的需要变成以逗号分割的字符串
        if (valueType === 'string') {
          model[data] = model[data].join(',');
        }
      }
    }
    await saveOrUpdate(model, isUpdate.value)
      .then((res) => {
        if (res.success) {
          createMessage.success(res.message);
          emit('ok');
        } else {
          createMessage.warning(res.message);
        }
      })
      .finally(() => {
        confirmLoading.value = false;
      });
  }


  defineExpose({
    add,
    edit,
    submitForm,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    height: 500px !important;
    overflow-y: auto;
    padding: 14px;
  }
</style>
