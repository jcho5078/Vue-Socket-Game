<template>
  <h3>game</h3>
  <div>
    <label>Chat</label>
    <textarea v-model="textarea" disabled v-auto-scroll></textarea>
    <br>
    <label>Your Message</label>
    <input type="text" v-model="message"/>
    <button class="md-primary md-raised" @click="sendMessage">Submit</button>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import GameData from '@/assets/game'
//import SocketioService from '../assets/socketPlugin';

export default {
  name: "GameView",
  created() {
    /*SocketioService.setupSocketConnection();*/
    /*this.socketPlugin.on('chat', (data)=> {
      this.textarea += data.message + "\n"
    })*/
    /*SocketioService.socketPlugin.on('chat', (data) => {
      console.log(data);
    });*/
    this.$socket.on('chat', (data) => {
      this.textarea += data + '\n';
    });
  },
  beforeUnmount() {
    /*SocketioService.disconnect();*/
  },
  data() {
    return {
      textarea: '',
      message: '',
    }
  },
  methods: {
    sendMessage(msg) {
      /*this.$socketPlugin.emit('chat',{
        message: this.message
      });*/
      /*SocketioService.socketPlugin.on("chat", (data) => {
        console.log(data);
        this.message = data;
        this.textarea += data + '\n';
      });*/
      this.message = msg;
      this.textarea += this.message + '\n';
      this.message = ''
      this.$sendMessage(msg);
    }
  }
}
</script>

<style scoped>

</style>