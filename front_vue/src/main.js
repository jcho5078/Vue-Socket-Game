import { createApp } from 'vue'
import {createRouter, createWebHistory} from "vue-router";

import App from './App.vue'
import GameView from "@/components/GameView";
import Intro from '@/components/IntroView';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/intro',
          component: Intro
        },
        { path: '/game',
          component: GameView
        }
    ]
});

const app = createApp(App)
app.use(router);

app.mount('#app');