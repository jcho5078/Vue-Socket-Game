<template>
  <button @click="moveGame">Move to Game</button><br>
  <button @click="moveChat">Move to Chat</button><br>
  <button @click="moveBoard">Move to Board</button>
  <h2>Intro</h2>
</template>

<script>
export default {
  name: "IntroView",
  methods: {
    moveGame(){
      this.$router.push('/game');
    },
    moveChat(){
      this.$router.push('/chat');
    },
    moveBoard(){
      this.$router.push('/board');
    }
  }
}
</script>

<style scoped>

</style>