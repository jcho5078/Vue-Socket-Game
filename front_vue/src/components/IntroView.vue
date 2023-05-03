<template>
  <button @click="moveGame">Move to Game</button>
  <h2>Intro</h2>
</template>

<script>
export default {
  name: "IntroView",
  methods: {
    moveGame(){
      this.$router.push('/game');
    }
  }
}
</script>

<style scoped>

</style>