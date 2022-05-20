import request from '@/utils/request'

export default {
  getCusListPage(current, limit, query) {
    return request({
      url: `/software/cus/getCustomerListPage/${current}/${limit}`,
      method: 'post',
      data: query,
    })
  },

  //添加客户
  addCus(cus) {
    return request({
      url: `/software/cus/addCustomer`,
      method: 'post',
      data: cus,
    })
  },

  //根据id查询客户
  getCusById(cusId) {
    return request({
      url: `/software/cus/getCustomerById/${cusId}`,
      method: 'get',
    })
  },

  //修改客户
  updateCus(cus) {
    return request({
      url: `/software/cus/updateCustomer`,
      method: 'post',
      data: cus,
    })
  },

  //删除客户
  deleteCus(cusId) {
    return request({
      url: `/software/cus/deleteCustomer/${cusId}`,
      method: 'delete',
    })
  },
}
