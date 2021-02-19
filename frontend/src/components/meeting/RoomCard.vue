<template>
  <v-col cols="3" >
    <v-card id="roomcard" @click="checkRoom(room.room_number)">
      <div>
        <v-sheet color="#FFCD12" dark>
          <v-card-text align="right" class="pa-1 guide">{{ room.room_number }}</v-card-text>
        </v-sheet>
        <v-sheet color="#F6F6F6">
          <v-card-title primary-title class="justify-center"> {{room.room_name}}</v-card-title>
        </v-sheet>
        <v-card-text align="center">{{ room.room_description }}</v-card-text>
        <v-card-text align="center">방장 : {{ room.room_publisher_name }}<br>
            인원 : {{ room.room_people_cnt}}
        </v-card-text>
      </div>
    </v-card>
  </v-col>
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  props: {
    room: Object,
  },
  methods: {
    checkRoom(room_number){
      axios
      .post(`${SERVER_URL}/sessions/checkRoom`,{
        room_number: room_number,
        user_token : localStorage.getItem('jwt')
      })  
      .then((response) => {
        if(response.data === "success"){
          this.moveRoom(room_number);
        } else {
          alert("존재하지 않는 미팅룸입니다");
          location.reload();
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          alert('세션이 만료되었습니다.');
          this.$emit('expireLogin');
        } else {
          alert('서버와 통신할 수 없습니다.');
        }
    });
    },
    moveRoom(room_number) {
      this.$router
        .push({ name: 'WebMeeting',  params : {room_number : room_number}})
        .catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
    },
  },
};
</script>