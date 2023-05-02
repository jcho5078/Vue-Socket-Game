import io from 'socket.io-client';

const socket = io('http://localhost:3000',{
    cors: { origin: '*' },
    transports : ['websocket']
});


const SocketPlugin = {
    install(vue) {
        vue.mixin({
        });

        vue.prototype.$sendMessage = ($payload) => {
            socket.emit('chat', $payload);
        };

        // 인스턴스 메소드 추가
        vue.prototype.$socket = socket;
    },
};

export default {
    name: SocketPlugin
};