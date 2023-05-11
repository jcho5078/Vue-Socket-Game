<template>
  <header-form/>
  <h2>Intro</h2>
  <button @click="moveGame">Move to Game</button><br>
  <button @click="moveChat">Move to Chat</button><br>
  <button @click="moveBoard">Move to Board</button><br>
  <button @click="moveLogin">Move to Login</button>
  <button @click="moveSignUp">Move to SignUp</button>

  <br><br>
  {{this.$store.state.counter}}
  <button @click="count">plus state</button>
</template>

<script>
import HeaderForm from "@/components/nav/headerForm";
export default {
  name: "IntroView",
  components: {HeaderForm},
  data() {

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
      this.$store.state.counter++;
    }
  }
}
</script>

<style scoped>

</style>