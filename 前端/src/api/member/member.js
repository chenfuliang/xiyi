import request from '@/utils/request'

export default {
  getMemberListPage(current, limit, query) {
    return request({
      url: `/software/member/getMemberListPage/${current}/${limit}`,
      method: 'post',
      data: query,
    })
  },

  //添加会员
  addMember(cusId) {
    return request({
      url: `/software/member/addMember/${cusId}`,
      method: 'get',
    })
  },

  //根据id查询会员
  getMemberById(memberId) {
    return request({
      url: `/software/member/getMemberById/${memberId}`,
      method: 'get',
    })
  },

  //修改会员
  updateMember(member) {
    return request({
      url: `/software/member/updateMember`,
      method: 'post',
      data: member,
    })
  },

  //删除会员
  deleteMember(cusId) {
    return request({
      url: `/software/member/deleteMember/${cusId}`,
      method: 'delete',
    })
  },
}
