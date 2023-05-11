<template>
  <div class="loginForm">
    <form method="post" enctype="application/x-www-form-urlencoded">
      <div class="input_div">
        ID:<input type="text" id="loginId">
      </div>
      <div class="input_div">
        PW:<input type="password" id="loginPw">
      </div>
      <button type="submit" @submit.prevent="doLogin">로그인</button>
    </form>
  </div>
</template>

<script>
import {login} from '../api/api'

export default {
  name: "LoginForm",
  data() {
    return {

    }
  },
  methods: {
    doLogin(){
      login().then(response => {
        console.log(this.response);
        if(response.status != 200){
          alert(response.status);
        }else{
          console.log(response.headers);
          console.log(response.data);
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