<template>
  <v-row>
    {{ checkUser() }}
    <v-col class="mt-1" style="border-bottom:solid 1px #e6e6e6;">
      <span style=" float:right; font-size:10px;">
        {{ comment.updated_at }}
      </span>
      <div style="font-size:13px; color:#5C6BC0;" class="mb-1">
        {{ comment.name }}
      </div>
      <div class="mb-2" v-if="isUpdate">
        <v-text-field v-model="content"></v-text-field>
      </div>
      <div class="mb-2" v-else>
        {{ contentOrigin }}
      </div>
      <div style=" float:right;" v-if="isUser">
        <div v-if="isUpdate">
          <v-btn plain color="indigo lighten-1" @click="updateComment()">확인</v-btn>
          <v-btn plain color="red lighten-1" @click="pushCancel()">취소</v-btn>
        </div>
        <div v-else>
          <v-btn plain color="indigo lighten-1" @click="pushUpdate()">수정</v-btn
          ><v-btn plain color="red lighten-1" @click="deleteComment()">삭제</v-btn>
        </div>
      </div>
      <div style=" float:right;" v-else>
        <v-btn outlined plain small color="red lighten-1" @click="pushLike()"
          ><v-icon size="15" class="mr-2">mdi-thumb-up</v-icon>{{ comment.likes }}</v-btn
        >
      </div>
    </v-col>
  </v-row>
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  props: {
    comment: Object,
  },
  data: () => {
    return {
      isUser: false,
      isUpdate: false,
      content: '',
      contentOrigin: '',
    };
  },
  created() {
    this.content = this.comment.comment_content;
  },
  methods: {
    checkUser() {
      if (this.comment.user_number == localStorage.getItem('user_number')) {
        this.isUser = true;
      } else {
        this.isUser = false;
      }
      this.contentOrigin = this.comment.comment_content;
    },
    pushUpdate() {
      this.isUpdate = true;
    },
    pushCancel() {
      this.isUpdate = false;
      this.content = this.contentOrigin;
    },
    updateComment() {
      if (confirm('정말 수정하시겠습니까?')) {
        console.log(this.content);
        axios
          .put(`${SERVER_URL}/problem/comment`, {
            token: localStorage.getItem('jwt'),
            comment_number: this.comment.comment_number,
            comment_content: this.content,
            likes: this.comment.likes,
          })
          .then(({ data }) => {
            if (data === 'success') {
              location.reload();
            }
          })
          .catch(error => {
            if (error.response.status === 401) {
              alert("세션이 만료되었습니다.");
              this.$emit("expireLogin");
            } else {
              console.log(error);
            }
          });
      }
    },
    deleteComment() {
      if (confirm('정말 삭제하시겠습니까?')) {
        axios
          .delete(`${SERVER_URL}/problem/comment`, {
            data: {
              token: localStorage.getItem('jwt'),
              comment_number: this.comment.comment_number,
            },
          })
          .then(({ data }) => {
            if (data === 'success') {
              location.reload();
            }
          })
          .catch(error => {
            if (error.response.status === 401) {
              alert("세션이 만료되었습니다.");
              this.$emit("expireLogin");
            } else {
              console.log(error);
            }
          });
      }
    },
    pushLike() {
      axios
        .put(`${SERVER_URL}/problem/comment/likes`, {
          token: localStorage.getItem('jwt'),
          comment_number: this.comment.comment_number,
          likes: this.comment.likes + 1,
        })
        .then(({ data }) => {
          if (data === 'success') {
            location.reload();
          }
        })
        .catch(error => {
          if (error.response.status === 401) {
            alert("세션이 만료되었습니다.");
            this.$emit("expireLogin");
          } else {
            console.log(error);
          }
        });
    },
  },
};
</script>

<style></style>
