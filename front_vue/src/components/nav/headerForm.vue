<template>
  <div class="v-container">
    <div class="header-left">
      <a class="navbar-brand" @click="moveIntro">
            <span>
              Combat Space
            </span>
      </a>
    </div>

    <div class="header-center">
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav  mx-auto ">
          <li class="nav-item active">
            <a class="nav-link" @click="moveBoard">커뮤니티 </a>
          </li>
          <li class="nav-item active">
            <a class="nav-link" @click="moveRank">랭킹 </a>
          </li>
<!--      <li class="nav-item active">
            <a class="nav-link" @click="moveStatistic">통계 </a>
          </li>-->
        </ul>
      </div>
    </div>

    <div class="header-right">
      <div class="userInfoDiv" v-if="getUserData.userNm != null ? true : false">
<!--    <p class="userNm">유저 : {{getUserData.userNm != null ? getUserData.userNm : ''}}</p>
        <button @click="getUserInfo">유저 데이터 확인</button>-->
        <button @click="doLogout">logout</button>
      </div>
      <div class="userInfoDiv" v-else>
        <button @click="moveLogin">Sign In</button>
        <button @click="moveSignUp">Sign Up</button>
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
      this.$router.push('/');
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
    moveRank(){
      this.$router.push('/rank');
    },
    moveStatistic(){
      this.$router.push('/stet');
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
  border-bottom: 1px solid #ebebeb;
  display: flex;
  background-color: black;
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
.navbar-nav .nav-item .nav-link {
  padding: 5px 20px;
  color: #ffffff;
  text-align: center;
  text-transform: uppercase;
  border-radius: 5px;
  -webkit-transition: all 0.3s;
  transition: all 0.3s;
}

.navbar-nav .nav-item:hover .nav-link, .navbar-nav .nav-item .active .nav-link {
  color: #ffbe33;
}

.navbar-brand {
  font-family: 'Dancing Script', cursive;
}

.navbar-brand span {
  font-weight: bold;
  font-size: 32px;
  color: #ffffff;
}
.navbar-brand:hover {
  cursor:pointer;
  transition: transform 0.1s linear;
  transform: scale(1.1);
}

ul {
  list-style: none;
}
ul li {
  float: left;
}
ul li:hover {
  cursor:pointer;
}

button {
  display: inline-block;
  padding: 8px 30px;
  background-color: #ffbe33;
  color: #ffffff;
  border-radius: 45px;
  -webkit-transition: all 0.3s;
  transition: all 0.3s;
  border: none;
}

button:hover {
  background-color: #e69c00;
  padding: 8px 30px;
}
</style>