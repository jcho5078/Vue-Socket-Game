import Phaser from 'phaser';
import {socket} from "@/js/socket";
import {isEmpty} from "@/common/commonUtil";

//import axios from "axios";
//import io from 'socket.io-client';
// eslint-disable-next-line no-unused-vars

const OFFLINE_STATE = 'offline';

class Bullet extends Phaser.Physics.Matter.Sprite
{
    lifespan;
    userNo;
    bulletData;

    constructor (world, x, y, texture, bodyOptions, userNo)
    {
        super(world, x, y, texture, null, { plugin: bodyOptions });
        this.userNo = userNo;
        this.setFrictionAir(0);
        this.setFixedRotation();
        this.setActive(false);

        this.scene.add.existing(this);

        this.world.remove(this.body, true);
    }

    fire (x, y, angle, speed)
    {
        this.world.add(this.body);

        this.setPosition(x, y);
        this.setActive(true);
        this.setVisible(true);

        this.setRotation(angle);
        this.setVelocityX(speed * Math.cos(angle));
        this.setVelocityY(speed * Math.sin(angle));

        this.bulletData = {
            "userNo" : this.userNo,
            "angle": angle,
            "x": x,
            "y": y,
        }
        if(this.bulletData.userNo != null && this.bulletData.userNo != ''){
            socket.emit("bulletUpdate", this.bulletData);
        }

        this.lifespan = 1000;
    }

    preUpdate (time, delta)
    {
        super.preUpdate(time, delta);

        this.lifespan -= delta;

        if (this.lifespan <= 0)
        {
            this.setActive(false);
            this.setVisible(false);
            this.world.remove(this.body, true);
        }
    }
}

class EnemeyBullet extends Phaser.Physics.Matter.Sprite
{
    lifespan;
    userNo;
    bulletData;

    constructor (world, x, y, texture, bodyOptions, userNo)
    {
        super(world, x, y, texture, null, { plugin: bodyOptions });
        this.userNo = userNo;
        this.setFrictionAir(0);
        this.setFixedRotation();
        this.setActive(false);

        this.scene.add.existing(this);

        this.world.remove(this.body, true);
    }

    emyFire (x, y, angle, speed)
    {
        this.world.add(this.body);

        this.setPosition(x, y);
        this.setActive(true);
        this.setVisible(true);

        this.setRotation(angle);
        this.setVelocityX(speed * Math.cos(angle));
        this.setVelocityY(speed * Math.sin(angle));

        this.lifespan = 1000;
    }

    preUpdate (time, delta)
    {
        super.preUpdate(time, delta);

        this.lifespan -= delta;

        if (this.lifespan <= 0)
        {
            this.setActive(false);
            this.setVisible(false);
            this.world.remove(this.body, true);
        }
    }
}

class Enemy extends Phaser.Physics.Matter.Sprite
{
    userName;
    userNo;
    socketId;
    constructor (world, x, y, texture, bodyOptions, userName, userNo, socketId)
    {
        super(world, x, y, texture, null, { plugin: bodyOptions });
        this.userName = userName;
        this.userNo = userNo;
        this.socketId = socketId;

        this.setFrictionAir(0.02);
        this.setFixedRotation();

        this.setFrictionAir(0);

        this.scene.add.existing(this);
    }

    preUpdate (time, delta)
    {
        super.preUpdate(time, delta);
    }
}

class Asteroid extends Phaser.Physics.Matter.Sprite
{
    constructor (world, x, y, frame, bodyOptions)
    {
        super(world, x, y, 'asteroids', frame, { plugin: bodyOptions });

        this.setFrictionAir(0);

        this.scene.add.existing(this);

        const angle = Phaser.Math.Between(0, 360);
        const speed = Phaser.Math.FloatBetween(1, 3);

        this.setAngle(angle);

        this.setAngularVelocity(Phaser.Math.FloatBetween(-0.05, 0.05));

        this.setVelocityX(speed * Math.cos(angle));
        this.setVelocityY(speed * Math.sin(angle));
    }

    preUpdate (time, delta)
    {
        super.preUpdate(time, delta);
    }
}

class PlayGame extends Phaser.Scene
{
    cursors;
    ship;
    bullets;
    enemeyBullets;
    asteroids;
    enemies;
    userName;
    tempData;
    alarm;
    userNo;

    shipCollisionCategory;
    bulletCollisionCategory;
    enemiesCollisionCategory;
    enemiesBulletCollisionCategory;
    asteroidsCollisionCategory;
    userData = JSON.parse(localStorage.getItem('localUserData'));
    wrapBounds = {
        wrap: {
            min: {x: 0, y: 0},
            max: {x: 800, y: 600}
        }
    };

    constructor ()
    {
        super({ key: 'playGame' });
    }

    preload ()
    {
        this.load.image('ship', '/thrust_ship.png');
        this.load.image('enemyShip', '/thrust_ship2.png');
        this.load.image('bullet', '/shmup-bullet.png');
        this.load.image('enemyBullet', '/enemy-bullet.png');
        //this.load.spritesheet('enemy', require('@/assets/sprites/metalface78x92.png'), { frameWidth: 78, frameHeight: 92 });
        //this.load.unityAtlas('asteroids', '/assets/sprites/asteroids.png', '/assets/sprites/asteroids.png.meta');
    }

    create ()
    {
        const self = this;
        const wrapBounds = {
            wrap: {
                min: { x: 0, y: 0 },
                max: { x: 800, y: 600 }
            }
        };

        this.anims.create({
            key: 'eyes',
            frames: this.anims.generateFrameNumbers('enemy', { start: 0, end: 3 }),
            frameRate: 20,
            repeat: -1
        });

        this.enemiesCollisionCategory = this.matter.world.nextCategory();
        this.enemiesBulletCollisionCategory = this.matter.world.nextCategory();
        this.shipCollisionCategory = this.matter.world.nextCategory();
        this.bulletCollisionCategory = this.matter.world.nextCategory();
        this.asteroidsCollisionCategory = this.matter.world.nextCategory();

        this.ship = this.matter.add.image(400, 300, 'ship', null, { plugin: wrapBounds });
        this.userData = isEmpty(this.userData) ? {
                "userNo": null,
                "loginId": null,
                "loginPw":null,
                "userNm": null,
                "regDt":null,
                "lastLoginDt": null,
                "jwtToken": null,
                "valid": null,
                "socketId":null
            } : this.userData;
        this.userName = this.add.text(410, 310, this.userData.userNm
            , { font: '16px Courier', fill: '#00ff00' }).setScrollFactor(0);
        this.userNo = this.add.text(410, 310, this.userData.userNo
            , { font: '16px Courier', fill: '#00ff00' }).setScrollFactor(0);
        this.userNo.visible = false;

        // Ship 오브젝트에 유저 데이터 셋팅
        this.ship.userName = this.userName;
        this.ship.userNo = this.userData.userNo;
        this.ship.socketId = this.userData.socketId;

        this.cursors = this.input.keyboard.createCursorKeys();

        this.input.keyboard.on('keydown-SPACE', () => {
            if(this.ship.userNo != null){
                const bullet = this.bullets.find(bullet => !bullet.active);

                if (bullet)
                {
                    bullet.fire(this.ship.x, this.ship.y, this.ship.rotation, 5);
                }
            }
        });

        this.ship.setFrictionAir(0.02);
        this.ship.setFixedRotation();
        this.ship.setCollisionCategory(this.shipCollisionCategory);

        this.ship.setCollidesWith([ this.enemiesBulletCollisionCategory ]);

        this.bullets = [];
        this.enemeyBullets = [];

        for (let i = 0; i < 64; i++)
        {
            const bullet = new Bullet(this.matter.world, 0, 0, 'bullet', wrapBounds, this.userData.userNo);

            bullet.setCollisionCategory(this.bulletCollisionCategory);
            /* 장애물, enemy만 충돌시 제거 */
            bullet.setCollidesWith([ this.enemiesCollisionCategory/*, this.asteroidsCollisionCategory*/ ]);
            bullet.setOnCollide(this.bulletVsEnemy);

            this.bullets.push(bullet);
        }

        this.asteroids = [];

        for (let i = 0; i < 16; i++) {
            const x = Phaser.Math.Between(0, 800);
            const y = Phaser.Math.Between(0, 600);
            const frame = Phaser.Math.Between(0, 31);

            const asteroid = new Asteroid(this.matter.world, x, y, `asteroids_${frame}`, wrapBounds);

            asteroid.setCollisionCategory(this.asteroidsCollisionCategory);
            asteroid.setCollidesWith([/*this.shipCollisionCategory, this.bulletCollisionCategory*/]);

            this.asteroids.push(asteroid);
        }

        this.enemies = [];

        /**
         * not login
         */
        if(this.userData == null ||this.userData.userNo == null){
            this.userName.setText('로그인해주세요 - offline mod');
            this.ship.userNo = OFFLINE_STATE;
        }

        /**
         * login
         */
        if(this.userData != null && this.userData.userNo != null) {
            var data = {
                "userName": this.userData.userNm,
                "userNo": this.userData.userNo,
                "shipX": this.ship.x,
                "shipY": this.ship.y,
                "userNameX": this.userName.x,
                "userNameY": this.userName.y,
            }
            socket.emit('gameConn', data);
        }

        if(this.userNo == null || this.userNo == OFFLINE_STATE){
            return;
        }

        socket.on('newPlayer', function (player) {
            const userData = JSON.parse(localStorage.getItem('localUserData'));
            console.log("newPlayer : " + JSON.stringify(player));
            if(player != null && player.userNo != userData.userNo){
                self.addOtherPlayers(self, player);
            }
        });

        socket.on('currentPlayers', function (players) {
            const userData = JSON.parse(localStorage.getItem('localUserData'));

            Object.keys(players).forEach(function(connId){
                if(players[connId].userNo != null && userData.userNo != players[connId].userNo){
                    players[connId].socketId = connId;
                    console.log('currentPlayers : ' + JSON.stringify(players[connId]));
                    self.addOtherPlayers(self, players[connId]);
                }
            });
        });

        socket.on('playerDisconnected', function (userNo) {
            console.log('playerDisconnected start : ' + userNo);
            self.enemies.forEach(function (otherPlayer, idx) {
                console.log('playerDisconnected : ' + userNo + " - " + otherPlayer.userNo);
                if (userNo == otherPlayer.userNo) {
                    console.log(userNo + ' : 연결끊기거나 파괴됨.');
                    otherPlayer.userName.destroy();
                    otherPlayer.destroy();
                    self.enemies.splice(idx, 1);
                    console.log('finished : ' + JSON.stringify(self.enemies));
                }
            })
        });

        socket.on('gameUpdated', (data) => {
            //console.log('gameUpdated : ' + data );
            self.enemies.forEach(function (otherPlayer) {
                if (data.userNo == otherPlayer.userNo) {
                    otherPlayer.setRotation(data.shipRotation);
                    otherPlayer.setPosition(data.shipX, data.shipY);
                    otherPlayer.userName.setPosition(data.userNameX, data.userNameY);
                }
            })
        });

        socket.on('bulletUpdated', (bullets) => {
            /*{
                "userNo" : userNo,
                "state": /!* create, die *!/
                "angle": angle,
                "x": x,
                "y": y,
            }*/
            if(bullets == null){
                return;
            }
            const userData = JSON.parse(localStorage.getItem('localUserData'));
            if(bullets.userNo != userData.userNo){
                self.addEnemyBullets(self, bullets);
            }
        });

        socket.on('hitUpdate', (userData) => {
            self.enemies.forEach(function (otherPlayer) {
                if (userData.userNo == otherPlayer.userNo) {
                    otherPlayer.userName.destroy();
                    otherPlayer.destroy();
                }
            })
        });
    }

    /**
     * 총알 충돌
     * @param collisionData
     */
    bulletVsEnemy (collisionData)
    {
        const objA = collisionData.bodyA.gameObject; // 총알 or 피해자
        const objB = collisionData.bodyB.gameObject; // 총알 or 피해자

        if(objA == null || objB == null){
            return;
        }
        if(objA.texture.key == 'ship' && objB.texture.key == 'enemyBullet'){
            // objB is bullet
            let hitData = {
                "bulletUserNo" : objB.userNo,
                "shipUserNo" : objA.userNo,
            };
            console.log('test killed1 : ' + JSON.stringify(hitData));

            socket.emit('hitByBullet', hitData);
            /*objA.userName.destroy();
            objA.destroy();*/
            objA.userNo = null;
            objA.userName.destroy();
            objA.destroy();
        }else if(objB.texture.key == 'ship' && objA.texture.key == 'enemyBullet'){
            // objA is bullet
            let hitData = {
                "bulletUserNo" : objA.userNo,
                "shipUserNo" : objB.userNo,
            };
            console.log('test killed2 : ' + JSON.stringify(hitData));

            socket.emit('hitByBullet', hitData);
            /*objB.userName.destroy();
            objB.destroy();*/
            objB.userNo = null;
            objB.userName.destroy();
            objB.destroy();
        }else{
            if(objA.bulletData != null && objA.userNo != objB.userNo){
                // objA is bullet
                let hitData = {
                    "bulletUserNo" : objA.userNo,
                    "shipUserNo" : objB.userNo,
                };
                console.log('test killed3 : ' + JSON.stringify(hitData));

                socket.emit('hitByBullet', hitData);
                objA.destroy();
                objB.userName.destroy();
                objB.destroy();
            }else if(objB.bulletData != null && objA.userNo != objB.userNo){
                // objB is bullet
                let hitData = {
                    "bulletUserNo" : objB.userNo,
                    "shipUserNo" : objA.userNo,
                };

                socket.emit('hitByBullet', hitData);

                objA.userName.destroy();
                objA.destroy();
                objB.destroy();
            }
        }

        //bullet.setActive(false);
        //bullet.setVisible(false);
    }

    update()
    {
        if(this.ship.userNo != null){
            if (this.cursors.left.isDown) {
                this.ship.setAngularVelocity(-0.15);
            } else if (this.cursors.right.isDown) {
                this.ship.setAngularVelocity(0.15);
            } else {
                this.ship.setAngularVelocity(0);
            }

            if (this.cursors.up.isDown) {
                this.ship.thrust(0.0005);
            }
            this.userName.x = this.ship.x - 20;
            this.userName.y = this.ship.y - 30;
            var data = {
                "userName": this.userName.text,
                "userNo": this.userNo.text,
                "shipX": this.ship.x,
                "shipY": this.ship.y,
                "shipRotation": this.ship.rotation,
                "userNameX": this.userName.x,
                "userNameY": this.userName.y,
            }
            socket.emit('gameUpdate', data);
        }
    }

    dieAlarm(){
        this.alarm = this.add.text(400, 250, 'YOU DIE!!'
            , { font: '38px Courier', fill: '#00ff00' }).setScrollFactor(0);
    }

    addPlayer(self, data) {
        /*self.ship = self.physics.add.image(playerInfo.shipX, playerInfo.shipY, 'ship')
            .setOrigin(0.5, 0.5)
            .setDisplaySize(50, 50)*/

        //self.ship.setCollideWorldBounds(true)
        //self.ship.setTint('red')
        //self.ship.setDrag(1000)
    }

    /**
     * add another player
     * @param self
     * @param data
     */
    addOtherPlayers(self, data) {
        console.log('addOtherPlayers : ' + JSON.stringify(data));
        const enemyNm = this.add.text(data.userNameX, data.userNameY, data.userName
            , {font: '16px Red', fill: '#EDFF11'}).setScrollFactor(0);
        const enemy = new Enemy(this.matter.world, data.shipX, data.shipY, 'enemyShip', this.wrapBounds, enemyNm, data.userNo, data.socketId);
        //enemy.setCollisionCategory(this.enemiesCollisionCategory);
        //enemy.setCollidesWith(this.enemiesBulletCollisionCategory);
        //enemy.setOnCollide(this.bulletVsEnemy);
        this.enemies.push(enemy);
    }

    /**
     * add enemy bullet
     * @param self
     * @param bulletData
     */
    addEnemyBullets(self, bulletData){
        const enemyBullet = new EnemeyBullet(this.matter.world, 0, 0, 'enemyBullet', this.wrapBounds, bulletData.userNo);

        enemyBullet.setCollisionCategory(this.enemiesBulletCollisionCategory);
        /* 플레이어 충돌 */
        enemyBullet.setCollidesWith([this.enemiesCollisionCategory, this.shipCollisionCategory]);
        enemyBullet.setOnCollide(this.bulletVsEnemy);
        this.enemeyBullets.push(enemyBullet);
        enemyBullet.emyFire(bulletData.x, bulletData.y, bulletData.angle, 5);
    }
}

const config = {
    type: Phaser.AUTO,
    width: 800,
    height: 600,
    backgroundColor: '#000000',
    parent: 'phaser-example',
    physics: {
        default: 'matter',
        matter: {
            debug: false,
            plugins: {
                wrap: true
            },
            gravity: {
                x: 0,
                y: 0
            }
        }
    },
    scene: [PlayGame]
};
// eslint-disable-next-line no-unused-vars
//const GameData = new Phaser.Game(config);

export {Phaser, PlayGame, config};