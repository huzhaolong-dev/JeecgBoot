<template>
  <a-spin :spinning="confirmLoading">
    <a-form class="antd-modal-form" v-bind="formItemLayout" ref="formRef">
      <a-row>
        <a-col :span="12">
          <a-form-item label="雅思成绩" v-bind="validateInfos.ielts">
	          <a-input-number v-model:value="formData.ielts" placeholder="请输入雅思成绩" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="托福成绩" v-bind="validateInfos.toefl">
	          <a-input-number v-model:value="formData.toefl" placeholder="请输入托福成绩" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="GRE成绩" v-bind="validateInfos.gre">
	          <a-input-number v-model:value="formData.gre" placeholder="请输入GRE成绩" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="GMAT成绩" v-bind="validateInfos.gmat">
	          <a-input-number v-model:value="formData.gmat" placeholder="请输入GMAT成绩" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="GPA成绩" v-bind="validateInfos.gpa">
	          <a-input-number v-model:value="formData.gpa" placeholder="请输入GPA成绩" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="均分" v-bind="validateInfos.wam">
	          <a-input-number v-model:value="formData.wam" placeholder="请输入均分" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="描述" v-bind="validateInfos.description">
            <a-input v-model:value="formData.description" placeholder="请输入描述" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="备注" v-bind="validateInfos.remark">
            <a-input v-model:value="formData.remark" placeholder="请输入备注" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, onMounted, inject, defineProps, unref } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getValueType } from '/@/utils';
  import { studentGradeSaveOrUpdate } from '../Student.api';
  import { Form } from 'ant-design-vue';

  //接收主表id
  const mainId = inject('mainId');
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
        ielts: undefined,
        toefl: undefined,
        gre: undefined,
        gmat: undefined,
        gpa: undefined,
        wam: undefined,
        description: '',   
        remark: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });
  const props = defineProps({
    disabled: { type: Boolean, default: false },
  });
  const formItemLayout = {
    labelCol: { xs: { span: 24 }, sm: { span: 5 } },
    wrapperCol: { xs: { span: 24 }, sm: { span: 16 } },
  };
  
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
      Object.assign(formData,tmpData);
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
    if (unref(mainId)) {
      model['studentId'] = unref(mainId);
    }
    await studentGradeSaveOrUpdate(model, isUpdate.value)
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
