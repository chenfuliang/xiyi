import request from '@/utils/request'

export default {
  uploadImage(data) {
    return request({
      url: `http://localhost:9002/eduoss/fileoss`,
      method: 'post',
      data: data,
    })
  },
}
