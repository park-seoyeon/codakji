<template>
  <div style="text-align: center">
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
              </tbody>
            </template>
          </v-simple-table>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import NoticeCard from '../../components/notice/NoticeCard.vue';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'Notice',
  components: {
    NoticeCard,
  },
  data() {
    return {
      notices: '',
    };
  },
  methods: {
  },
  created() {
    axios
      .get(`${SERVER_URL}/notice`)
      .then((response) => {
        this.notices = response.data;
      })
      .catch(() => {
        alert('서버와 통신할 수 없습니다.');
      });
  },
};
</script>

<style>
tr > th {
  font-size: 20px !important;
}
</style>
