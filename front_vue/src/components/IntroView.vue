<template>
  <header-form/>
  <h2>Intro</h2>
  <button @click="moveGame">Move to Game</button><br>
  <button @click="moveChat">Move to Chat</button><br>
  <button @click="moveBoard">Move to Board</button>
</template>

<script>
import HeaderForm from "@/components/nav/headerForm";
export default {
  name: "IntroView",
  components: {HeaderForm},
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