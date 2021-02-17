<template>
  <div>
    <v-sheet height="300" align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-img src="@/assets/img/banner/problem_banner.png" height="300">
            <v-container fill-height fluid>
              <v-row class="fill-height" align="center" justify="center">
                <v-col>
                  <div class="banner text-center" style="font-size:40px;">
                    화상 미팅
                  </div>
                  <div class="guide text-center" style="font-size:15px">
                    선생님, 친구들과 함께 화상으로 질문하고 토론해보아요!
                  </div>
                </v-col>
              </v-row>
            </v-container>
          </v-img>
        </div>
      </v-row>
    </v-sheet>
    <v-container fluid>
      <MakeRoom />
    </v-container>
    <!--
            <div>
                <button @click="makeRoom()">방 만들기</button>
            </div>
            -->
    <v-container>
      <v-row v-if="meetingRooms" class="mt-4 mb-4">
        <RoomCard v-for="(room, idx) in meetingRooms" :key="idx" :room="room" />
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import RoomCard from '@/components/meeting/RoomCard';
import MakeRoom from '@/components/meeting/MakeRoom';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

//const VUE_APP_SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'MeetingRoomList',
  data: () => {
    return {
      meetingRooms: '',
    };
  },
  components: {
    MakeRoom,
    RoomCard,
  },
  methods: {
    getListMeetingRoom() {
      axios
        .post(`${SERVER_URL}/meeting/all`, localStorage.getItem('jwt'))
        .then((response) => {
          this.meetingRooms = response.data;
          console.log(response.data);
        })
        .catch((error) => {
          if (error.response.status === 401) {
            alert('세션이 만료되었습니다.');
            this.$emit('expireLogin');
          } else {
            console.log(error);
          }
        });
    },
  },
  created() {
    this.getListMeetingRoom();
  },
};
</script>
