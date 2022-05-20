<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="会员id">
        <el-input disabled v-model="member.memId" />
      </el-form-item>

      <el-form-item label="会员等级">
        <el-select v-model="member.memLevel" placeholder="会员等级">
          <el-option label="会员1" :value="'1'" />
          <el-option label="会员2" :value="'2'" />
          <el-option label="会员3" :value="'3'" />
        </el-select>
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
import memberApi from '@/api/member/member'
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
export default {
  setup() {
    let member = reactive({})
    const router = useRouter()
    let route = useRoute()
    let saveBtnDisabled = ref(false) // 保存按钮是否禁用

    //修改信息回显方法
    function getInfo(id) {
      memberApi
        .getMemberById(id)
        .then(res => {
          //reactive响应式针对属性而非对象。使用Object.assign(old,new)实现响应式赋值
          Object.assign(member, res.data.member)
        })
        .catch(err => {
          console.log(err)
        })
    }

    //修改member
    function updateMember(member) {
      memberApi
        .updateMember(member)
        .then(res => {
          ElMessage({
            type: 'success',
            message: '修改成功',
          })
        })
        .then(res => {
          router.push('/member/list')
        })
    }

    onMounted(() => {
      if (route.params && route.params.id) getInfo(route.params.id)
    })

    // 保存
    function saveData() {
      memberApi
        .addMember(member)
        .then(() => {
          ElMessage({
            type: 'success',
            message: '保存成功',
          })
        })
        .then(() => {
          router.push('/member/list')
          // this.$router.push({ path: '/member/table' })
        })
    }

    function saveOrUpdate() {
      console.log(member)
      if (!member.memberId)
        //无id为添加
        saveData()
      else updateMember(member) //有id则为修改
    }
    return {
      member,
      saveBtnDisabled,
      saveOrUpdate,
      saveData,
      updateMember,
      router,
      getInfo,
      close,
    }
  },
}
</script>

<style lang=""></style>
