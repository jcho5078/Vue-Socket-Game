<template>
  <div class="v-container">
    <h4>header</h4>
    <div v-if="isAuthenticated">
      <p>userToken : {{userToken}}</p>
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
      isAuthenticated: (localStorage.getItem('userToken') !== 'null' && localStorage.getItem('userToken') !== null),
      userToken: localStorage.getItem('userToken')
    }
  },
  methods: {
    getUserInfo() {
      const header = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem("userToken"),
      }

      getUserInfo(header).then(response => {
        console.log(response.data);
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