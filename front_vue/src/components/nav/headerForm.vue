<template>
  <div class="v-container">
    <div class="header-left">
      <h3 class="title-logo" @click="moveIntro">Test Project</h3>
    </div>

    <div class="header-center">
      <button @click="moveGame">Move to Game</button><br>
      <button @click="moveChat">Move to Chat</button><br>
      <button @click="moveBoard">Move to Board</button><br>
    </div>

    <div class="header-right">
      <div class="userInfoDiv" v-if="isAuthenticated">
        <p class="userNm">유저 : {{getUserData.userNm}}</p>
        <button @click="getUserInfo">유저 데이터 확인</button>
        <button @click="doLogout">logout</button>
      </div>
      <div class="userInfoDiv" v-else>
        <button @click="moveLogin">Move to Login</button><br>
        <button @click="moveSignUp">Move to SignUp</button><br>
      </div>
    </div>
  </div>
</template>

<script>
import {getUserInfo} from '@/api/backend'
import {isEmpty} from "@/common/commonUtil";

export default {
  name: "headerForm",
  data() {
    return {
      isAuthenticated: isEmpty((localStorage.getItem('userToken'))) == false ? true : false,
      userData: {

      }
    }
  },
  computed : {
    getUserData(){
     return JSON.parse(localStorage.userData);
    }
  },
  methods: {
    getUserInfo() {
      getUserInfo().then(response => {
        console.log(response.data.responseDto);
      }).catch(error => {
        console.log('error: ' + error);
      });
    },
    doLogout(){
      localStorage.removeItem("userToken");
      location.reload();
    },
    moveIntro(){
      this.$router.push('/');
    },
    moveGame(){
      this.$router.push('/game');
    },
    moveChat(){
      this.$router.push('/chat');
    },
    moveBoard(){
      this.$router.push('/board');
    },
    moveLogin(){
      this.$router.push('/user/login')
    },
    moveSignUp(){
      this.$router.push('/user/signUp')
    }
  }
}
</script>

<style scoped>
.v-container {
  padding: 10px 0px 10px 0px;
  margin: 10px;
  border-bottom: 1px solid #ebebeb;
  display: flex;
}

.title-logo:hover {
  cursor:pointer;
  transition: transform 0.2s linear;
  transform: scale(1.1);
}

.header-left {
  width: 20%;
  display: flex;
  margin: auto;
  justify-content: center
}

.header-center {
  width: 50%;
  display: flex;
  margin: auto;
  justify-content: center
}

.header-right {
  width: 30%;
  display: flex;
  margin: auto;
  justify-content: center
}

.userInfoDiv {
  display: flex;
  text-align: center;
}

.userNm {
  padding: 2px 4px;
  margin: 5px;
}

</style>