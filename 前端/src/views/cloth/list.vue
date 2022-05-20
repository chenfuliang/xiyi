<template>
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="自衣物送达日期起">
      <el-date-picker
        v-model="data.clothQuery.begin"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm"
        format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择开始时间"
      />
    </el-form-item>
    <el-form-item label="请选择衣物状态">
      <el-select v-model="data.clothQuery.washState" placeholder="衣物状态">
        <el-option label="已清洗" :value="1" />
        <el-option label="未清洗" :value="0" />
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
    <el-table-column prop="cloId" label="Id" />
    <el-table-column prop="color" label="color" />
    <el-table-column label="衣物图片">
      <template #default="scope">
        <img
          :src="scope.row.imageUrl"
          alt=""
          style="width:170px;height:100px"
        />
      </template>
    </el-table-column>
    <el-table-column prop="type" label="type" />
    <el-table-column prop="brand" label="brand" />
    <el-table-column label="衣物状态">
      <template #default="scope">
        {{ scope.row.washState == '1' ? '已清洗' : '未清洗' }}
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <router-link :to="`/cloth/edit/${scope.row.cloId}`">
          <el-button type="primary" size="small" icon="el-icon-edit">
            编辑详情
          </el-button>
        </router-link>
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="deleteClothById(scope.row.cloId)"
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
import clothApi from '@/api/cloth/cloth'
import { reactive, onMounted, toRefs } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
export default {
  setup() {
    let datas = reactive({
      data: {
        page: 1,
        limit: 3,
        clothQuery: { sex: ' ' },
        total: 0,
        list: null,
      },
    })

    function getList() {
      clothApi
        .pageClothsList(
          datas.data.page,
          datas.data.limit,
          datas.data.clothQuery
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
      datas.data.clothQuery = { sex: ' ' }
      getList()
    }

    function deleteClothById(id) {
      ElMessageBox.confirm('是否删除该衣物记录?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }).then(() => {
        clothApi.deleteCloth(id).then(res => {
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
      deleteClothById,
      clear,
    }
  },
}
</script>

<style lang=""></style>
