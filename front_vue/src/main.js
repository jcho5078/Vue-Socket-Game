import { createApp } from 'vue'
import {createRouter, createWebHistory} from "vue-router";

import App from './App.vue'
import GameView from "@/components/GameView";
import Intro from '@/components/IntroView';
import '@/plugin/socketPlugin';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/',
          component: Intro
        },
        { path: '/game',
          component: GameView
        }
    ]
});
const app = createApp(App)
app.use(router);

app.directive('auto-scroll', {
    updated: el => {
        el.scrollTop = el.scrollHeight;
    }
});

app.mount('#app');