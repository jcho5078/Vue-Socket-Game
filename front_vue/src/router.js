import {createRouter, createWebHistory} from "vue-router";

import Intro from "@/components/IntroView";
import GameView from "@/components/GameView";
import ChatView from "@/components/ChatView";
import BoardList from "@/view/board/BoardList";
import BoardView from "@/view/board/BoardView";


export default createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            component: Intro
        },
        {
            path: '/game',
            component: GameView
        },
        {
            path: '/chat',
            component: ChatView
        },
        {
            path: '/board',
            component: BoardList
        },
        {
            path: '/board/view/:boardNo',
            component: BoardView
        }
    ]
});