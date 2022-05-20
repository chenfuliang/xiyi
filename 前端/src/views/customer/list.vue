<template>
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="请输入客户名">
      <el-input v-model="data.cusQuery.name" placeholder="客户名称" />
    </el-form-item>
    <el-form-item label="客户加入日期">
      <el-date-picker
        v-model="data.cusQuery.begin"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择开始时间"
      />
      <el-date-picker
        v-model="data.cusQuery.end"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择结束时间"
      />
    </el-form-item>
    <el-form-item label="请选择客户性别">
      <el-select v-model="data.cusQuery.sex" placeholder="客户性别">
        <el-option label="男" :value="'1'" />
        <el-option label="女" :value="'0'" />
      </el-select>
    </el-form-item>
    <el-form-item label="请选择客户身份">
      <el-select v-model="data.cusQuery.memId" placeholder="客户身份">
        <el-option label="会员" :value="'1'" />
        <el-option label="非会员" :value="'0'" />
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
    <el-table-column prop="cusId" label="Id" />
    <el-table-column prop="phone" label="phone" />
    <el-table-column prop="name" label="name" />
    <el-table-column label="客户性别">
      <template #default="scope">
        {{ scope.row.sex == '1' ? '男' : '女' }}
      </template>
    </el-table-column>
    <el-table-column label="客户身份">
      <template #default="scope">
        {{ scope.row.memId ? '会员' : '非会员' }}
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <router-link :to="`/cus/edit/${scope.row.cusId}`">
          <el-button type="primary" size="small" icon="el-icon-edit">
            编辑
          </el-button>
        </router-link>

        <el-button type="primary" size="small" icon="el-icon-primary">
          查询余额
        </el-button>
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="deleteCusById(scope.row.cusId)"
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
import cusApi from '@/api/customer/customer'
import { reactive, onMounted, toRefs } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
export default {
  setup() {
    let datas = reactive({
      data: {
        page: 1,
        limit: 3,
        cusQuery: { sex: ' ' },
        total: 0,
        list: null,
      },
    })

    function getList() {
      cusApi
        .getCusListPage(datas.data.page, datas.data.limit, datas.data.cusQuery)
        .then(res => {
          console.log(res)
          datas.data.list = res.data.rows
          datas.data.total = res.data.total
        })
        .catch(err => {
          console.log(err)
        })
    }
    //清空查询条件
    function clear() {
      datas.data.cusQuery = { sex: ' ' }
      getList()
    }

    function deleteCusById(id) {
      ElMessageBox.confirm('是否删除该客户记录?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }).then(() => {
        cusApi.deleteCus(id).then(res => {
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
      deleteCusById,
      clear,
    }
  },
}
</script>

<style lang=""></style>
