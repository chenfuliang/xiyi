import request from '@/utils/request'

export default {
  getInvestListPage(current, limit, query) {
    return request({
      url: `/software/inves/getInvesListPage/${current}/${limit}`,
      method: 'post',
      data: query,
    })
  },

  //添加余额记录
  addInvest(cusId, money) {
    return request({
      url: `/software/inves/addInves/${cusId}/${money}`,
      method: 'get',
    })
  },

  // //根据会员id查询余额记录
  // getInvestById(memberId) {
  //   return request({
  //     url: `/software/member/getInvestById/${memberId}`,
  //     method: 'get',
  //   })
  // },

  //修改余额记录
  // updateInvest(member) {
  //   return request({
  //     url: `/software/member/updateInvest`,
  //     method: 'post',
  //     data: member,
  //   })
  // },

  //删除余额记录
  // deleteInvest(cusId) {
  //   return request({
  //     url: `/software/member/deleteInvest/${cusId}`,
  //     method: 'delete',
  //   })
  // },
}
