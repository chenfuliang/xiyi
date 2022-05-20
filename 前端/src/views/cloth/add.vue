<template>
  <div class="app-container">
    <el-form label-width="120px" :rules="baseRules" :model="cloth">
      <el-form-item label="衣物类型" prop="type">
        <el-input v-model="cloth.type" />
      </el-form-item>
      <el-form-item label="衣物颜色" prop="color">
        <el-input v-model="cloth.color" />
      </el-form-item>
      <el-form-item label="衣物状态" prop="washState">
        <el-switch
          v-model="cloth.washState"
          :active-value="'1'"
          active-color="#13ce66"
          :inactive-value="'0'"
          active-text="已清洗"
          inactive-color="#ff4949"
          inactive-text="未清洗"
        ></el-switch>
      </el-form-item>
      <el-form-item label="衣物品牌" prop="brand">
        <el-input v-model="cloth.brand" />
      </el-form-item>
      <!-- 衣物图片 TODO -->
      <el-form-item label="衣物图片" prop="imageUrl">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API + '/eduoss/fileoss'"
          class="avatar-uploader"
        >
          <img class="imgCover" :src="cloth.imageUrl" />
        </el-upload>
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
import clothApi from '@/api/cloth/cloth'
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
export default {
  setup() {
    //表单校验规则
    let baseRules = {
      type: [{ required: true, message: '请输入衣物类型', trigger: 'blur' }],
      color: [{ required: true, message: '请输入颜色', trigger: 'blur' }],
      washState: [{ required: false, message: '请选择状态', trigger: 'blur' }],
      brand: [{ required: true, message: '请输入品牌', trigger: 'blur' }],
      imageUrl: [{ required: true, message: '请输入图片', trigger: 'blur' }],
    }
    let cloth = reactive({})
    //图片提交地址
    cloth.imageUrl =
      'https://edu-000002.oss-cn-beijing.aliyuncs.com/2022/04/152f6ac2cb-6d2d-4c96-ad73-f0036acc4377file.png'
    const router = useRouter()
    let route = useRoute()
    let saveBtnDisabled = ref(false) // 保存按钮是否禁用

    //修改信息回显方法
    function getInfo(id) {
      clothApi
        .getClothsById(id)
        .then(res => {
          //reactive响应式针对属性而非对象。使用Object.assign(old,new)实现响应式赋值
          Object.assign(cloth, res.data.cloth)
        })
        .catch(err => {
          console.log(err)
        })
    }

    function updateCloth(cloth) {
      clothApi
        .updateCloths(cloth)
        .then(res => {
          ElMessage({
            type: 'success',
            message: '修改成功',
          })
        })
        .then(res => {
          router.push('/cloth/list')
        })
    }

    onMounted(() => {
      if (route.params && route.params.id) getInfo(route.params.id)
    })

    // 保存
    function saveData() {
      clothApi
        .addCloths(cloth)
        .then(() => {
          ElMessage({
            type: 'success',
            message: '保存成功',
          })
        })
        .then(() => {
          router.push('/cloth/list')
          // this.$router.push({ path: '/cloth/table' })
        })
    }
    //上传封面成功
    function handleAvatarSuccess(res, file) {
      cloth.imageUrl = res.data.url
    }

    function saveOrUpdate() {
      console.log(cloth)
      if (!cloth.cloId)
        //无id为添加
        saveData()
      else updateCloth(cloth) //有id则为修改
    }
    return {
      cloth,
      saveBtnDisabled,
      saveOrUpdate,
      saveData,
      updateCloth,
      router,
      getInfo,
      close,
      handleAvatarSuccess,
      baseRules,
    }
  },
}
</script>

<style lang=""></style>
