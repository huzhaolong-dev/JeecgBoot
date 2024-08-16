<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="12">
          <a-form-item label="身份类型" v-bind="validateInfos.idType">
	          <j-dict-select-tag v-model:value="formData.idType" dictCode="id_type" placeholder="请选择身份类型" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="身份证号码" v-bind="validateInfos.idCard">
            <a-input v-model:value="formData.idCard" placeholder="请输入身份证号码" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="名称" v-bind="validateInfos.name">
            <a-input v-model:value="formData.name" placeholder="请输入名称" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="拼音" v-bind="validateInfos.pinyin">
            <a-input v-model:value="formData.pinyin" placeholder="请输入拼音" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="性别" v-bind="validateInfos.sex">
            <j-dict-select-tag type='radio' v-model:value="formData.sex" dictCode="sex" placeholder="请选择性别" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="头像" v-bind="validateInfos.avatarUrl">
	          <j-image-upload  v-model:value="formData.avatarUrl" :disabled="disabled"></j-image-upload>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="学业阶段" v-bind="validateInfos.studyStage">
	          <j-dict-select-tag v-model:value="formData.studyStage" dictCode="study_stage" placeholder="请选择学业阶段" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="出生日期" v-bind="validateInfos.birthday">
		        <a-date-picker placeholder="请选择出生日期" v-model:value="formData.birthday" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="户口地" v-bind="validateInfos.registrationAddr">
            <a-input v-model:value="formData.registrationAddr" placeholder="请输入户口地" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="出生地" v-bind="validateInfos.birthplaceAddr">
            <a-input v-model:value="formData.birthplaceAddr" placeholder="请输入出生地" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="家庭地址" v-bind="validateInfos.homeAddr">
            <a-input v-model:value="formData.homeAddr" placeholder="请输入家庭地址" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="手机" v-bind="validateInfos.telephone">
            <a-input v-model:value="formData.telephone" placeholder="请输入手机" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="紧急联系人手机" v-bind="validateInfos.emergencyPhone">
            <a-input v-model:value="formData.emergencyPhone" placeholder="请输入紧急联系人手机" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="电子邮件地址" v-bind="validateInfos.email">
            <a-input v-model:value="formData.email" placeholder="请输入电子邮件地址" :disabled="disabled"></a-input>
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
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JImageUpload from '/@/components/Form/src/jeecg/components/JImageUpload.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../Student.api';
  import { Form } from 'ant-design-vue';
  
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
        idType: '',   
        idCard: '',   
        name: '',   
        pinyin: '',   
        sex: '',   
        avatarUrl: '',   
        studyStage: '',   
        birthday: '',   
        registrationAddr: '',   
        birthplaceAddr: '',   
        homeAddr: '',   
        telephone: '',   
        emergencyPhone: '',   
        email: '',   
        description: '',   
        remark: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    idType: [{ required: true, message: '请输入身份类型!'},],
    idCard: [{ required: true, message: '请输入身份证号码!'},],
    name: [{ required: true, message: '请输入名称!'},],
    pinyin: [{ required: true, message: '请输入拼音!'},],
    sex: [{ required: true, message: '请输入性别!'},],
    avatarUrl: [{ required: true, message: '请输入头像!'},],
    studyStage: [{ required: true, message: '请输入学业阶段!'},],
    birthday: [{ required: true, message: '请输入出生日期!'},],
    telephone: [{ required: true, message: '请输入手机!'},],
    emergencyPhone: [{ required: true, message: '请输入紧急联系人手机!'},],
    email: [{ required: true, message: '请输入电子邮件地址!'},],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });
  const formRef = ref();
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
