<template>
  <div class="row">
    <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
    <div class="col-lg-6 col-md-10 col-sm-9 col-10 content">
      <div class="form-wrapper form-wrapper-sm">
        <span class="header">회원정보수정</span>
        <form class="form">
          <div>
            <label for="email" align="left">E-mail </label>
            <input id="email" type="text" v-model="email" disabled />
          </div>
          <div>
            <label for="password" align="left">비밀번호 </label>
            <input id="password" type="password" v-model="form.password" />
            <p class="validation-text">
              <span class="warning" v-if="!passwordLength">
                비밀번호는 10자 이상 20자 이하로 입력해주세요.
              </span>
            </p>
          </div>
          <div>
            <label for="passwordConfirm" align="left">비밀번호 확인 </label>
            <input
              id="passwordConfirm"
              type="password"
              v-model="passwordConfirm"
            />
            <p class="validation-text">
              <span class="warning" v-if="!isPasswordValid">
                Please check the password
              </span>
            </p>
          </div>
          <div>
            <label for="name" align="left">닉네임</label>
            <input id="name" type="text" v-model="form.name" />
            <p class="validation-text">
              <span class="warning" v-if="!nameLength">
                닉네임은 2자이상 10자 이하로 입력해주세요
              </span>
            </p>
          </div>
          <div class="checkbox" align="left">
            <details>
              <summary for="use_language">
                선호 언어 <i class="fas fa-chevron-down"></i>
              </summary>
              <ul>
                <li>
                  Java<input
                    class="checkbox-check"
                    type="checkbox"
                    value="Java"
                    id="flexCheckDefault"
                    v-model="form.use_language"
                  />
                </li>
                <li>
                  Python<input
                    class="checkbox-check"
                    type="checkbox"
                    value="Python"
                    id="Python"
                    v-model="form.use_language"
                  />
                </li>
                <li>
                  C<input
                    class="checkbox-check"
                    type="checkbox"
                    value="C"
                    id="C"
                    v-model="form.use_language"
                  />
                </li>
                <li>
                  C++<input
                    class="checkbox-check"
                    type="checkbox"
                    value="C++"
                    id="C++"
                    v-model="form.use_language"
                  />
                </li>
                <li>
                  JavaScript<input
                    class="checkbox-check"
                    type="checkbox"
                    value="JavaScript"
                    id="JavaScript"
                    v-model="form.use_language"
                  />
                </li>
                <li>
                  Ruby<input
                    class="checkbox-check"
                    type="checkbox"
                    value="Ruby"
                    id="Ruby"
                    v-model="form.use_language"
                  />
                </li>
                <li>
                  Go<input
                    class="checkbox-check"
                    type="checkbox"
                    value="Go"
                    id="Go"
                    v-model="form.use_language"
                  />
                </li>
              </ul>
            </details>
          </div>
          <div class="checkbox" align="left">
            <details>
              <summary for="problem_site">
                선호 알고리즘사이트 <i class="fas fa-chevron-down"></i>
              </summary>
              <ul>
                <li>
                  백준<input
                    class="checkbox-check"
                    type="checkbox"
                    value="boj"
                    id="boj"
                    v-model="form.problem_site"
                  />
                </li>
                <li>
                  정보올림피아드<input
                    class="checkbox-check"
                    type="checkbox"
                    value="jungol"
                    id="jungol"
                    v-model="form.problem_site"
                  />
                </li>
                <li>
                  프로그래머스<input
                    class="checkbox-check"
                    type="checkbox"
                    value="programmers"
                    id="programmers"
                    v-model="form.problem_site"
                  />
                </li>
                <li>
                  SWEA<input
                    class="checkbox-check"
                    type="checkbox"
                    value="swea"
                    id="swea"
                    v-model="form.problem_site"
                  />
                </li>
                <li>
                  알고스팟<input
                    class="checkbox-check"
                    type="checkbox"
                    value="algospot"
                    id="algospot"
                    v-model="form.problem_site"
                  />
                </li>
                <li>
                  코드포스<input
                    class="checkbox-check"
                    type="checkbox"
                    value="codeforce"
                    id="codeforce"
                    v-model="form.problem_site"
                  />
                </li>
                <li>
                  엣코더<input
                    class="checkbox-check"
                    type="checkbox"
                    value="atcoder"
                    id="atcoder"
                    v-model="form.problem_site"
                  />
                </li>
              </ul>
            </details>
          </div>
        </form>
        <div>    
            <button @click="deleteUser" class="btn">
                회원탈퇴
            </button>
            <button
              :disabled="!passwordLength || !passwordConfirm || !nameLength"
              @click="submitForm"
              class="btn"
            >
              수정
            </button>
            <button @click="gotoBack" class="btn">취소</button>
          </div>
      </div>
    </div>
  </div>
</template>

<script>
import { validatePassword } from "@/utils/passwordConfirm.js";
import axios from "axios";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
let userpk = localStorage.getItem("userPk");

export default {
  data() {
    return {
      email: "",
      form: {
        name: "",
        password: "",
        use_language: [],
        problem_site: [],
      },
      passwordConfirm: "",
    };
  },
  created: function () {
    const token = sessionStorage.getItem("jwt");
    if (!token) {
      this.$router.push({ name: "login" });
    }
    axios({
      method: "get",
      url: `${SERVER_URL}/member/modify`,
      headers: this.getToken,
    })
    .then((res) => {
      this.email = res.data.member.email;
      this.form.name = res.data.member.name;

      this.form.use_language = res.data.member.useLanguageLike;
      this.form.problem_site = res.data.member.problemSiteList;
    })
    .catch((err) => {
      console.log(err);
    });
  },
  computed: {
    isPasswordValid() {
      return validatePassword(this.form.password, this.passwordConfirm);
    },
    getToken() {
      const token = sessionStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },
    passwordLength() {
      if (this.form.password.length > 9 && this.form.password.length < 21) {
        return true;
      }
      return false;
    },
    nameLength() {
      if (this.form.name.length >= 2 && this.form.name.length < 10) {
        return true;
      }
      return false;
    },
  },
  methods: {
    submitForm: function () {
      axios({
        method: 'put',
        url: `${SERVER_URL}/member/modify`,
        data: this.form,
        headers: this.getToken
      })
      .then((res) => {
        
        if(res.data.success =='False'){
          this.$swal('이미 존재하는 닉네임입니다.')
        }else{
          this.$swal('수정이 완료되었습니다.')
          this.$router.push({ name: 'profilePage', params:userpk })
        }
      })
      .catch(err => {
        alert(err)
      })
    },

    deleteUser: function(){
      this.$swal.fire({
        text: "탈퇴하시겠습니까?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '네',
        cancelButtonText: '아니요'
      }).then((result) => {
        console.log('q1234')
        if (result.value) {
          axios({
            method: 'delete',
            url: `${SERVER_URL}/member/modify`,
            headers: this.getToken
          })
          .then(() => {
            sessionStorage.removeItem("jwt")
            this.$router.push({ name: 'login' })
          })
          .catch(err => {
            alert(err)
          })
        }
      })
    },

    gotoBack: function(){
      this.$router.push({'name':'profilePage'})
    }
    
  }
};
</script>

<style scoped>
/*--- LAYOUT ---*/
.header {
  margin-top: 15px;
  margin-right: 25px;
  font-size: 30px;
  font-weight: 550;
  text-align: right;
}
.content {
  background: white;
  -webkit-box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  border-radius: 3%;
  padding: 15px 15px;
  height: 100%;
}
.form-wrapper.form-wrapper-sm {
  max-width: 700px;
}
.form-wrapper-sm .page-header {
  padding: 0px 0 20px;
}
.form {
  width: 100%;
  height: 100%;
}
.form .validation-text {
  margin-top: -0.5rem;
  margin-bottom: 0.5rem;
  font-size: 16px;
  display: flex;
  flex-direction: row-reverse;
  justify-content: space-between;
}
.form label {
  width: 100%;
  display: block;
  margin-bottom: 0.5rem;
  font-size: 20px;
  font-weight: 600;
}
.form input,
.form textarea {
  font-family: inherit;
  font-size: 20px;
  width: 100%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
}
@media (min-width: 1440px) {
  .form input,
  .form textarea {
    font-family: inherit;
    font-size: 20px;
    width: 100%;
    border: 1px solid #dae1e7;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);
    padding: 0.5rem 0.75rem;
    margin-bottom: 1rem;
  }
}
@media (max-width: 768px) {
  .form input,
  .form textarea {
    font-family: inherit;
    font-size: 20px;
    width: 60%;
    border: 1px solid #dae1e7;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);
    padding: 0.5rem 0.75rem;
    margin-bottom: 1rem;
    margin-left: 20px;
  }
  .form label {
    width: 100%;
    display: block;
    margin-bottom: 0.5rem;
    font-size: 20px;
    font-weight: 600;
    margin-left: 20px;
  }
  summary {
    display: block; /* works in firefox */
    list-style: none; /* works in firefox */
    font-size: 20px;
    font-weight: 550;
    margin-left: 20px;
  }
}

.form div:nth-last-child(2) {
  margin-bottom: 0.5rem;
}
.btn {
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: 0.1rem;
}
.btn-duplicate {
  opacity: 0.8;
}
.btn.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.form li input {
  width: 100%;
}
ul {
  list-style: none;
}
summary {
  display: block; /* works in firefox */
  list-style: none; /* works in firefox */
  font-size: 20px;
  font-weight: 550;
}
li {
  margin-right: 10px;
  font-size: 15px;
  font-weight: 550;
}
.form li input {
  margin-left: 10px;
  width: 5%;
}
</style>