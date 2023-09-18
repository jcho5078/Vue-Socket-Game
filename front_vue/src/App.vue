<template>
  <header-form/>
  <chat-form/>
  <keep-alive>
    <router-view></router-view>
  </keep-alive>
</template>

<script>

import HeaderForm from "@/components/nav/headerForm";
import ChatForm from "@/components/chat/ChatForm";

export default {
  name: 'App',
  components: {HeaderForm, ChatForm},
  methods: {
    hideGame (){
      document.querySelector('canvas').hidden = true;
    }
  },
  beforeMount() {
    //this.hideGame();
    window.onkeydown = (e) => {
      if(e.key == 'Control' && document.querySelector('msg-chat') != null){
        var chatFocus = document.getElementById('msg-chat');
        chatFocus.focus();
        chatFocus.value = '';
      }
    }
    console.log(this.$cookies);
  },
  mounted() {
    this.$store.dispatch("loadStateFromStorage");
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
screen {
  width: 100%;
  padding: 0px 50px 0 50px;
}
button {
  padding: 2px 4px;
  margin: 5px;
  border: 1px solid #000000;
}
button:hover{
  padding: 3px 6px;
}
input[type=text]{
  border: 1px solid #000000;
}
textarea{
  border: 1px solid #000000;
}
</style>
