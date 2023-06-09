<template>
  <div class="v-container">
    <h4>header</h4>
    <div v-if="isAuthenticated">
      <p>유저 : {{getUserData.userNm}}</p>
      <button @click="getUserInfo">유저 데이터 확인</button>

    </div>
  </div>
</template>

<script>
import {getUserInfo} from '@/api/api'

export default {
  name: "headerForm",
  data() {
    return {
      isAuthenticated: (localStorage.userToken !== 'null' && localStorage.userToken !== null),
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
    }
  }
}
</script>

<style scoped>
.v-container {
  padding: 10px 0px 10px 0px;
  margin: 10px;
  border-bottom: 1px solid #ebebeb;
}
</style>