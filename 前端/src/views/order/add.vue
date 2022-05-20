<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="订单处理员工">
        <el-input disabled v-model="ord.empName" />
      </el-form-item>
      <el-form-item label="订单顾客">
        <el-input disabled v-model="ord.cusName" />
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="ord.phone" />
      </el-form-item>
      <el-form-item label="订单金额">
        <el-input v-model.number="ord.cost" />
      </el-form-item>
      <el-form-item label="收衣时间">
        <el-date-picker
          v-model="ord.receiveTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm"
          format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择开始时间"
        />
      </el-form-item>
      <el-form-item label="取衣时间">
        <el-date-picker
          v-model="ord.fetchTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm"
          format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择开始时间"
        />
      </el-form-item>
      <el-form-item label="订单状态">
        <el-switch
          v-model="ord.payState"
          active-color="#13ce66"
          :active-value="'1'"
          :inactive-value="'0'"
          active-text="已支付"
          inactive-text="未支付"
          inactive-color="#ff4949"
        ></el-switch>
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
        >
          保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import ordApi from '@/api/order/order'
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
export default {
  setup() {
    let ord = reactive({})
    const router = useRouter()
    let route = useRoute()
    let saveBtnDisabled = ref(false) // 保存按钮是否禁用

    //修改信息回显方法
    function getInfo(id) {
      ordApi
        .getOrdById(id)
        .then(res => {
          //reactive响应式针对属性而非对象。使用Object.assign(old,new)实现响应式赋值
          Object.assign(ord, res.data.order)
        })
        .catch(err => {
          console.log(err)
        })
    }

    function updateOrd(ord) {
      ordApi
        .updateOrd(ord)
        .then(res => {
          ElMessage({
            type: 'success',
            message: '修改成功',
          })
        })
        .then(res => {
          router.push('/ord/list')
        })
    }
    onMounted(() => {
      if (route.params && route.params.id) getInfo(route.params.id)
    })

    // 保存
    function saveData() {
      ordApi
        .addOrd(ord)
        .then(() => {
          ElMessage({
            type: 'success',
            message: '保存成功',
          })
        })
        .then(() => {
          router.push('/ord/list')
          // this.$router.push({ path: '/ord/table' })
        })
    }

    function saveOrUpdate() {
      console.log(ord)
      if (!ord.ordId)
        //无id为添加
        saveData()
      else updateOrd(ord) //有id则为修改
    }
    return {
      ord,
      saveBtnDisabled,
      saveOrUpdate,
      saveData,
      updateOrd,
      router,
      getInfo,
      close,
    }
  },
}
</script>

<style lang=""></style>
