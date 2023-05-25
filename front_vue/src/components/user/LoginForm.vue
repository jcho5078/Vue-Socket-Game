<template>
  <div class="signUpForm">
    <form @submit.prevent="doLogin">
      <div class="input_div">
        ID:<input type="text" id="loginId" v-model="loginData.loginId">
      </div>
      <div class="input_div">
        PW:<input type="password" id="loginPw" v-model="loginData.loginPw">
      </div>
      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<script>
import {isEmpty} from '@/common/commonUtil'
import {login} from '@/api/api'

export default {
  name: "LoginForm",
  data() {
    return {
      loginData: {
        loginId: '',
        loginPw: ''
      }
    }
  },
  methods: {
    doLogin(){
      login(this.loginData).then(response => {

        console.log(response);
        if(response.status != 200){
          alert('Error : ' + response.status);
        }else if(response.status == 200
            && (isEmpty(response.data.responseDto.toString()) || isEmpty(response.data.responseDto.jwtToken))){
          alert('ID, PW가 틀립니다.');
        }else{
          console.log(response.data.responseDto.jwtToken);
          localStorage.setItem('userData', JSON.stringify(response.data.responseDto));
          localStorage.setItem('userToken', response.data.responseDto.jwtToken);
          console.log("userToken in localStroage : " + localStorage.getItem("userToken"));
          this.$router.push('/');
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
.signUpForm{
  width: 340px;
  padding: 10px;
  margin: 0 auto;
  border: 1px solid black;
}
</style>