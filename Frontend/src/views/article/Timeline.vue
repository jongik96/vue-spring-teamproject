<template>
  <div class="container">
    <TimelineWriteArticle/>
    <div>
      <div v-for="(item, idx) in articleItems" :key="idx">
        <TimelineItem
          :articleNo="item.articleNo"
          :content="item.articleContent"
        />
      </div>
      <infinite-loading @infinite="infiniteHandler" spinner="sprial">
        <div
          slot="no-more"
          style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px"
        >
          목록의 끝입니다 :)
        </div>
      </infinite-loading>
    </div>
  </div>
</template>

<script>
import TimelineItem from "@/components/article/TimelineItem.vue";
import TimelineWriteArticle from "@/components/article/TimelineWriteArticle.vue";
import axios from "axios";
import InfiniteLoading from "vue-infinite-loading";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const token = sessionStorage.getItem('jwt')

export default {
  components: { TimelineItem, InfiniteLoading ,TimelineWriteArticle},
  data: function () {
    return {
      articleItems: [],
      page: 0,
      isLogin: false,
    };
  },
  computed: {
    getToken() {
      const token = sessionStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },
  },

  methods: {
    infiniteHandler($state) {
      if(token){
        axios({
          method: "get",
          url: `${SERVER_URL}/main` + "?page=" + this.page,
          headers: this.getToken,
        })
          .then((res) => {
            setTimeout(() => {
              if (res.data.article.length) {
                this.articleItems = this.articleItems.concat(res.data.article);
                $state.loaded();
                this.page += 1;
                //console.log("after", this.page)
                // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
                if(res.data.article.length / 10 < 1) {
                  $state.complete()
                }
              } else {
                // 끝 지정(No more data)
                $state.complete();
              }
            }, 1000);
          })
          .catch((err) => {
            console.error(err);
          });
      }
      else{
        axios({
          method: "get",
          url: `${SERVER_URL}/articlelist/article` + "?page=" + this.page,
        })
          .then((res) => {
            setTimeout(() => {
              if (res.data.article.length) {
                this.articleItems = this.articleItems.concat(res.data.article);
                $state.loaded();
                this.page += 1;
                //console.log("after", this.page)
                // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
                if(res.data.article.length / 10 < 1) {
                  $state.complete()
                }
              } else {
                // 끝 지정(No more data)
                $state.complete();
              }
            }, 1000);
          })
          .catch((err) => {
            console.error(err);
          });
      }
    },
  },
};
</script>

<style scoped>
.isNotLogin{
  display: flex;
  justify-content: center;
  font-size: 40px;
}

</style>