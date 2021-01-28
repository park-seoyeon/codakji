import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home.vue';
import LogIn from '@/views/Accounts/LogIn.vue';
import SignUp from '@/views/Accounts/SignUp.vue';
import FindPassword from '@/views/Accounts/FindPassword.vue';
import ProblemRankList from '@/views/ProblemRankList'

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
    path: '/problem/rank/:problemrank',
    name: 'ProblemRankList',
    component: ProblemRankList,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;