<template>
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="请输入会员id">
      <el-input v-model="data.investQuery.memId" placeholder="会员id" />
    </el-form-item>
    <el-form-item label="充值日期">
      <el-date-picker
        v-model="data.investQuery.begin"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择开始时间"
      />

      <el-date-picker
        v-model="data.investQuery.end"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择结束时间"
      />
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
    <el-table-column prop="memId" label="会员id" />
    <el-table-column label="会员级别">
      <template #default="scope">
        {{
          scope.row.memLevel == '1'
            ? '会员1'
            : scope.row.memLevel == '2'
            ? '会员2'
            : '会员3'
        }}
      </template>
    </el-table-column>
    <el-table-column prop="investMoney" label="余额变动" />
    <el-table-column prop="invTime" label="余额变动日期" />
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
import investApi from '@/api/invest/invest'
import { reactive, onMounted, toRefs } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
export default {
  setup() {
    let route = useRoute()
    let datas = reactive({
      data: {
        page: 1,
        limit: 3,
        investQuery: { memId: route.params.memId },
        total: 0,
        list: null,
      },
    })

    function getList() {
      investApi
        .getInvestListPage(
          datas.data.page,
          datas.data.limit,
          datas.data.investQuery
        )
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
      datas.data.investQuery = {}
      getList()
    }

    function goToUpdate(id) {}

    function deleteInvestById(id) {
      ElMessageBox.confirm('是否删除该充值记录?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }).then(() => {
        investApi.deleteInvest(id).then(res => {
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
      deleteInvestById,
      goToUpdate,
      clear,
    }
  },
}
</script>

<style lang=""></style>
