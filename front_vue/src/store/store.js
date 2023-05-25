import {createStore} from 'vuex';

export default createStore({
    state() {
        return {
            counter: 10
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
        }
    }
});