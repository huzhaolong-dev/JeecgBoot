<template>
 <div class="p-2">
   <!--查询区域-->
   <div class="jeecg-basic-table-form-container">
     <a-form ref="formRef" @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
       <a-row :gutter="24">
         <a-col :lg="6">
           <a-form-item name="name">
             <template #label><span title="名称">名称</span></template>
             <a-input placeholder="请输入名称" v-model:value="queryParam.name"></a-input>
           </a-form-item>
         </a-col>
         <a-col :lg="6">
           <a-form-item name="materialType">
             <template #label><span title="材料类型">材料类型</span></template>
             <j-dict-select-tag placeholder="请选择材料类型" v-model:value="queryParam.materialType" dictCode="material_type" />
           </a-form-item>
         </a-col>
         <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
                <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">
                  {{ toggleSearchStatus ? '收起' : '展开' }}
                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />
                </a>
              </a-col>
            </span>
         </a-col>
       </a-row>
     </a-form>
   </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button>批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template v-slot:bodyCell="{ column, record, index, text }">
        <template v-if="column.dataIndex==='materiaUrl'">
          <!--文件字段回显插槽-->
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载</a-button>
        </template>
      </template>
    </BasicTable>

    <StudentMaterialModal ref="registerModal" @success="handleSuccess"/>
   </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, unref, inject, watch } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage'
  import StudentMaterialModal from './components/StudentMaterialModal.vue'
  import { studentMaterialColumns } from './Student.data';
  import { studentMaterialList, studentMaterialDelete, studentMaterialDeleteBatch, studentMaterialExportXlsUrl, studentMaterialImportUrl } from './Student.api';
  import { isEmpty } from "/@/utils/is";
  import { useMessage } from '/@/hooks/web/useMessage';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import JDictSelectTag from "@/components/Form/src/jeecg/components/JDictSelectTag.vue";
  
  const toggleSearchStatus = ref<boolean>(false);
  //接收主表id
  const mainId = inject('mainId') || '';
  //提示弹窗
  const $message = useMessage()
  const queryParam = {};
  // 列表页面公共参数、方法
  const { prefixCls, tableContext, onImportXls, onExportXls } = useListPage({
    tableProps: {
      api: studentMaterialList,
      columns: studentMaterialColumns,
      canResize: false,
      useSearchForm: false,
      actionColumn: {
        width: 180,
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam);
      },
    },
    exportConfig: {
      name: '学生材料模块',
      url: studentMaterialExportXlsUrl,
      params: {
        'studentId': mainId
      }
    },
    importConfig: {
      url: ()=>{
        return studentMaterialImportUrl + '/' + unref(mainId)
      }
    }
  });

  //注册table数据
  const [registerTable, { reload}, { rowSelection, selectedRowKeys }] = tableContext;
  const registerModal = ref();
  const formRef = ref();
  const labelCol = reactive({
    xs:24,
    sm:4,
    xl:6,
    xxl:4
  });
  const wrapperCol = reactive({
    xs: 24,
    sm: 20,
  });

  /**
   * 新增事件
   */
  function handleAdd() {
    if (isEmpty(unref(mainId))) {
        $message.createMessage.warning('请选择一个主表信息')
        return;
    }
    registerModal.value.disableSubmit = false;
    registerModal.value.add();
  }

  /**
   * 编辑事件
   */
  async function handleEdit(record: Recordable) {
    registerModal.value.disableSubmit = false;
    registerModal.value.edit(record);
  }

  /**
   * 详情事件
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    registerModal.value.edit(record);
  }
  
  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await studentMaterialDelete({id: record.id}, handleSuccess);
  }

  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await studentMaterialDeleteBatch({ids: selectedRowKeys.value}, handleSuccess);
  }

  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }

  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
      },
    ]
  }
  
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record){
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      },
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        },
      },
    ];
  }

  /**
   * 查询
   */
  function searchQuery() {
    reload();
  }

  /**
   * 重置
   */
  function searchReset() {
    formRef.value.resetFields();
    selectedRowKeys.value = [];
    //刷新数据
    reload();
  }
  
  watch(mainId, () => {
    queryParam['studentId'] = unref(mainId);
    reload();
  });
</script>
<style lang="less" scoped>
  .jeecg-basic-table-form-container {
    padding: 0;
    .table-page-search-submitButtons {
      display: block;
      margin-bottom: 24px;
      white-space: nowrap;
    }
    .query-group-cust{
      min-width: 100px !important;
    }
    .query-group-split-cust{
      width: 30px;
      display: inline-block;
      text-align: center
    }
  }
</style>
