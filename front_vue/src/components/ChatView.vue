<template>
  <label>Chat</label>
  <textarea v-model="textarea" disabled v-auto-scroll></textarea>
  <br>
  <label>Your Message</label>
  <input type="text" v-model="message"/>
  <button class="md-primary md-raised" @click="sendMessage">Submit</button>
</template>

<script>
import {socket} from "@/assets/socket";

export default {
  name: "ChatView",
  created() {
    socket.on('connection', socket => {
      console.log(socket);
    });
    socket.on('chat', (data) => {
      this.textarea += data + '\n';
    });
  },
  data() {
    return {
      textarea: '',
      message: '',
    }
  },
  methods: {
    sendMessage() {
      console.log(this.message);
      socket.emit('chat', this.message);
      this.message = '';
    }
  }
}
</script>

<style scoped>

</style>