<template>
  <!-- flex만으로 구현이 가능할 지 확인 중 -->
  <!-- <div my-auto>
    <div class="d-flex flex-column align-center mt-auto mb-auto">
      <div>
        <v-img
          lazy-src="https://picsum.photos/id/11/10/6"
          max-height="350"
          max-width="450"
          src="https://picsum.photos/id/11/500/300"
        ></v-img>
      </div>
      <div>
        여기
      </div>
    </div>
  </div> -->
  <v-container fill-height fluid>
    <v-row no-gutters justify="center" align="center">
      <div class="d-flex flex-column">
        <div class="d-flex justify-space-between">
          <v-img
            max-height="350"
            max-width="450"
            :src="'/img/profile/' + result.user_number + '.png'"
          ></v-img>
          <div class="d-flex flex-column justify-space-between align-start ml-5 py-5">
            <h1>이름 : {{ result.name }}</h1>
            <h1>이메일 : {{ result.email }}</h1>
            <h1>SNS 연동 여부 : {{ userInfo.sns }}</h1>
            <div>
              <v-btn>정보수정</v-btn>
              <v-btn>비밀번호수정</v-btn>
            </div>
          </div>
        </div>
        
        <h2 class="d-flex flex-column align-start mt-5">
          푼 문제들
        </h2>
        <hr>
        <v-virtual-scroll
          height="150"
          item-height="50"
          :items="solvedProblems"
        >
          <template v-slot="{ item }">
            <v-list-item :key="item.id">
              <v-list-item-content>
                <v-list-item-title>
                  <strong>{{ item.title }}. {{ item.content }} </strong>
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-divider></v-divider>
          </template>
        </v-virtual-scroll>
      </div>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'UserProfile',
  data: () => {
    return {
      userInfo: {
        sns: '연동여부야',
      },
      result: null,
      solvedProblems: [
        {id: '1', title: '문제1', content: '내용1 이 이렇게 적혀있습니다.'},
        {id: '2', title: '문제2', content: '내용2 이 이렇게 적혀있습니다.'},
        {id: '3', title: '문제3', content: '내용3 이 이렇게 적혀있습니다.'},
        {id: '4', title: '문제4', content: '내용4 이 이렇게 적혀있습니다.'},
        {id: '5', title: '문제5', content: '내용5 이 이렇게 적혀있습니다.'},
      ]
    }
  },
  methods: {
    setToken() {
      const token = localStorage.getItem("jwt")
      if (token) {
        console.log("임시로 작성한 메시지")
        return token;
      }
    }
    // 1. 현재 유저의 정보를 가져오는 함수 :email로 axios get
    // 2. 현재 유저의 푼 문제들 가져오기 함수 setToken으로 토큰 획득 후 axios post
    // 3. 정보수정이나 비밀번호 수정을 눌렀을 때 비밀번호 재입력하는 모달 창 띄우기 함수
    // 4. 3.을 부르고 정보수정으로 보내는 함수
    // 5. 3.을 부르고 비밀번호 수정으로 보내는 함수 혹은 비밀번호 수정 모달 창 띄우기
  },
  created() {
    // methods에 있는 유저의 푼 문제들 가져오기 함수 쓰기
    const userKey = {
      'token': this.setToken()
    }

    // console.log(userKey);

    axios.post(`${SERVER_URL}/user/info`, userKey)
    .then(response => {
      this.result = response.data;
      if(this.result.oauth==null)
        this.userInfo.sns = 'X'
      else
        this.userInfo.sns = 'O'
    })
    .catch(error => {
      console.log(error);
    })
  }
}
</script>

<style>

</style>