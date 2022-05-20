<template>
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="请输入员工名">
      <el-input v-model="data.empQuery.name" placeholder="员工名称" />
    </el-form-item>
    <el-form-item label="员工加入日期">
      <el-date-picker
        v-model="data.empQuery.begin"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择开始时间"
      />
      <el-date-picker
        v-model="data.empQuery.end"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择结束时间"
      />
    </el-form-item>
    <el-form-item label="请选择员工级别">
      <el-select v-model="data.empQuery.level" placeholder="员工级别">
        <el-option label="可查看" :value="0" />
        <el-option label="可修改" :value="1" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">
        查询
      </el-button>
      <el-button type="danger" icon="el-icon-delete" @click="clear()">
        清空
      </el-button>
    </el-form-item>
  </el-form>

  <el-table :data="data.list" stripe style="width: 100%">
    <el-table-column prop="empId" label="Id" />
    <el-table-column prop="name" label="员工姓名" />
    <el-table-column prop="account" label="账号" />
    <el-table-column prop="password" label="密码" />
    <el-table-column label="员工级别">
      <template #default="scope">
        {{ scope.row.level == 1 ? '可修改' : '可查看' }}
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <router-link :to="`/emp/edit/${scope.row.empId}`">
          <el-button type="primary" size="small" icon="el-icon-edit">
            编辑
          </el-button>
        </router-link>

        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="deleteEmpById(scope.row.empId)"
        >
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination
    style="float:right"
    v-model:currentPage="data.page"
    v-model:page-size="data.limit"
    layout="total, prev, pager, next, jumper"
    :total="data.total"
    @current-change="getList"
  />
</template>

<script>
import empApi from '@/api/employee/employee'
import { reactive, onMounted, toRefs } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
export default {
  setup() {
    let datas = reactive({
      data: {
        page: 1,
        limit: 3,
        empQuery: {},
        total: 0,
        list: null,
      },
    })

    function getList() {
      empApi
        .getEmpListPage(datas.data.page, datas.data.limit, datas.data.empQuery)
        .then(res => {
          datas.data.list = res.data.rows
          datas.data.total = res.data.total
        })
        .catch(err => {
          console.log(err)
        })
    }
    //清空查询条件
    function clear() {
      datas.data.empQuery = {}
      getList()
    }

    function goToUpdate(id) {}

    function deleteEmpById(id) {
      ElMessageBox.confirm('是否删除该员工记录?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }).then(() => {
        empApi.deleteEmp(id).then(res => {
          //刷新删除后的列表
          getList()
        })
        ElMessage({
          type: 'success',
          message: 'Delete completed',
        })
      })
    }
    onMounted(() => {
      getList()
    })

    return {
      ...toRefs(datas),
      getList,
      deleteEmpById,
      goToUpdate,
      clear,
    }
  },
}
</script>

<style lang=""></style>
