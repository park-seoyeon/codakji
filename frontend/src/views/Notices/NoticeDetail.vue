<template>
  <div>
    <v-sheet height="300" align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-img src="@/assets/img/banner/intro_banner.png" height="300">
            <v-container fill-height fluid>
              <v-row class="fill-height" align="center" justify="center">
                <v-col>
                  <div class="banner text-center" style="font-size:40px;">
                    공지사항
                  </div>
                  <p class="guide text-center" style="font-size:15px; line-height: 25px;">
                    코딱지에서 알려주는 여러 공지사항들을 확인하세요
                  </p>
                </v-col>
              </v-row>
            </v-container>
          </v-img>
        </div>
      </v-row>
    </v-sheet>
    <v-container>
      <v-card class="pb-10">
        <v-card-text>
          <v-row>
            <v-col cols="2">#{{ notice.notice_number }}</v-col>
            <v-col cols="4">제목: {{ notice.notice_title }}</v-col>
            <v-col cols="1"></v-col>
            <v-col cols="3"> 등록일: {{ notice.created_at }} </v-col>
            <v-col cols="2">조회수: {{ notice.views }}</v-col>
          </v-row>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-text class="my-10">
          <div>
            <span v-html="notice.notice_content"></span>
          </div>
        </v-card-text>
      </v-card>
      <div align="right" class="mt-3 mr-2">
        <v-chip color="indigo darken-1" outlined @click="moveAllNotices" style="font-size:1.2em">
          <v-icon size="20">mdi-arrow-left-box</v-icon>
          목록으로
        </v-chip>
      </div>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'NoticeDetail',
  data: () => {
    return {
      notice: '',
    };
  },
  methods: {
    moveAllNotices() {
      this.$router.push({ name: 'Notice' });
    },
  },
  created() {
    axios
      .get(`${SERVER_URL}/notice`)
      .then((response) => {
        for (let i = 0; i < response.data.length; i++) {
          if (response.data[i].notice_number == this.$route.params.noticenumber) {
            this.notice = response.data[i];
            return;
          }
        }
      })
      .catch(() => {
        alert('서버와 통신할 수 없습니다.');
      });
  },
};
</script>

<style></style>
