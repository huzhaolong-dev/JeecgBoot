<template>
  <a-spin :spinning="confirmLoading">
    <a-form class="antd-modal-form" v-bind="formItemLayout" ref="formRef">
      <a-row>
        <a-col :span="12">
          <a-form-item label="名称" v-bind="validateInfos.name">
            <a-input v-model:value="formData.name" placeholder="请输入名称" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="开始时间" v-bind="validateInfos.startDate">
		        <a-date-picker placeholder="请选择开始时间" v-model:value="formData.startDate" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="结束时间" v-bind="validateInfos.endDate">
		        <a-date-picker placeholder="请选择结束时间" v-model:value="formData.endDate" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="在读年级" v-bind="validateInfos.grade">
	          <j-dict-select-tag v-model:value="formData.grade" dictCode="grade" placeholder="请选择在读年级" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="内容" v-bind="validateInfos.content">
	          <a-textarea v-model:value="formData.content" :rows="4" placeholder="提示：可以填写教育期间自我成长的表现、经历、感受等" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="成果" v-bind="validateInfos.results">
	          <a-textarea v-model:value="formData.results" :rows="4" placeholder="提示：可以填写教育期间获得的奖项、输出的论文、作品集等可以量化的成果等" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="中国大学编码" v-bind="validateInfos.chUniversityCode">
	          <j-search-select v-model:value="formData.chUniversityCode" dict="ch_university,ch_name,code" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="中国专业分类编码" v-bind="validateInfos.chMajorCategoryCode">
	          <j-search-select-ch-major v-model:value="formData.chMajorCategoryCode" dict="ch_major_category where ch_major_category_type = '2',name,code" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="中国专业编码" v-bind="validateInfos.chMajorCode">
	          <j-search-select-ch-major v-model:value="formData.chMajorCode" dict="ch_major_category where ch_major_category_type = '3',name,code" :pcode="formData.chMajorCategoryCode" :disabled="disabled" />
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
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JSearchSelectChMajor from '/@/components/Form/src/jeecg/components/JSearchSelectChMajor.vue';
  import { getValueType } from '/@/utils';
  import { studentEduBackgroundSaveOrUpdate } from '../Student.api';
  import { Form } from 'ant-design-vue';

  //接收主表id
  const mainId = inject('mainId');
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
        name: '',   
        startDate: '',   
        endDate: '',   
        grade: '',   
        content: '',   
        results: '',   
        chUniversityCode: '',   
        chMajorCategoryCode: '',   
        chMajorCode: '',   
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
    startDate: [{ required: true, message: '请输入开始时间!'},],
    endDate: [{ required: true, message: '请输入结束时间!'},],
    grade: [{ required: true, message: '请输入在读年级!'},],
    content: [{ required: true, message: '请输入内容!'},],
    chUniversityCode: [{ required: true, message: '请输入中国大学编码!'},],
    chMajorCategoryCode: [{ required: true, message: '请输入中国专业分类编码!'},],
    chMajorCode: [{ required: true, message: '请输入中国专业编码!'},],
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
    await studentEduBackgroundSaveOrUpdate(model, isUpdate.value)
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
