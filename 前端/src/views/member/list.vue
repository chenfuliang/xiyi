<template>
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="请输入会员名">
      <el-input v-model="data.memberQuery.memId" placeholder="会员id" />
    </el-form-item>
    <el-form-item label="会员加入日期">
      <el-date-picker
        v-model="data.memberQuery.begin"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择开始时间"
      />
    </el-form-item>
    <el-form-item label="请选择会员身份">
      <el-select v-model="data.memberQuery.level" placeholder="会员等级">
        <el-option label="会员1" :value="'1'" />
        <el-option label="会员2" :value="'2'" />
        <el-option label="会员3" :value="'3'" />
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
    <el-table-column prop="memId" label="会员id" />
    <el-table-column prop="cusId" label="顾客id" />
    <el-table-column label="余额">
      <template #default="scope">
        {{ scope.row.balance ? scope.row.balance : 0 }}
      </template>
    </el-table-column>

    <el-table-column label="会员等级">
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
    <el-table-column label="操作">
      <template #default="scope">
        <router-link :to="`/member/edit/${scope.row.memId}`">
          <el-button type="primary" size="small" icon="el-icon-edit">
            详情
          </el-button>
        </router-link>

        <el-button
          type="danger"
          size="small"
          @click="deleteMemberById(scope.row.cusId)"
          icon="el-icon-edit"
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
import memberApi from '@/api/member/member'
import { reactive, onMounted, toRefs } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
export default {
  setup() {
    let datas = reactive({
      data: {
        page: 1,
        limit: 3,
        memberQuery: { sex: ' ' },
        total: 0,
        list: null,
      },
    })

    function getList() {
      memberApi
        .getMemberListPage(
          datas.data.page,
          datas.data.limit,
          datas.data.memberQuery
        )
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
      datas.data.memberQuery = { sex: ' ' }
      getList()
    }

    function deleteMemberById(id) {
      ElMessageBox.confirm('是否删除该会员记录?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }).then(() => {
        memberApi.deleteMember(id).then(res => {
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
      deleteMemberById,
      clear,
    }
  },
}
</script>

<style lang=""></style>
