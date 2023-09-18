<template>
  <div class="signUpForm">
    <form @submit.prevent="doSignUp">
      <div class="input_div">
        ID:<input type="text" id="loginId" v-model="userData.loginId">
      </div>
      <div class="input_div">
        PW:<input type="password" id="loginPw" v-model="userData.loginPw">
      </div>
      <div class="input_div">
        NICK NAME:<input type="text" id="userNm" v-model="userData.userNm">
      </div>
      <button type="submit">회원가입</button>
    </form>
  </div>
</template>

<script>
import {signUp} from '../../api/backend'

export default {
  name: "SignUpForm",
  data() {
    return {
      userData: {
        loginId: '',
        loginPw: '',
        userNm: ''
      }
    }
  },
  methods: {
    doSignUp(){
      signUp(this.userData).then(response => {

        console.log(this.response);
        if(response.status != 200){
          alert('Error : ' + response.data.msg);
        }else{
          /**
          const userData = JSON.parse(response.data.responseDto);
          this.$store.commit('setUserData', userData);

          localStorage.setItem('userToken', response.data.jwtToken);
          console.log("userToken in localStroage : " + localStorage.getItem("userToken"));
          this.$router.push('/');
           **/
          if (window.confirm('회원가입이 완료되었습니다.\n로그인해주십시오.'))
          {
            // They clicked Yes
            this.$router.push('/');
          }
          else
          {
            // They clicked no
            this.$router.push('/');
          }
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