import {createRouter, createWebHistory} from "vue-router";

import Intro from "@/view/IntroView";
import RankView from "@/view/rank/RankView";
import StetView from "@/view/stet/StetView";
import LoginView from "@/view/login/LoginView";
import SignUpView from "@/view/login/SignUpView";
import BoardDetail from "@/components/board/BoardDetail";
import BoardWrite from "@/components/board/BoardWrite";
import BoardListView from "@/view/board/BoardListView";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'Intro',
            component: Intro
        },
        {
            path: '/rank',
            component: RankView
        },
        {
            path: '/stet',
            component: StetView
        },
        {
            path: '/board',
            component: BoardListView
        },
        {
            path: '/board/view/:boardNo',
            component: BoardDetail
        },
        {
            path: '/board/write',
            component: BoardWrite
        },
        {
            path: '/board/write/:boardNo',
            component: BoardWrite
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