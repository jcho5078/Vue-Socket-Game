import { createApp } from 'vue'
import {createRouter, createWebHistory} from "vue-router";

import App from './App.vue'
import GameView from "@/components/GameView";
import Intro from '@/components/IntroView';
/*import {Phaser, PlayGame, config} from './assets/game'*/
import ChatView from "@/components/ChatView";
/*import io from 'socket.io-client';

const socket = io('http://localhost:3000',{
    cors: { origin: '*' },
    transports : ['websocket']
});*/

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/',
          component: Intro
        },
        { path: '/game',
          component: GameView
        },
        { path: '/chat',
            component: ChatView
        }
    ]
});
const app = createApp(App);

//app.config.globalProperties.$socket = socket;
/*const gameScene = new PlayGame();
gameScene.userName = '';
const GameData = Phaser.Game(config);

app.config.globalProperties.$game = GameData;
GameData.pause();
GameData.onHidden();*/

app.use(router);
app.directive('auto-scroll', {
    updated: el => {
        el.scrollTop = el.scrollHeight;
    }
});

app.mount('#app');
