<template>
  <div class="top">
    <div class="main">      
      <div class="main-left">
        <!-- 로고 -->
        <div class="logo" @click="clickTimeline">
           <img class="logo1" src="@/assets/images/logo1.jpg" alt="Logo">
           <img class="logo2" src="@/assets/images/logo2.jpg" height="60px" alt="Logo">
        </div>
      </div>
      <div class="main-middle">
        <!-- 검색 -->
        <div class="search">
          <SearchBar />
        </div>
      </div>
    
      <div class="main-right">
        <!-- 유저 -->
        <div class="user">      
          <!-- 로그인 했을 때 -->
          <div class="isLogin" v-if="isLogin">
          <!-- 탐색 -->
          <TimelineIcon title="Main"/>
          <QnaIcon title="QnA"/>
          <DebateIcon title="Discuss"/>   
          <span class="notifi-btn" @click="clickAlarm">
            <i class="fas fa-bell fas-bell" v-if="getNotify"></i>
            <i class="far fa-bell" v-else></i>
          </span>
          <div class="notify-table container" @scroll="handleNotificationListScroll">
            <div class="row">
              <div class="col py-2 ps-1" style="font-size:27px; font-weight:bold;">알림</div>
            </div>
            <div v-for="item,idx in getNotificationList" :key="idx">
              <NotificationList :item="item"/>
            </div>
          </div>
          <ul class="navbar-nav" >
            <li class="nav-item dropdown">
              <a
                class="nav-link"
                href="#"
                id="navbarDropdown"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <span class="imageSection">
                  <img class="profileImg" :src="imgsrc" @error="imageError = true" alt="프로필사진">
                </span>
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" @click="modifyUser">정보수정</a></li>
                <li><a class="dropdown-item" @click="goProfile">프로필페이지</a></li>
                <li><a class="dropdown-item" v-on:click="logout">로그아웃</a></li>
              </ul>
            </li>
          </ul>
          </div>
    <!-- 로그인 안했을 때 -->
          <div class="login-signup" v-if="!isLogin">
            <span @click="login" class="loginBtn">Log in </span>
            <span @click="signup" class="signupBtn"> Sign Up</span>
          </div>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
import NotificationList from '@/components/navBar/NotificationList.vue'
import axios from 'axios'
import jwt_decode from 'jwt-decode'
import TimelineIcon from './icon/TimelineIcon.vue'
import QnaIcon from './icon/QnaIcon.vue'
import DebateIcon from './icon/DebateIcon.vue'
import SearchBar from "@/components/search/SearchBar.vue";

import $ from 'jquery'
const token = sessionStorage.getItem('jwt')
const SERVER_URL = process.env.VUE_APP_SERVER_URL
let username = '';
let userpk = '';
if (token) {
  const decoded = jwt_decode(token)
  username = decoded.name
  userpk = decoded.sub
}

export default {
  components:{
    TimelineIcon,
    QnaIcon,
    DebateIcon,
    SearchBar,
    NotificationList,
  },
  data(){
    return{
      imgsrc: `${SERVER_URL}/profile/img/${userpk}`,
      page: 0,
      flag : true
    }
  },
  methods: {
    handleNotificationListScroll(e) {
      this.flag = true
      const { scrollHeight, scrollTop, clientHeight } = e.target;
      const isAtTheBottom = scrollHeight < scrollTop + clientHeight + 2;
        // 일정 한도 밑으로 내려오면 함수 실행
      if (isAtTheBottom && this.flag) {
          setTimeout(() => {
            this.handleLoadMore();          
          }, 500);
        }              
    },

    handleLoadMore() {
      this.flag = false
      axios({
        method: 'get',
        url: `${SERVER_URL}/notilist` + "?page=" + this.page,
        headers: this.getToken()
      })
      .then(res=>{
        this.page += 1
        this.$store.dispatch('createNoticationList', res.data.notificationList)
      })
      .catch(err => {
        console.log(err)
      })
    },
    
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },

    clickAlarm: function() {
      this.page = 0

      this.$store.dispatch('deleteNotify')
      this.$store.dispatch('deleteNotificationList')
      axios({
        method: 'get',
        url: `${SERVER_URL}/notilist?page=0`,
        headers: this.getToken()
      })
      .then(res=>{
        this.page += 1
        this.$store.dispatch('createNoticationList', res.data.notificationList)
      })
      .catch(err => {
        console.log(err)
      })

      var div = $('.notify-table')
      if( div.is(":visible") ){
          div.slideUp(700);
      }else{
          div.slideDown(1000);
      }
    }, 
    logout: function(){
      this.$swal.fire({
          text: "로그아웃 하시겠습니까?",
          icon: 'question',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: '네',
          cancelButtonText: '아니요'
           }).then((result) => {
             if (result.value) {
              this.$store.dispatch('logout')
              sessionStorage.removeItem("jwt");
              sessionStorage.removeItem("refresh");
              localStorage.removeItem("vuex")
              localStorage.removeItem("userPk")
              this.$router.push({name: 'login'})
             }
          })
    },
    modifyUser: function() {
      this.$router.push({'name':'passwordConfirm'})
    },
    goProfile: function() {
      localStorage.setItem("userPk",userpk)
      location.href=`/profilePage`      
    },
    login: function() {
      this.$router.push({'name':'login'})
    },
    signup: function() {
      this.$router.push({'name':'signup'})
    },
    clickTimeline: function() {
      if (document.location.href == `https://i5d205.p.ssafy.io/timeline`){
        location.reload()
      }
      else{
        this.$router.push({'name':'timeline'})
      }
    },
  },
  computed: {
    getNotify() {
      return this.$store.getters.getNotify
    },
    getNotificationList() {
      return this.$store.getters.getNotificationList
    },
    isLogin(){
      return this.$store.getters.isLogin
    },
    userName: function(){
      return username
    },
    userEmail(){
      return this.$store.getters.getEmail
    },
    creatorImage() {
        return this.imageError ? this.defaultImage : "imgsrc";
    }

  }
}
</script>

<style scoped>
.notifi-btn{
  display: flex;
  align-items: center;
  font-size:40px;
  cursor:pointer;
}
.top{
  position:sticky;
  top: 0px;
  /* sticky가 fixed랑 비슷한효과 그런데 flex의 적용을 받음 */
  background-color: rgb(62, 171, 111);
  z-index:4;
  width: 100%;
  height:70px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.logo{
  cursor: pointer;
}
.logo1{
  width: 100%;
  max-width: 192px;
  max-height: 60px;
}
.logo2{
  display: none;
}

.user{
  font-size:20px;

}

.nav-link{
    font-size: 25px;
    font-weight: 600;
    line-height: 1.0;
    color: black;
}
.dropdown-item {
  cursor: pointer;
  font-size: 1.0rem;
  line-height: 1.3;
}
.login-signup{
  color:white;
  cursor: pointer;
  font-weight: 550;
  font-size: min(2.9vw,25px);
  
}
.loginBtn{
    margin-right: 10px;
  }
.signupBtn{
    margin-right: 10px;
}

.username {
  margin-right: 15px;
}
.profileImg {
    width: 70px;
    height: 70px;
    border-radius: 75%;
}
.show{
  display: block;
}
.notify-table{
  position: absolute;
  top:70px;
  width: 380px;
  height:80vh;
  display:none;
  overflow: auto;
  background-color: white;  
  -webkit-box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.08);
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.12);
  border-radius: 6px;
}

.fas-bell{
  color:white;
  text-shadow: 0px 0px 8px rgb(102, 219, 81);
  animation-name: tada;
  animation-duration: 2s;
  animation-iteration-count: infinite;   
}

@media (max-width:640px){
  .logo1{
    display: none;
  }
  .logo2{
    display: inline;
  }
  .login-signup{
    font-weight: 550;
    /* font-size: 20px; */
    font-size: min(4.2vw,25px);
  }
  .loginBtn{
    margin-right: 10px;
  }
  .signupBtn{
    margin-right: 10px;
  }
  .profileImg {
      width: 35px;
      height: 35px;
      border-radius: 75%;
  }
  .main-left{
    flex-basis: 40% !important;
  }
  .main-middle{
    flex-basis: 10% !important;
  }
  .main-right{
    flex-basis: 40% !important;
  }
}




.profileImg {
    width: 45px;
    height: 45px;
    border-radius: 75%;
}
.main{
  width: inherit;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 5% 0 5%;
}

i {
  color: white;
  cursor: pointer;
  /* font-size: min(max(3.5vw,10px),30px); */
  font-size: 30px;
}
.main-left{
  flex-basis: 30%;
}
.main-middle{
  flex-basis: 30%;
  display: flex;
  justify-content: center;
}
.main-right{
  display: flex;
  align-items: center;
  justify-content: flex-end;
  flex-basis: 30%;
}
.isLogin{
  width: inherit;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.isLogin >:nth-last-child(n){
  margin-left:min(1.8vw, 20px);
}
</style>
