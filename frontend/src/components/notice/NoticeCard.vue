<template>
  <tr
    style="cursor: pointer;"
    @click="moveNoticeDetail(notice)"
  >
    <td class="text-left">{{ notice.notice_number }}</td>
    <td class="text-left">{{ notice.notice_title }}</td>
    <td>{{ notice.updated_at }}</td>
    <td>{{ notice.views }}</td>
  </tr>
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'NoticeCard',
  props: {
    notice: Object,
  },
  methods: {
    moveNoticeDetail(notice) {
      // 조회수 증가 axios
      axios
        .get(`${SERVER_URL}/notice/views/` + notice.notice_number)
        .then(() => {
          this.$router.push({
            name: 'NoticeDetail',
            params: {
              noticenumber: notice.notice_number
            }
          })
        })
        .catch((error) => {
          alert('서버와 통신할 수 없습니다.');
          console.log(error);
        });

    },
  }
}
</script>

<style>

</style>