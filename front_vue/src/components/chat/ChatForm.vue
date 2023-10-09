<template>
  <div class="chat-box" v-auto-scroll>
    <div class="view-chat" v-html="textarea" v-auto-scroll disabled ></div>
    <div class="msg-box" v-if="getUserData.userNm != null ? true : false">
      <label>Your Message</label>
      <input id="msg-chat" type="text" v-model="message" @keypress.enter="sendMessage" v-auto-scroll style="width: 100%;"/>
      <button class="md-primary md-raised" @click="sendMessage">Submit</button>
    </div>
    <div class="msg-box" v-else>
      <label>로그인 해주세요</label>
    </div>
  </div>
</template>

<script>
import {socket} from "@/js/socket";
import {isEmpty} from "@/common/commonUtil";

export default {
  name: "ChatForm",
  created() {
    socket.on('connection', socket => {
      alert("게임서버 연결에 실패했습니다.\n");
    });
    socket.on('chat', (data) => {
      //this.textarea += data + '\n';

      const userData = this.$store.state.userData;
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
  mounted() {
    window.addEventListener('resize', this.handleResize);
  },
  data() {
    return {
      textarea: '',
      message: '',
      width: 0,
      height: 0,
    }
  },
  computed : {
    getUserData(){
      const userData = this.$store.state.userData;
      return isEmpty(userData) ? {
        "userNo": null,
        "loginId": null,
        "loginPw":null,
        "userNm": null,
        "regDt":null,
        "lastLoginDt": null,
        "jwtToken": null,
        "valid": null
      } : userData;
    }
  },
  methods: {
    handleResize(event) {
      this.width = window.innerWidth;
      this.height = window.innerHeight;
      if(this.width < 1332 || this.height < 698){
        document.querySelector('.chat-box').style.display = 'none';
      }else{
        document.querySelector('.chat-box').style.display = 'block';
      }
    },
    sendMessage() {
      if(this.message.length == 0) return;
      let data = '';
      let msg = this.message.replaceAll(/(\n|\r\n)/g, '<br>');
      const userData = this.$store.state.userData;
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
    overflow:scroll;
  }
  .msg-box {
    display: flex;
    height: 10%;
  }
</style>