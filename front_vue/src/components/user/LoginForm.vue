<template>
  <div class="signUpForm">
    <form @submit.prevent="doLogin">
      <div class="input_div">
        <label for="loginId">ID </label>
        <input type="text" id="loginId" v-model="loginData.loginId">
      </div>
      <div class="input_div">
        <label for="loginPw">PW </label>
        <input type="password" id="loginPw" v-model="loginData.loginPw">
      </div>
      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<script>
import {isEmpty} from '@/common/commonUtil'
import {login} from '@/api/backend'

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
          const userData = response.data.responseDto;
          localStorage.setItem('localUserData', JSON.stringify(userData));
          this.$store.commit('callUserDataLocalStorage');


          localStorage.setItem('userToken', response.data.responseDto.jwtToken);
          this.$router.push({
            name: 'Intro',
            query: {isDoLogin: true},
          });
        }
      }).catch(error => {
        alert('로그인 실패: ' + error);
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