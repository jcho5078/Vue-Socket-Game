<template>
  <div class="page_nm">
    <h4 id="wellcome" v-if="getUserData.userNm != null ? true : false">WellCome, {{getUserData.userNm != null ? getUserData.userNm : ''}}!</h4>
    <h4 class="moveLogin" @click="moveLogin" v-else>Sign In</h4>
  </div>
  <chat-form/>
</template>

<script>
import {Phaser, PlayGame, config} from '@/js/game';
import {isEmpty} from "@/common/commonUtil";

import ChatForm from "@/components/chat/ChatForm";

export default {
  name: "IntroView",
  components: {ChatForm},
  data() {
    return {
      textarea: '',
      message: '',
      isAuthenticated: !isEmpty((localStorage.getItem('userToken'))),
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
    },
  },
  created() {
    const gameScene = new PlayGame();
    gameScene.userName = '';
    config.scene = [gameScene];
    const GameData = new Phaser.Game(config);
    document.querySelector('canvas').style.position = 'absolute';
    document.querySelector('canvas').style.left = '20%';
    document.querySelector('canvas').style.margin = '10px';
    document.querySelector('canvas').style.padding = '10px';
    document.querySelector('canvas').style.border = '0.5px ridge black';

    if(this.$route.query.isDoLogin){
      // vue phaser 버그 수정
      const baseUrl = window.location.protocol + '//' + window.location.host;
      location.href = baseUrl + '/';
    }
  },
  beforeMount() {
    window.onkeydown = (e) => {
      if(e.key == 'Control' && document.querySelector('msg-chat') != null){
        var chatFocus = document.getElementById('msg-chat');
        chatFocus.focus();
        chatFocus.value = '';
      }
    }
  },
  unmounted() {
    this.hideGame();
  },
  methods: {
    hideGame (){
      const g = document.querySelector('canvas');
      g.parentElement.removeChild(g);
      //document.querySelectorAll('canvas').hidden = true;
    },
    moveLogin(){
      this.$router.push('/user/login')
    },
    showGame(){
      //document.querySelectorAll('canvas').hidden = false;
    },
    count(){
      //this.$store.commit('setCounter', {value: 1});
      /*this.$store.commit({
        type: 'setCounter',
        value: 1
      })*/
    }
  }
}
</script>

<style scoped>\
  .moveLogin {
    color: darkblue;
  }
  .moveLogin:hover {
    cursor:pointer;
    transition: transform 0.1s linear;
    transform: scale(1.1);
  }
</style>