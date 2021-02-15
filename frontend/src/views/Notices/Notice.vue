<template>
  <!--상단배너 -->
  <div style="text-align: center">
    <v-sheet color="#FFB911" height="300" light align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="banner text-center" style="font-size: 40px">
          <v-icon color="black" size="40px">mdi-bell-ring-outline</v-icon
          >공지사항
        </div>
      </v-row>
    </v-sheet>

    <!-- 공지목록 -->
    <v-container>
      <v-row>
        <v-col>
          <v-simple-table>
            <template>
              <thead>
                <tr>
                  <th class="text-left">
                    No.
                  </th>
                  <th>
                    제목
                  </th>
                  <th class="text-center">
                    등록일
                  </th>
                  <th class="text-center">
                    조회수
                  </th>
                </tr>
              </thead>
              <tbody>
                <NoticeCard
                  v-for="(notice, idx) in notices"
                  :key="idx"
                  :notice="notice"
                />
                <!-- <tr
                  v-for="(notice, idx) in notices"
                  :key="idx"
                  @click="moveNoticeDetail(notice)"
                  style="cursor: pointer;"
                >
                  <td class="text-left">{{ notice.notice_number }}</td>
                  <td class="text-left">{{ notice.notice_title }}</td>
                  <td>{{ notice.updated_at }}</td>
                  <td>{{ notice.views }}</td>
                </tr> -->
              </tbody>
            </template>
          </v-simple-table>
        </v-col>
      </v-row>
    </v-container>



    <!-- <v-row>
      <v-col cols="3"></v-col>
      <v-col col="6">
        <template v-if="noticeList" style="text-align:center">
          <v-card elevation="0">
            <v-card-title>
              <v-btn
                color="primary"
                v-if="userStat == '관리자'"
                @click="
                  isUpdate = false;
                  newNotice = true;
                "
                ><v-icon color="white">mdi-bell-plus-outline</v-icon>공지
                추가하기</v-btn
              >
              <v-spacer></v-spacer>
              <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                label="키워드 검색"
                single-line
                hide-details
              ></v-text-field>
            </v-card-title>
            <br />
            <br />
            <v-data-table
              :headers="headers"
              :items="notices"
              :search="search"
              style="cursor:pointer"
              @click:row="rowClick"
              item-key="notice_number"
              single-select
            ></v-data-table>
          </v-card>
        </template>
      </v-col>
      <v-col cols="3"></v-col>
    </v-row> -->

    <!-- 공지 상세보기 -->
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

    <!-- 공지 추가/수정 하기 모달창 -->
    <!-- <v-dialog v-model="newNotice" width="50%">
      <v-card min-height="500px" style="position: relative">
        <v-card-title class="headline yellow lighten-2">
          <img src="@/assets/img/codackji_logo.png" height="30px" />
        </v-card-title>

        <v-card-text style="font-size:1.2em;">
          <br />
          <v-textarea
            label="제목"
            auto-grow
            outlined
            rows="1"
            row-height="10"
            v-model="newNoticeTitle"
          ></v-textarea>
          <v-textarea
            label="내용"
            auto-grow
            outlined
            rows="10"
            row-height="40"
            v-model="newNoticeContent"
          ></v-textarea>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="cancelNotice" style="font-size:1.4em">
            <v-icon color="#FFB911" size="20">mdi-close-thick</v-icon>
            취소하기
          </v-btn>
          <v-btn
            v-if="isUpdate == false"
            text
            @click="uploadNotice"
            style="font-size:1.4em"
          >
            <v-icon color="#FFB911" size="20">mdi-lead-pencil</v-icon>
            등록하기
          </v-btn>
          <v-btn v-else text @click="editNotice" style="font-size:1.4em">
            <v-icon color="#FFB911" size="20">mdi-lead-pencil</v-icon>
            수정하기
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog> -->
  </div>
</template>

<script>
import axios from 'axios';
import NoticeCard from '../../components/notice/NoticeCard.vue';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'Notice',
  components: {
    NoticeCard
  },
  data() {
    return {
      notices: '',
      // search: '',
      // userStat: '',
      // userNumber: '',
      // newNotice: false,
      // noticeList: true,
      // isUpdate: false, //true면 수정하기 false면 추가하기
      // selected: [],
      // newNoticeTitle: '',
      // newNoticeContent: '',
      // headers: [
      //   { text: '#', value: 'notice_number', align: 'center', sortable: true },
      //   {
      //     text: '제목',
      //     value: 'notice_title',
      //     align: 'center',
      //     sortable: false,
      //   },
      //   {
      //     text: '등록일',
      //     value: 'created_at',
      //     align: 'center',
      //     sortable: false,
      //   },
      //   { text: '조회수', value: 'views', align: 'center', sortable: true },
      // ],
      // notices: [],
    }
  },
  methods: {

    // rowClick: function(item) {
    //   this.selected = item;

    //   //조회수 증가시키기
    //   axios
    //     .get(`${SERVER_URL}/notice/views/` + this.selected.notice_number)
    //     .then(({ data }) => {
    //       console.log(data);
    //     })
    //     .catch((error) => {
    //       alert('에러');
    //       console.log(error);
    //     });

    //   this.noticeList = false;
    //   this.listNotice();
    // },
    // listNotice() {
    //   axios
    //     .get(`${SERVER_URL}/notice`)
    //     .then((response) => {
    //       console.log(response);
    //       this.notices = response.data;
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // },
    // editNotice() {
    //   if (confirm('\n정말로 수정하시겠습니까?')) {
    //     axios
    //       .put(`${SERVER_URL}/notice`, {
    //         notice_number: this.selected.notice_number,
    //         notice_title: this.newNoticeTitle,
    //         notice_content: this.newNoticeContent,
    //       })
    //       .then((response) => {
    //         alert('\n성공적으로 글이 수정되었습니다.');
    //         console.log(response);
    //         this.noticeList = true;
    //         this.newNotice = false;
    //         this.newNoticeTitle = '';
    //         this.newNoticeContent = '';
    //         this.listNotice();
    //       })
    //       .catch((error) => {
    //         alert('error!');
    //         console.log(error);
    //       });
    //   }
    // },
    // deleteNotice() {
    //   if (confirm('\n정말로 삭제하시겠습니까?')) {
    //     axios
    //       .delete(`${SERVER_URL}/notice/` + this.selected.notice_number)
    //       .then((response) => {
    //         alert('\n공지 글이 삭제되었습니다.');
    //         console.log(response);
    //         this.noticeList = true;
    //         this.listNotice();
    //       })
    //       .catch((error) => {
    //         alert('error!');
    //         console.log(error);
    //       });
    //   }
    // },
    // uploadNotice() {
    //   axios
    //     .post(`${SERVER_URL}/notice`, {
    //       admin_user_number: this.userNumber,
    //       notice_title: this.newNoticeTitle,
    //       notice_content: this.newNoticeContent,
    //     })
    //     .then((response) => {
    //       console.log(response);
    //       alert('\n새로운 공지 사항을 추가하시겠습니까?');
    //       alert('\n공지 사항이 추가되었습니다.');
    //       this.newNotice = false;
    //       this.newNoticeTitle = '';
    //       this.newNoticeContent = '';
    //       this.listNotice();
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //       alert('error! :(');
    //     });
    // },
    // cancelNotice() {
    //   if (confirm('\n작성중인 게시글이 삭제됩니다.\n계속 하시겠습니까?')) {
    //     this.newNotice = false;
    //     this.newNoticeTitle = '';
    //     this.newNoticeContent = '';
    //   }
    // },
  },
  created() {
    axios
      .get(`${SERVER_URL}/notice`)
      .then((response) => {
        // console.log(response);
        this.notices = response.data;
      })
      .catch((error) => {
        console.log(error);
      });

    // this.listNotice();
    // axios
    //   .post(`${SERVER_URL}/user/info`, { token: localStorage.getItem('jwt') })
    //   .then((response) => {
    //     if (response.data.admin == true) {
    //       this.userStat = '관리자';
    //       console.log(response.data.admin);
    //     } else if (response.data.teach == true) {
    //       this.userStat = '선생님';
    //       console.log(response.data.teach);
    //     } else {
    //       this.userStat = '학생';
    //     }

    //     this.userNumber = response.data.user_number;
    //   })
    //   .catch((error) => {
    //     if (error.response.status === 401) {
    //       alert('세션이 만료되었습니다.');
    //       this.$emit('expireLogin');
    //     } else {
    //       console.log(error);
    //     }
    //   });
  },
};
</script>

<style>
/* .v-data-table > .v-data-table__wrapper > table > tbody > tr > th,
.v-data-table > .v-data-table__wrapper > table > thead > tr > th, */
/* .v-data-table > .v-data-table__wrapper > table > tfoot > tr > th { */
tr > th {
  font-size: 20px !important;
}
</style>
