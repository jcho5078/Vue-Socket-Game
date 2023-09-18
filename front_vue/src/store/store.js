import {createStore} from 'vuex';

export default createStore({
    state() {
        return {
            counter: 10,
            userData: {
                "userNo": null,
                "loginId": null,
                "loginPw":null,
                "userNm": null,
                "regDt":null,
                "lastLoginDt": null,
                "jwtToken": null,
                "valid": null
            }
        }
    },
    getters: {
        time2(state) {
            return state.counter * 2;
        }
    },
    mutations: {
        setCounter(state, payload) {
            state.counter += payload.value;
        },
        setUserData(state, payload) {
            state.userData += payload.value;
        },
        resetUserData(state){
            localStorage.removeItem("localUserData");
            state.userData = {
                "userNo": null,
                "loginId": null,
                "loginPw":null,
                "userNm": null,
                "regDt":null,
                "lastLoginDt": null,
                "jwtToken": null,
                "valid": null
            }
        },
        callUserDataLocalStorage(state){
            state.userData = JSON.parse(localStorage.getItem('localUserData'));
        }
    },
    actions: {
        loadStateFromStorage({commit}) {
            commit('callUserDataLocalStorage');
        }
    }
});