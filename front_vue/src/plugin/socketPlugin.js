import io from 'socket.io-client';

const socketPlugin = io('http://localhost:3000',{
    cors: { origin: '*' },
    transports : ['websocket']
});


const SocketPlugin = {
    install(vue) {
        vue.mixin({
        });

        vue.prototype.$sendMessage = ($payload) => {
            socketPlugin.emit('chat', $payload);
        };

        // 인스턴스 메소드 추가
        vue.prototype.$socket = socketPlugin;
    },
};

export default {
    name: SocketPlugin
};