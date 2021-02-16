import Vue from 'vue';
import VueRouter from 'vue-router';

import Home from '@/views/Home.vue';
import LogIn from '@/views/Accounts/LogIn.vue';
import SignUp from '@/views/Accounts/SignUp.vue';
import FindPassword from '@/views/Accounts/FindPassword.vue';
import UserProfile from '@/views/Accounts/UserProfile.vue'

import AllProblemRank from '@/views/Problems/AllProblemRank.vue'
import ProblemRankList from '@/views/Problems/ProblemRankList.vue'
import ProblemDetail from '@/views/Problems/ProblemDetail.vue'
import SolveProblemPage from '@/views/Problems/SolveProblemPage'
import SolveResult from '@/views/Problems/SolveResult'

import Notice from '@/views/Notices/Notice.vue'
import NoticeDetail from '@/views/Notices/NoticeDetail'
import CoFAQ from '@/views/Notices/CoFAQ.vue'

import Introduce from '@/views/About/Introduce.vue'

import LearnBasic from '@/views/Basics/LearnBasic.vue'
import Basic from '@/views/Basics/Basic.vue'

import WebMeeting from '@/views/Meetings/WebMeeting.vue'
import MeetingRoomList from '@/views/Meetings/MeetingRoomList.vue'


Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/accounts/login',
    name: 'LogIn',
    component: LogIn,
  },
  {
    path: '/accounts/signup',
    name: 'SignUp',
    component: SignUp,
  },
  {
    path: '/accounts/findpassword',
    name: 'FindPassword',
    component: FindPassword,
  },
  {
    path: '/problem/rank',
    name: 'AllProblemRank',
    component: AllProblemRank,
  },
  {
    path: '/problem/rank/:problemrank',
    name: 'ProblemRankList',
    component: ProblemRankList,
    props: true,
  },
  {
    path: '/problem/problem_:problemnumber/detail',
    name: 'ProblemDetail',
    component: ProblemDetail,
  },
  {
    // path: '/user/:name', 단, post로 보내지는 데이터는 이메일
    // path: '/user/:email', get 요청 이 경우 login 성공 후 localstorage에 email 저장 (백에서 보내줄 필요 X)
    path: '/user',
    name: 'UserProfile',
    component: UserProfile,
  },
  {
    path: '/problem/problem_:problemnumber/solve',
    name: 'SolveProblemPage',
    component: SolveProblemPage,
  },
  {
    path: '/problem/problem_:problemnumber/result_:resultnumber',
    name: 'SolveResult',
    component: SolveResult,
  },
  {
    path: '/codackji/notices',
    name: 'Notice',
    component: Notice,
  },
  {
    path: '/codackji/notices/notice:noticenumber',
    name: 'NoticeDetail',
    component: NoticeDetail,
  },
  {
    path: '/codackji/CoFAQ',
    name: 'CoFAQ',
    component: CoFAQ,
  },
  {
    path: '/about/introduce',
    name: 'Introduce',
    component: Introduce,
  },
  {
    path: '/basic',
    name: 'LearnBasic',
    component: LearnBasic,
  },
  {
    path: '/basic/:topic',
    name: 'Basic',
    component: Basic,
  },
  {
    path: '/meeting/room/:room_number',
    name: 'WebMeeting',
    component: WebMeeting,
  },
  {
    path: '/meeting/list',
    name: 'MeetingRoomList',
    component: MeetingRoomList,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;