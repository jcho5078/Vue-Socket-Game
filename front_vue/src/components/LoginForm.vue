<template>
  <div class="loginForm">
    <form @submit.prevent="doLogin">
      <div class="input_div">
        ID:<input type="text" id="loginId" v-model="loginData.loginId">
      </div>
      <div class="input_div">
        PW:<input type="password" id="loginPw" v-model="loginData.loginPw">
      </div>
      <h5 v-if="cookie !== ''">{{this.cookie}}</h5>
      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<script>
import {login} from '../api/api'

export default {
  name: "LoginForm",
  data() {
    return {
      loginData: {
        loginId: '',
        loginPw: '',
        withCredentials: true
      },
      cookie: ""
    }
  },
  methods: {
    doLogin(){
      login(this.loginData).then(response => {

        console.log(this.response);
        if(response.status != 200){
          alert(response.status);
        }else{
          console.log(response.headers);
          console.log(response.data);
          this.$cookies.set("userCookie", response.headers.get(process.env.APP_JWT_HEADER));
          this.cookie = this.$cookies.get("userCookie");
          localStorage.setItem("userData", JSON.stringify(response.headers.get(process.env.APP_JWT_HEADER)));
          console.log(response.headers.get(process.env.APP_JWT_HEADER));
          const test = response.headers;
          console.log(test);
          console.log("userData in localStroage : " + localStorage.getItem("userData"));
        }
      });
    }
  }
}
</script>

<style scoped>
input{
  width: 120px;
  margin: 1px;
  border: 1px solid black;
}
button{
  border: 1px solid black;
  margin: 5px;
}
.input_div{
  border: 1px solid black;
  padding: 15px;
  text-align: center;
}
.loginForm{
  width: 340px;
  padding: 10px;
  margin: 0 auto;
  border: 1px solid black;
}
</style>