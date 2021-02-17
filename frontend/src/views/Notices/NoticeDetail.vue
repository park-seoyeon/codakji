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

        <v-btn text @click="moveAllNotices" style="font-size:1.4em">
          <v-icon color="#FFB911" size="40">mdi-arrow-left-box</v-icon>
          목록으로
        </v-btn>
      </v-card>
    </v-container>

    <!-- <div v-if="!noticeList" style="width:60%; margin-left:20%">
      <div style="font-size:1.4em">
        <br />
        <v-card>
          <v-card-text style="font-size:1em; background-color:#CBE5FD">
            <v-row>
              <v-col cols="2">#{{ this.selected.notice_number }}</v-col>
              <v-col cols="4">제목: {{ this.selected.notice_title }}</v-col>
              <v-col cols="1"></v-col>
              <v-col cols="3"
                >등록일: {{ this.selected.created_at.substring(0, 10) }}</v-col
              >
              <v-col cols="2">조회수: {{ this.selected.views + 1 }}</v-col>
            </v-row>
          </v-card-text>

          <v-divider></v-divider>
          <v-card-text
            ><br /><br />
            <div>
              <span v-html="selected.notice_content"></span>
            </div>

            <br /><br /><br /><br />
          </v-card-text>
        </v-card>
      </div>
      <br />
      <div v-if="userStat == '관리자'">
        <v-btn text @click="deleteNotice" style="font-size:1.4em">
          <v-icon color="#555555" size="40">mdi-delete-outline</v-icon>
          삭제하기
        </v-btn>
        <v-btn
          text
          @click="
            isUpdate = true;
            newNotice = true;
            newNoticeTitle = selected.notice_title;
            newNoticeContent = selected.notice_content;
          "
          style="font-size:1.4em"
        >
          <v-icon color="#FF4D55" size="40">mdi-lead-pencil</v-icon>
          수정하기
        </v-btn>
      </div>

      <br />
      <v-btn text @click="noticeList = true" style="font-size:1.4em">
        <v-icon color="#FFB911" size="40">mdi-arrow-left-box</v-icon>
        목록으로
      </v-btn>
      <br /><br /><br />
    </div> -->
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
    // 번호를 통해서 해당 번호의 notice만 가져오기로 바꾸면 좋음
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
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style></style>
