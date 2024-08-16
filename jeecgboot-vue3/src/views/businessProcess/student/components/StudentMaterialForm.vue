<template>
  <a-spin :spinning="confirmLoading">
    <a-form class="antd-modal-form" v-bind="formItemLayout" ref="formRef">
      <a-row>
        <a-col :span="12">
          <a-form-item label="材料类型" v-bind="validateInfos.materialType">
            <j-dict-select-tag v-model:value="formData.materialType" dictCode="material_type" placeholder="请选择材料类型" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="材料" v-bind="validateInfos.materiaUrl">
	          <j-upload @change=handelChange v-model:value="formData.materiaFile" :maxCount=1 :returnUrl=false :disabled="disabled" ></j-upload>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="名称" v-bind="validateInfos.name">
            <a-input v-model:value="formData.name" placeholder="请输入名称" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="材料后缀" v-bind="validateInfos.materialSuffix">
            <a-input v-model:value="formData.materialSuffix" placeholder="请输入材料后缀" disabled></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="大小，单位：字节" v-bind="validateInfos.size">
	          <a-input-number v-model:value="formData.size" placeholder="请输入大小，单位：字节" style="width: 100%" disabled/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="审核状态" v-bind="validateInfos.auditStatus">
	          <j-dict-select-tag v-model:value="formData.auditStatus" dictCode="audit_status" placeholder="请选择审核状态" :disabled="disabled"/>
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
  import JUpload from '/@/components/Form/src/jeecg/components/JUpload/JUpload.vue';
  import { getValueType } from '/@/utils';
  import { studentMaterialSaveOrUpdate } from '../Student.api';
  import { Form } from 'ant-design-vue';

  //接收主表id
  const mainId = inject('mainId');
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
        name: '',
        materiaFile:'[]',
        materiaUrl: '',   
        materialType: '',   
        materialSuffix: '',   
        size: undefined,
        auditStatus: 'wait',   
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
    materiaUrl: [{ required: true, message: '请上传材料!'},],
    materialType: [{ required: true, message: '请选择材料类型!'},],
    materialSuffix: [{ required: true, message: '请输入材料后缀!'},],
    size: [{ required: true, message: '请输入大小，单位：字节!'},],
    auditStatus: [{ required: true, message: '请选择审核状态!'},],
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
   * change
   */
  function handelChange(jsonStr: string) {
      try {
          let fileJsonArray = JSON.parse(jsonStr);
          const fileName = String(fileJsonArray[0].fileName);
          formData.name = fileName.substring(0, fileName.lastIndexOf("."));
          formData.materiaUrl = fileJsonArray[0].filePath;
          formData.materialSuffix = fileName.split(".").pop();
          formData.size = fileJsonArray[0].fileSize;
      } catch (e) {
          console.log("jsonStr=[%s]解析失败", jsonStr)
      }
  }

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
        if (record.hasOwnProperty(key)) {
          tmpData[key] = record[key];
        }
      });
      if (record.hasOwnProperty('id')) {
        let fileList: any[] = [];
        let fileJson = {
          fileName: tmpData['name'] + '.' + tmpData['materialSuffix'],
          filePath: tmpData['materiaUrl'],
          fileSize: tmpData['size'],
        };
        fileList.push(fileJson);
        tmpData['materiaFile'] = JSON.stringify(fileList);
      }
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
    if (unref(mainId)) {
      model['studentId'] = unref(mainId);
    }
    await studentMaterialSaveOrUpdate(model, isUpdate.value)
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
