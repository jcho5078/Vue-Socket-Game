<template>
  <div class="v-container">
    <div class="header-left">
      <h3 class="title-logo" @click="moveIntro">Test Project</h3>
    </div>

    <div class="header-center">
<!--      <button @click="moveGame">Move to Game</button>-->
      <button @click="moveBoard">게시판</button>
    </div>

    <div class="header-right">
      <div class="userInfoDiv" v-if="getUserData.userNm != null ? true : false">
        <p class="userNm">유저 : {{getUserData.userNm != null ? getUserData.userNm : ''}}</p>
        <button @click="getUserInfo">유저 데이터 확인</button>
        <button @click="doLogout">logout</button>
      </div>
      <div class="userInfoDiv" v-else>
        <button @click="moveLogin">Move to Login</button>
        <button @click="moveSignUp">Move to SignUp</button>
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
      test: JSON.stringify(this.$store.state.userData)
    }
  },
  computed : {
    getUserData(){
      const userData = this.$store.state.userData;
      return isEmpty(userData) ? {
        "userNo": null,
        "loginId": null,
        "loginPw":null,
        "userNm": null,
        "regDt":null,
        "lastLoginDt": null,
        "jwtToken": null,
        "valid": null
      } : userData;
    },
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
      this.$store.commit('resetUserData');
      location.reload();
    },
    moveIntro(){
      this.$router.push('/');
    },
    moveGame(){
      this.$router.push('/game');
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