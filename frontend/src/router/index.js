import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home.vue';
import LogIn from '@/views/Accounts/LogIn.vue';
import SignUp from '@/views/Accounts/SignUp.vue';
import FindPassword from '@/views/Accounts/FindPassword.vue';
import AllProblemRank from '@/views/AllProblemRank.vue'
import ProblemRankList from '@/views/ProblemRankList.vue'
import ProblemDetail from '@/views/Problems/ProblemDetail.vue'

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
  },
  {
    // path: '/problem/rank/detail/:problemnumber',
    path: '/problem/rank/detail',
    name: 'ProblemDetail',
    component: ProblemDetail,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;