import request from '@/utils/request'

export default {
  getEmpListPage(current, limit, query) {
    console.log(query)
    return request({
      url: `/software/emp/getEmpListPage/${current}/${limit}`,
      method: 'post',
      data: query,
    })
  },

  //添加员工
  addEmp(emp) {
    return request({
      url: `/software/emp/addEmp`,
      method: 'post',
      data: emp,
    })
  },
  //修改员工权限
  // updateEmpLevel() {
  //   return request({
  //     url: `/software/emp/updateEmpLevel`,
  //     method: 'post',
  //     data: emp,
  //   })
  // },
  //根据id查询员工
  getEmpById(empId) {
    return request({
      url: `/software/emp/getEmpById/${empId}`,
      method: 'get',
    })
  },

  //修改员工
  updateEmp(emp) {
    return request({
      url: `/software/emp/updateEmp`,
      method: 'post',
      data: emp,
    })
  },

  //删除员工
  deleteEmp(empId) {
    return request({
      url: `/software/emp/deleteEmp/${empId}`,
      method: 'delete',
    })
  },
}
