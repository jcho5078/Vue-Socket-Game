import {createRouter, createWebHistory} from "vue-router";

import Intro from "@/view/IntroView";
import GameView from "@/components/GameView";
import ChatView from "@/components/ChatView";
import BoardList from "@/view/board/BoardList";
import BoardView from "@/view/board/BoardView";
import LoginView from "@/view/login/LoginView";
import SignUpView from "@/view/login/SignUpView";

const router = createRouter({
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
        },
        {
            path: '/user/login',
            component: LoginView
        },
        {
            path: '/user/signUp',
            component: SignUpView
        }
    ]
});

export default router;