<template>
  <h2>Intro</h2>
  <button @click="moveGame">Move to Game</button><br>
  <button @click="moveChat">Move to Chat</button><br>
  <button @click="moveBoard">Move to Board</button><br>
  <button v-if="!isAuthenticated" @click="moveLogin">Move to Login</button><br>
  <button v-if="!isAuthenticated" @click="moveSignUp">Move to SignUp</button><br>
  <br><br>
  {{this.$store.state.counter}}
  <button @click="count">plus state</button>
</template>

<script>
import {isEmpty} from "@/common/commonUtil";

export default {
  name: "IntroView",
  data() {
    return {
      isAuthenticated: !isEmpty((localStorage.getItem('userToken'))),
    }
  },
  methods: {
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
    },

    count(){
      //this.$store.commit('setCounter', {value: 1});
      this.$store.commit({
        type: 'setCounter',
        value: 1
      })
    }
  }
}
</script>

<style scoped>

</style>