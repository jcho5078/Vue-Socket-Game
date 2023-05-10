<template>

  <keep-alive>
    <router-view></router-view>
  </keep-alive>
</template>

<script>

export default {
  name: 'App',
  /*methods: {
    hideGame (){
      document.querySelector('canvas').hidden = true;
    }
  },
  beforeMount() {
    this.hideGame();
  }*/
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
screen {
  width: 100%;
  padding: 0px 50px 0 50px;
}
</style>
