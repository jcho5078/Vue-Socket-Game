<template>
  <div></div>
</template>

<script>
//import {Phaser, PlayGame, config} from '@/assets/game'

// eslint-disable-next-line no-unused-vars
//import {GameData} from '@/assets/game'

export default {
  name: "GameView",
  created() {
    //this.showGame();

   /* const gameScene = new PlayGame();
    gameScene.userName = '';
    config.scene = [gameScene];
    const GameData = new Phaser.Game(config);

    GameData.pause();
    GameData.onHidden();*/
  },
  beforeUnmount() {
    this.hideGame();
  },
  data() {
    return {
      textarea: '',
      message: '',
    }
  },
  methods: {
    showGame(){
      document.querySelector('canvas').hidden = false;
    },
    hideGame (){
      document.querySelector('canvas').hidden = true;
    }
  }
}
</script>

<style scoped>

</style>