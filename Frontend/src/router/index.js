import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '@/App.vue'
import Login from '@/views/account/login.vue'
import Signup from '@/views/account/signup.vue'
import PageNotFound from '@/views/PageNotFound.vue'
import PasswordConfirm from '@/views/account/PasswordConfirm.vue'
import ProfilePage from '@/views/profile/ProfilePage.vue'
import UserModify from '@/views/account/modify.vue'
import followPage from '@/views/profile/followPage.vue'
import TargetProfilePage from '@/views/profile/targetProfilePage'
import debateList from '@/views/debate/debateList.vue'
import CreateArticle from '@/views/createArticle/CreateArticle.vue'
import Timeline from '@/views/article/Timeline.vue'
import QnA from '@/views/article/QnA.vue'
import introduce from '@/views/profile/introduce.vue'
import uploadImg from '@/views/profile/uploadImg.vue'
import createDebate from '@/views/debate/createDebate.vue'
import debateDetail from '@/views/debate/debateDetail.vue'
import ArticleDetail from '@/components/article/ArticleDetail.vue'
import SearchProblem from '@/components/article/SearchProblem.vue'
import SearchAlgorithm from '@/components/article/SearchAlgorithm.vue'
import SearchUser from '@/components/article/SearchUser.vue'

Vue.use(VueRouter)


const routes = [
  {
    path: '/',
    name: 'home',
    component: Timeline
  },
  {
    path: '/timeline',
    name: 'timeline',
    component: Timeline
  },
  {
    path: '/QnA',
    name: 'qna',
    component: QnA
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/signup',
    name: 'signup',
    component: Signup
  },
  {
    path: '/passwordConfirm',
    name: 'passwordConfirm',
    component: PasswordConfirm,
  },
  {
    path: `/profilePage/:userPk`,
    name: 'profilePage',
    component: ProfilePage,
    props:true
  },
  {
    path: '/targetProfilePage',
    name: 'targetProfilePage',
    component: TargetProfilePage
  },
  {
    path: '/profilePage/followPage',
    name: 'followPage',
    component: followPage
  },
  {
    path: '/profilePage/introduce',
    name: 'introduce',
    component: introduce
  },
  {
    path: '/profilePage/uploadImg',
    name: 'uploadImg',
    component: uploadImg
  },
  {
    path: '/usermodify',
    name: 'usermodify',
    component: UserModify,
  },
  {
    path: '/debate',
    name: 'debate',
    component: debateList
  },
  {
    path: '/debate/createDebate',
    name: 'createDebate',
    component: createDebate
  },
  {
    path: '/debate/debateDetail',
    name: 'debateDetail',
    component: debateDetail
  },
  {
    path: '/createArticle',
    name: 'createArticle',
    component: CreateArticle
  },
  {
    path: '/articleDetail/:articleno',
    name: 'articleDetail',
    component: ArticleDetail,
    props: true
  },
  {
    path: '/searchProblem',
    name: 'searchProblem',
    component: SearchProblem,
    props: true
  },
  {
    path: '/searchAlgorithm',
    name: 'searchAlgorithm',
    component: SearchAlgorithm,
    props: true
  },
  {
    path: '/searchUser',
    name: 'searchUser',
    component: SearchUser,
    props: true
  },
  {
    path: '*',
    name: 'PageNotFound',
    component: PageNotFound
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    if (err.name !== 'NavigationDuplicated') throw err;
  });
};



export default router
