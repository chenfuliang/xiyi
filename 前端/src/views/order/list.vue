<template>
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="请输入订单金额">
      <el-input v-model="data.ordQuery.cost" placeholder="订单金额" />
    </el-form-item>
    <el-form-item label="订单日期">
      <el-date-picker
        v-model="data.ordQuery.receiveTime"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择开始时间"
      />
      <el-date-picker
        v-model="data.ordQuery.fetchTime"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择结束时间"
      />
    </el-form-item>
    <el-form-item label="请选择订单状态">
      <el-select v-model="data.ordQuery.payState" placeholder="订单状态">
        <el-option label="未支付" :value="'0'" />
        <el-option label="已支付" :value="'1'" />
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
    <el-table-column prop="ordId" label="Id" />
    <el-table-column prop="receiveTime" label="接受时间" />
    <el-table-column prop="fetchTime" label="完成时间" />
    <el-table-column prop="cost" label="订单金额" />
    <el-table-column label="支付状态">
      <template #default="scope">
        {{ scope.row.payState == 1 ? '已支付' : '未支付' }}
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <router-link :to="`/ord/edit/${scope.row.ordId}`">
          <el-button type="primary" size="small" icon="el-icon-edit">
            修改
          </el-button>
        </router-link>
        <router-link :to="`/ord/track/${scope.row.ordId}`">
          <el-button type="primary" size="small" icon="el-icon-edit">
            追踪订单
          </el-button>
        </router-link>
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="deleteOrdById(scope.row.ordId)"
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
import ordApi from '@/api/order/order'
import { reactive, onMounted, toRefs } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
export default {
  setup() {
    let datas = reactive({
      data: {
        page: 1,
        limit: 3,
        ordQuery: {},
        total: 0,
        list: null,
      },
    })

    function getList() {
      ordApi
        .getOrdListPage(datas.data.page, datas.data.limit, datas.data.ordQuery)
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
      datas.data.ordQuery = {}
      getList()
    }

    function goToUpdate(id) {}

    function deleteOrdById(id) {
      ElMessageBox.confirm('是否删除该订单记录?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }).then(() => {
        ordApi.deleteOrd(id).then(res => {
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
      deleteOrdById,
      goToUpdate,
      clear,
    }
  },
}
</script>

<style lang=""></style>
