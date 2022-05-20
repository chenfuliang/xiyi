<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="客户名称">
        <el-input v-model="cus.name" />
      </el-form-item>
      <el-form-item label="客户联系方式">
        <el-input v-model="cus.phone" />
      </el-form-item>
      <el-form-item label="客户性别">
        <el-switch
          v-model="cus.sex"
          active-color="#f38b00"
          :active-value="'1'"
          :inactive-value="'0'"
          inactive-color="#0086d1"
          active-text="男"
          inactive-text="女"
        ></el-switch>
      </el-form-item>
      <el-form-item label="是否会员">
        <el-switch
          v-model="isMember"
          @change="addOrDeleteMember"
          active-color="#13ce66"
          :active-value="true"
          :inactive-value="false"
          inactive-color="#ff4949"
          active-text="成为会员"
          inactive-text="取消会员"
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
import cusApi from '@/api/customer/customer'
import memberApi from '@/api/member/member'
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
export default {
  setup() {
    let cus = reactive({})
    let isMember = ref()
    const router = useRouter()
    let route = useRoute()
    let saveBtnDisabled = ref(false) // 保存按钮是否禁用

    //修改信息回显方法
    function getInfo(id) {
      cusApi
        .getCusById(id)
        .then(res => {
          //reactive响应式针对属性而非对象。使用Object.assign(old,new)实现响应式赋值
          Object.assign(cus, res.data.customer)
          isMember.value = cus.memId ? true : false
        })
        .catch(err => {
          console.log(err)
        })
    }

    function addOrDeleteMember() {
      if (isMember.value) {
        memberApi.addMember(cus.cusId).then(res => {
          ElMessage.success('已添加为会员')
        })
      } else {
        memberApi.deleteMember(cus.cusId)
        ElMessage.success('已取消会员')
      }
    }

    function updateCus(cus) {
      cusApi
        .updateCus(cus)
        .then(res => {
          if (res.data.success)
            ElMessage({
              type: 'success',
              message: '修改成功',
            })
        })
        .then(res => {
          router.push('/cus/list')
        })
    }
    onMounted(() => {
      if (route.params && route.params.id) getInfo(route.params.id)
    })

    // 保存
    function saveData() {
      cusApi
        .addCus(cus)
        .then(() => {
          ElMessage({
            type: 'success',
            message: '保存成功',
          })
        })
        .then(() => {
          router.push('/cus/list')
          // this.$router.push({ path: '/cus/table' })
        })
    }

    function saveOrUpdate() {
      console.log(cus)
      if (!cus.cusId)
        //无id为添加
        saveData()
      else updateCus(cus) //有id则为修改
    }
    return {
      cus,
      saveBtnDisabled,
      saveOrUpdate,
      saveData,
      updateCus,
      router,
      getInfo,
      close,
      addOrDeleteMember,
      isMember,
    }
  },
}
</script>

<style lang=""></style>
