<template>
  <div class="animate__animated animate__fadeInUp my-4 main">
    <div
      class="article-box col-lg-8 col-md-10 col-sm-9 col-10"
      @click="clickDebate"
    >
      <div class="contentsWrap" @click="clickDebate">
        <p class="title">
          {{ this.discussCompName }} <span>{{ this.disscussCompProblem }}</span>
        </p>
        <p>{{ this.discussCompHostName }}</p>
      </div>
      <div class="btn-group wrap col-12 col-lg-12 col-ml-12">
        <div class="col box1 like-comment">
          <i class="far fa-comment-dots mx-2"></i>
          <span>{{ this.commentCount }}</span>
          <div>
            <p class="date">{{ this.discussDate }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  props: {
    discussionNo: Number,
  },
  data() {
    return {
      commentCount: "",
      discussCompHostNo: "",
      discussCompHostName: "",
      discussCompName: "",
      discussDate: "",
      disscussCompProblem: "",
      item: null,
    };
  },
  computed: {
    Content: function () {
      return this.content;
    },
    getToken() {
      const token = sessionStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },
  },
  mounted() {
    axios({
      method: "get",
      url: `${SERVER_URL}/article/discussion/${this.discussionNo}`,
      headers: this.getToken,
    })
      .then((res) => {
        const detail = res.data.articleDetail;
        this.item = res.data;
        this.commentCount = detail.commentCount;
        this.discussCompHostNo = detail.discussCompHostNo.no;
        this.discussCompHostName = detail.discussCompHostNo.name;
        this.discussCompName = detail.discussCompName;
        this.disscussCompProblem = detail.disscussCompProblem;
        const date = detail.discussDate;
        this.discussDate = this.getDate(date);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    getDate: function (date) {
      const today = new Date();
      const timeValue = new Date(date);

      const betweenTime = Math.floor(
        (today.getTime() - timeValue.getTime()) / 1000 / 60
      );
      if (betweenTime < 1) return "방금전";
      if (betweenTime < 60) {
        return `${betweenTime}분전`;
      }

      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간전`;
      }

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일전`;
      }

      return `${Math.floor(betweenTimeDay / 365)}년전`;
    },
    clickDebate: function () {
      localStorage.setItem("discussionNo", this.discussionNo);
      this.$router.push({ name: "debateDetail", params: { Page: "0" } });
    },
  },
};
</script>

<style scoped>
.article-box {
  background: white;

  border: 1px solid rgb(207, 207, 207);
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
  cursor: pointer;
}
.article-box:hover {
  box-shadow: 0 0 0px 5px rgba(62, 171, 111, 1);
}

.contentsWrap {
  box-sizing: border-box;
  padding: 12px;
  /* float: left; */
}
.title {
  color: #000;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap: break-word;
  line-height: 1.5em;
  font-size: 25px;
  font-weight: 600;
  margin: 0 0 8px;
  white-space: normal;
}
.date {
  float: right;
  font-size: 15px;
  color: rgba(0, 0, 0, 0.5);
}

.like-comment {
  text-align: end;
}
.comment {
  margin-right: 30px;
}
.article-box {
  background: white;

  border: 1px solid rgb(207, 207, 207);
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
  cursor: pointer;
}
</style>