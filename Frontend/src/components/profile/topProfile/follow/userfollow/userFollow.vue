<template>
    <div>
      <followListImg class="followlist" v-for="(item,index) in following" :key="index" v-bind:name="item.name" v-bind:no="item.no" v-bind:followState="item.followState"/>
      <infinite-loading @infinite="infiniteHandler" spinner="sprial">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>
    </div>
</template>

<script>
import followListImg from '@/components/profile/topProfile/follow/userfollow/followListImg.vue'
import jwt_decode from "jwt-decode";
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios';
const token = sessionStorage.getItem("jwt");
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
}
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  components:{
    followListImg,
    InfiniteLoading
  },
  data(){
    return {
      following:[],
      page:0,
      pk: '',
      myPage: '',
      isLogin: '',
    }
  },
  created: function() {
      const userPk = localStorage.getItem("userPk")
      if(userPk){
          this.pk = userPk
          this.myPage = false
      }else{
          this.pk = userpk
          this.myPage = true
      }
  },
  computed: {
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
	},
  methods:{
      
    infiniteHandler($state) {
      axios({
      method: 'get',
      url: `${SERVER_URL}/profile/${this.pk}/memfollowings`+"?page=" + (this.page),
      headers: this.getToken,
      }).then(res => {
        setTimeout(() => {  
          if(res.data.length) {
            this.following = this.following.concat(res.data)
            $state.loaded()
            this.page += 1
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
            if(res.data.length / 10 < 1) {
              $state.complete()
            }
          } else {
              // 끝 지정(No more data)
            $state.complete()
          }
        }, 1000)
      }).catch(err => {
        console.error(err);
      })
    },
  },

}
</script>

<style scoped>

</style>