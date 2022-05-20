import request from '@/utils/request'

export default {
  pageClothsList(page, limit, clothQuery) {
    return request({
      url: `/software/cloths/getClothsListPage/${page}/${limit}`,
      method: 'post',
      data: clothQuery,
    })
  },

  //添加cloth
  addCloths(cloth) {
    return request({
      url: `/software/cloths/addCloths`,
      method: 'post',
      data: cloth,
    })
  },

  //修改cloth
  updateCloths(cloth) {
    return request({
      url: `/software/cloths/updateCloths`,
      method: 'post',
      data: cloth,
    })
  },

  //删除
  deleteCloth(id) {
    return request({
      url: `/software/cloths/deleteCloths/${id}`,
      method: 'delete',
    })
  },

  //获取clothById
  getClothsById(id) {
    return request({
      url: `/software/cloths/getClothsById/${id}`,
      method: 'get',
    })
  },
}
