<template>
  <h4 id="wellcome" v-if="getUserData.userNm != null ? true : false">WellCome, {{getUserData.userNm != null ? getUserData.userNm : ''}}!</h4>
  <a class="moveLogin" @click="moveLogin" v-else>Sign In</a>
</template>

<script>
import {Phaser, PlayGame, config} from '@/js/game';
import {isEmpty} from "@/common/commonUtil";

export default {
  name: "IntroView",
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
    document.querySelector('canvas').style.left = '15%';
    document.querySelector('canvas').style.margin = '10px';
    document.querySelector('canvas').style.padding = '10px';
    document.querySelector('canvas').style.border = '0.5px ridge black';

    //const gameView = document.querySelector('canvas');
    //document.getElementById('gameDiv').appendChild(gameView);
    //this.showGame();

    //GameData.pause();
    //GameData.onHidden();
  },
  beforeUnmount() {
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

<style scoped>
  .moveLogin {
    color: darkblue;
  }
  .moveLogin:hover {
    cursor:pointer;
    transition: transform 0.1s linear;
    transform: scale(1.1);
  }
</style>