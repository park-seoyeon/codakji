<template>
  <v-sheet height="300" align="center">
    <v-container fill-height fluid >
      <v-row class="fill-height" align="center" justify="center" >
        <v-col cols="12" sm="4">
          <h1 class="text--primary pt-3">화상미팅 만들기</h1>
          <v-form persistent ref="form">
            <v-text-field
              class="pl-3 pr-3"
              :rules="roomNameRules"
              :counter="20"
              v-model="room_name"
              label="미팅룸 이름"
              type="text"
              prepend-icon="mdi-account"
            ></v-text-field>
            <v-text-field
              class="pl-3 pr-3"
              :rules="roomDescRulse"
              :counter="30"
              v-model="room_description"
              label="미팅룸 설명"
              type="text"
              prepend-icon="mdi-account"
              @keypress.enter="makeRoom"
            ></v-text-field>
            <v-chip class="ma-2" color="primary" outlined pill @click="makeRoom">
              만들기
              <v-icon right>
                  mdi-account-outline
              </v-icon>
            </v-chip>
            <v-chip class="ma-2" close color="red darken-3" outlined @click="reset">
              초기화
            </v-chip>
          </v-form>
        </v-col>
      </v-row>
    </v-container>
  </v-sheet>
</template>  

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'MakeRoom',
  data: ()=>({
    room_name : '',
    room_description: '',
    roomNameRules : [
      (v) => !!v || '미팅룸 이름을 정해주세요.',
      (v) => (v && v.length <= 20) || '미팅룸 이름은 20글자를 넘으면 안됩니다',
    ],
    roomDescRulse : [
      (v) => !!v || '미팅룸에 대한 설명을 작성해주세요.',
      (v) => (v && v.length <= 30) || '미팅룸 설명은 30글자를 넘으면 안됩니다',
    ],
  }),
  methods: {
    makeRoom(){
      if(this.$refs.form.validate()){
        axios
          .post(`${SERVER_URL}/sessions/makeRoom`, {
            token : localStorage.getItem('jwt'),
            room_name : this.room_name,
            room_description : this.room_description,
          })
          .then((response) => {
            let room_number = response.data.room_number;
            this.$router
              .push({ name: 'WebMeeting' , params : {room_number : room_number}})
              .catch((error) => {
                if (error.name === 'NavigationDuplicated') {
                  location.reload();
                }
            });
          })
          .catch((error) => {
            if (error.response.status === 401) {
              alert('세션이 만료되었습니다.');
              this.$emit('expireLogin');
            } else {
              alert('서버와 통신할 수 없습니다.');
            }
        });
      }
    },
    reset(){
      this.$refs.form.reset();   
    },
  },     
}
</script>