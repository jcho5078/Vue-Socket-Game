var app = require('express')();
var server = require('http').createServer(app);
import { Server } from 'socket.io'

/*const io = new Server(http, {
    cors: {
        origin: ['http://localhost:8080', 'http://localhost', 'http://ec2-13-125-51-108.ap-northeast-2.compute.amazonaws.com/'],
        credentials: true,
    },
    allowEIO3: true,
})*/

/*io.on('chat', (data) => {
    console.log(data);
    io.emit('chat', data);
});*/

/*io.on('connection', (socket) => {
    console.log('a user connected');
    socket.on('send', message => {
        messages.push(message)
        io.emit('messages', messages)
    });
    socket.on('chat', (data) => {
        console.log(data);
    });
});*/

/*server.listen(3000, () => {
    console.log('listening on *:3000');
});*/

//connection event handler
/*io.on('connection' , function(socket) {
    console.log('Connect from Client: '+socket)

    socket.on('chat', function(data){
        console.log('message from Client: '+data.message)

        var rtnMessage = {
            message: data.message
        };

        // 클라이언트에게 메시지를 전송한다
        socket.broadcast.emit('chat', rtnMessage);
    });
});*/
/*

server.listen(3000, function() {
    console.log('socket io server listening on port 3001')
})
*/

/*
setInterval(update, 10);

function update() {
    renderGame();
}

function renderGame() {

    collisionDetection();
    renderStage();
    renderPlayers();
    renderEnemys();
    renderStraightEnemys();
    renderItems();
    renderTimer();

    if(stageClear){
        renderClearMessage();
    }
    acquireDetection();
    if(coffeeEffect){
        enemys.length = 0;
        straightEnemys.length = 0;
        items.length = 0;
        coffeeEffect = false;
    }

    if(balls.length){
        sendData();
    }
    if(isStart){
        timer -= 0.010;
        if(parseInt(timer) <= 0){
            timer = 0;
        }
    }
    renderTimer();
}*/

