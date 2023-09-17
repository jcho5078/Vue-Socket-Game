<template>
  <div class="chat-box" v-auto-scroll>
    <div class="view-chat" v-html="textarea" disabled ></div>
    <div class="msg-box">
      <label>Your Message</label>
      <input type="text" v-model="message" v-auto-scroll style="width: 100%;"/>
      <button class="md-primary md-raised" @click="sendMessage">Submit</button>
    </div>
  </div>
  <br>
</template>

<script>
import {socket} from "@/assets/socket";

export default {
  name: "ChatForm",
  created() {
    socket.on('connection', socket => {
      console.log(socket);
      alert("게임서버 연결에 실패했습니다.\n");
    });
    socket.on('chat', (data) => {
      //this.textarea += data + '\n';

      const userData = JSON.parse(localStorage.getItem('userData'));
      let msg = '';
      let nmColor = '';
      let align = '';
      if(userData.userNo == data.userNo) {
        nmColor = 'blue';
        align = 'right;';
      }
      else {
        nmColor = 'black';
        align = 'left';
      }
      msg += '<div class="chat-msg" style="text-align:'+align+'; padding: 5px;">' +
                '<h5 style="color: '+nmColor+'; display: inline; font-size: 9px;">'
                    +data.userNm+
                '</h5>' +
                '<p style="display: inline; font-size: 8px;">'+
                  ' : ' +data.msg +
                '</p>' +
              '</div>' +
              '<hr>';
      this.textarea += msg;
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
      let data = '';
      let msg = this.message.replaceAll(/(\n|\r\n)/g, '<br>');
      const userData = JSON.parse(localStorage.getItem('userData'));
      let userNo = userData.userNo;
      let userNm = userData.userNm;

      data = {
        'msg': msg,
        'userNo': userNo,
        'userNm': userNm
      }

      socket.emit('chat', data);
      this.message = '';
    }
  }
}
</script>

<style scoped>
  .chat-box{
    width: 250px;
    height: 500px;
    position: absolute;
    top:15%;
    right: 20px;
    border: 0.3px ridge black;
  }
  .view-chat{
    border-bottom: 0.5px ridge black;
    height: 90%;
  }
  .msg-box {
    display: flex;
    height: 10%;
  }
</style>