import request from '@/utils/request'

export default {
  getOrdListPage(current, limit, query) {
    return request({
      url: `/software/order/getOrderListPage/${current}/${limit}`,
      method: 'post',
      data: query,
    })
  },

  //添加客户
  addOrd(ord) {
    return request({
      url: `/software/order/addOrder`,
      method: 'post',
      data: ord,
    })
  },

  //根据id查询客户
  getOrdById(ordId) {
    return request({
      url: `/software/order/getOrderById/${ordId}`,
      method: 'get',
    })
  },

  //修改客户
  updateOrd(ord) {
    return request({
      url: `/software/order/updateOrder`,
      method: 'post',
      data: ord,
    })
  },

  //删除客户
  deleteOrd(ordId) {
    return request({
      url: `/software/order/deleteOrder/${ordId}`,
      method: 'delete',
    })
  },
}
