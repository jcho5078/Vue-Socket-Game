
import io from 'socket.io-client';

const socket = io('http://ec2-3-37-230-102.ap-northeast-2.compute.amazonaws.com:3000',{
    cors: { origin: '*' },
    transports : ['websocket']
});

export {socket};
