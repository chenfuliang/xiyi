<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="员工名称">
        <el-input v-model="emp.name" />
      </el-form-item>
      <el-form-item label="员工账号">
        <el-input v-model="emp.account" />
      </el-form-item>
      <el-form-item label="员工密码">
        <el-input v-model="emp.password" />
      </el-form-item>
      <el-form-item label="员工权限">
        <el-switch
          v-model="emp.level"
          active-color="#13ce66"
          :active-value="'1'"
          :inactive-value="'0'"
          active-text="可修改"
          inactive-text="可查看"
          inactive-color="#ff4949"
          label="可查看"
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
import empApi from '@/api/employee/employee'
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
export default {
  setup() {
    let emp = reactive({})
    const router = useRouter()
    let route = useRoute()
    let saveBtnDisabled = ref(false) // 保存按钮是否禁用

    //修改信息回显方法
    function getInfo(id) {
      empApi
        .getEmpById(id)
        .then(res => {
          //reactive响应式针对属性而非对象。使用Object.assign(old,new)深拷贝赋值
          Object.assign(emp, res.data.emp)
        })
        .catch(err => {
          console.log(err)
        })
    }

    function updateEmp(emp) {
      empApi
        .updateEmp(emp)
        .then(res => {
          if (emp.level == '1')
            ElMessage({
              type: 'success',
              message: '修改成功,已开启修改权限',
            })
          else
            ElMessage({
              type: 'success',
              message: '修改成功,已关闭修改权限',
            })
        })
        .then(res => {
          router.push('/emp/list')
        })
    }
    onMounted(() => {
      if (route.params && route.params.id) getInfo(route.params.id)
    })

    // 保存
    function saveData() {
      empApi
        .addEmp(emp)
        .then(() => {
          ElMessage({
            type: 'success',
            message: '保存成功',
          })
        })
        .then(() => {
          router.push('/emp/list')
          // this.$router.push({ path: '/emp/table' })
        })
    }

    // function updateEmpLevel(eid, level) {
    //   empApi
    //     .updateEmpLevel(emp)
    //     .then(res => {
    //       ElMessage({
    //         type: 'success',
    //         message: '成功修改权限',
    //       })
    //     })
    //     .then(res => {
    //       router.push('/emp/list')
    //     })
    // }

    function saveOrUpdate() {
      console.log(emp)
      if (!emp.empId)
        //无id为添加
        saveData()
      else updateEmp(emp) //有id则为修改
    }
    return {
      emp,
      saveBtnDisabled,
      saveOrUpdate,
      saveData,
      updateEmp,
      router,
      getInfo,
      close,
    }
  },
}
</script>

<style lang=""></style>
