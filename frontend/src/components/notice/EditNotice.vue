<template>
  <div>

  </div>
</template>

<script>
export default {
  name: 'EditNotice',
  data: () => {
    return {

    }
  },
  methods: {
    uploadNotice() {
      axios
        .post(`${SERVER_URL}/notice`, {
          admin_user_number: this.userNumber,
          notice_title: this.newNoticeTitle,
          notice_content: this.newNoticeContent,
        })
        .then((response) => {
          console.log(response);
          alert('\n새로운 공지 사항을 추가하시겠습니까?');
          alert('\n공지 사항이 추가되었습니다.');
          this.newNotice = false;
          this.newNoticeTitle = '';
          this.newNoticeContent = '';
          this.listNotice();
        })
        .catch((error) => {
          console.log(error);
          alert('error! :(');
        });
    },
    cancelNotice() {
      if (confirm('\n작성중인 게시글이 삭제됩니다.\n계속 하시겠습니까?')) {
        this.newNotice = false;
        this.newNoticeTitle = '';
        this.newNoticeContent = '';
      }
    },
    editNotice() {
      if (confirm('\n정말로 수정하시겠습니까?')) {
        axios
          .put(`${SERVER_URL}/notice`, {
            notice_number: this.selected.notice_number,
            notice_title: this.newNoticeTitle,
            notice_content: this.newNoticeContent,
          })
          .then((response) => {
            alert('\n성공적으로 글이 수정되었습니다.');
            console.log(response);
            this.noticeList = true;
            this.newNotice = false;
            this.newNoticeTitle = '';
            this.newNoticeContent = '';
            this.listNotice();
          })
          .catch((error) => {
            alert('error!');
            console.log(error);
          });
      }
    },
    deleteNotice() {
      if (confirm('\n정말로 삭제하시겠습니까?')) {
        axios
          .delete(`${SERVER_URL}/notice/` + this.selected.notice_number)
          .then((response) => {
            alert('\n공지 글이 삭제되었습니다.');
            console.log(response);
            this.noticeList = true;
            this.listNotice();
          })
          .catch((error) => {
            alert('error!');
            console.log(error);
          });
      }
    },
  }
}
</script>

<style>

</style>