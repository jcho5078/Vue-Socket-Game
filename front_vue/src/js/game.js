import Phaser from 'phaser';
import {socket} from "@/js/socket";
import {isEmpty} from "@/common/commonUtil";

//import axios from "axios";
//import io from 'socket.io-client';
// eslint-disable-next-line no-unused-vars

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
            "state": "create",/* create, die */
            "angle": angle,
            "x": x,
            "y": y,
        }
        socket.emit("bulletUpdate", this.bulletData);

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
    constructor (world, x, y, texture, bodyOptions, userName, userNo)
    {
        super(world, x, y, texture, null, { plugin: bodyOptions });
        this.userName = userName;
        this.userNo = userNo;

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

        this.cursors = this.input.keyboard.createCursorKeys();

        this.input.keyboard.on('keydown-SPACE', () => {

            const bullet = this.bullets.find(bullet => !bullet.active);

            if (bullet)
            {
                bullet.fire(this.ship.x, this.ship.y, this.ship.rotation, 5);
            }
        });

        this.ship = this.matter.add.image(400, 300, 'ship', null, { plugin: wrapBounds });
        this.userData = isEmpty(this.userData) ? {
                "userNo": null,
                "loginId": null,
                "loginPw":null,
                "userNm": null,
                "regDt":null,
                "lastLoginDt": null,
                "jwtToken": null,
                "valid": null
            } : this.userData;
        console.log('game test: ' + JSON.stringify(this.userData));
        this.userName = this.add.text(410, 310, this.userData.userNm
            , { font: '16px Courier', fill: '#00ff00' }).setScrollFactor(0);
        this.userNo = this.add.text(410, 310, this.userData.userNo
            , { font: '16px Courier', fill: '#00ff00' }).setScrollFactor(0);
        this.userNo.visible = false;
        this.ship.userName = this.userName;
        this.ship.userNo = this.userData.userNo;

        this.ship.setFrictionAir(0.02);
        this.ship.setFixedRotation();
        this.ship.setCollisionCategory(this.shipCollisionCategory);

        this.ship.setCollidesWith([ this.enemiesCollisionCategory, this.enemiesBulletCollisionCategory ]);

        this.bullets = [];
        this.enemeyBullets = [];

        for (let i = 0; i < 64; i++)
        {
            const bullet = new Bullet(this.matter.world, 0, 0, 'bullet', wrapBounds, this.userData.userNo);

            bullet.setCollisionCategory(this.bulletCollisionCategory);
            /* 장애물, enemy만 충돌시 제거 */
            bullet.setCollidesWith([ this.enemiesCollisionCategory, this.asteroidsCollisionCategory ]);
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
            asteroid.setCollidesWith([this.shipCollisionCategory, this.bulletCollisionCategory]);

            this.asteroids.push(asteroid);
        }

        this.enemies = [];

        /**
         * not login
         */
        if(this.userData == null ||this.userData.userNo == null){
            this.userName.setText('로그인해주세요 - offline mod');
        }

        /**
         * login
         */
        if(this.userData != null && this.userData.userNo != null) {
            var data = {
                "userName": this.userData.userNm,
                "userNo": this.userData.userNo,
                "state": "conn",/*update, bullet, conn*/
                "shipX": this.ship.x,
                "shipY": this.ship.y,
                "userNameX": this.userName.x,
                "userNameY": this.userName.y,
            }
            socket.emit('gameConn', data);
        }

        this.socketLogic(self);

        socket.on('currentPlayers', function (players) {
            Object.keys(players).forEach(function (userNo) {
                const userData = JSON.parse(localStorage.getItem('localUserData'));
                if(players[userNo] != null && userData.userNo != userNo){
                    console.log('userNo : ' + userNo + ' - ' + JSON.stringify(players[userNo]));
                    self.addOtherPlayers(self, players[userNo]);
                }
            })
        });

        socket.on('playerDisconnected', function (userNo) {
            self.enemies.forEach(function (otherPlayer) {
                if (userNo === otherPlayer.userNo) {
                    console.log(userNo + ' : 연결끊기거나 파괴됨.');
                    otherPlayer.destroy();
                }
            })
        });

        socket.on('gameUpdated', (data) => {
            self.enemies.forEach(function (otherPlayer) {
                if (data.userNo === otherPlayer.userNo) {
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
            /*bullets.forEach(function(bulletData){
                if(bulletData == null){
                    return;
                }
                const userData = JSON.parse(localStorage.getItem('localUserData'));
                if(bulletData.userNo != userData.userNo){
                    if(bulletData.state == 'create'){
                        self.addEnemyBullets(self, bulletData);
                    }/!*else if(bullet.state == 'die'){

                    }*!/
                }
            });*/
            if(bullets == null){
                return;
            }
            const userData = JSON.parse(localStorage.getItem('localUserData'));
            if(bullets.userNo != userData.userNo){
                if(bullets.state == 'create'){
                    self.addEnemyBullets(self, bullets);
                }
            }
        });
    }

    /**
     * 총알 충돌
     * @param collisionData
     */
    bulletVsEnemy (collisionData)
    {
        const enemy = collisionData.bodyA.gameObject; // 총알 or 피해자
        const bullet = collisionData.bodyB.gameObject; // 총알 or 피해자

        //enemy.setActive(false);
        //enemy.setVisible(false);
        enemy.world.remove(bullet.body, true);

        if(enemy.texture.key == 'ship' && bullet.texture.key == 'enemyBullet'){
            //this.userName.remove(this.userName.body, true);
            //enemy.world.remove(enemy, true);
            //socket.emit('disconn', enemy.userNo);
            location.reload();
        }else if(bullet.texture.key == 'ship' && enemy.texture.key == 'enemyBullet'){
            //bullet.world.remove(enemy, true);
            //socket.emit('disconn', bullet.userNo);
            location.reload();
        }else if(enemy.texture.key == 'enemyShip' && bullet.texture.key == 'bullet'){
            //enemy.world.remove(enemy, true);
            //socket.emit('disconn', enemy.userNo);
            //enemy.world.remove(enemy.body, true);
        }else if(bullet.texture.key == 'enemyShip' && enemy.texture.key == 'bullet'){
            //bullet.world.remove(bullet, true);
            //socket.emit('disconn', bullet.userNo);
            //bullet.world.remove(bullet.body, true);
        }else{
            enemy.setActive(false);
            enemy.setVisible(false);
            bullet.setActive(false);
            bullet.setVisible(false);
            enemy.world.remove(enemy, true);
        }

        //bullet.setActive(false);
        //bullet.setVisible(false);
    }

    update()
    {
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
            "state": "update",/*update, bullet, conn, die*/
            "shipX": this.ship.x,
            "shipY": this.ship.y,
            "shipRotation": this.ship.rotation,
            "userNameX": this.userName.x,
            "userNameY": this.userName.y,
        }
        socket.emit('gameUpdate', data);
    }

    dieAlarm(){
        this.alarm = this.add.text(400, 250, 'YOU DIE!!'
            , { font: '38px Courier', fill: '#00ff00' }).setScrollFactor(0);
    }

    /**
     * check in client(wrong)
     */
    socketLogic(self){
        /*socket.on('game', (data) => {

            if (data.state == "conn") {

                if(data.userName != null && data.userName != this.userData.userNm){
                    if(this.enemies.length > 0){
                        const length = this.enemies.length;
                        for(let i=0; i<length; i++){
                            if(this.enemies[i].userNo == data.userNo){
                                console.log('new user : ' + data.userName);
                                const enemyNm = this.add.text(data.userNameX, data.userNameY, data.userName
                                    , {font: '16px Red', fill: '#00ff00'}).setScrollFactor(0);
                                const enemy = new Enemy(this.matter.world, data.shipX, data.shipY, 'ship', this.wrapBounds, enemyNm, data.userNo);
                                this.enemies.push(enemy);
                            }
                        }
                    }else if(this.enemies.length < 1){
                        console.log('new user : ' + data.userName);
                        const enemyNm = this.add.text(data.userNameX, data.userNameY, data.userName
                            , {font: '16px Red', fill: '#00ff00'}).setScrollFactor(0);
                        const enemy = new Enemy(this.matter.world, data.shipX, data.shipY, 'ship', this.wrapBounds, enemyNm, data.userNo);
                        this.enemies.push(enemy);
                    }
                }

            } else if (data.state == "update") {

                /!**
                 * 이동
                 *!/
                for(let i=0; i<this.enemies.length; i++){
                    var currentData = {
                        "shipX": this.enemies[i].x,
                        "shipY": this.enemies[i].y,
                        "shipRotation": this.enemies[i].rotation,
                        "userNameX": this.enemies[i].userName.x,
                        "userNameY": this.enemies[i].userName.x,
                    }
                    if(this.enemies[i].userNo == data.userNo
                        && currentData.shipX != data.shipX
                        && currentData.shipY != data.shipY
                        && currentData.shipRotation != data.shipRotation
                        && currentData.userNameX != data.userNameX
                        && currentData.userNameY != data.userNameY){

                        this.enemies[i].x = data.shipX;
                        this.enemies[i].y = data.shipY;
                        this.enemies[i].userName.x = data.userNameX;
                        this.enemies[i].userName.y = data.userNameY;
                        //this.enemies[i].setRotation(data.shipRotation);
                        this.enemies[i].rotation = data.shipRotation;
                    }
                }

            } else if (data.state == "bullet") {
                console.log();
            } else if (data.state == "die") {
                console.log();
            }
        });*/
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
        console.log('new user : ' + data.userName);
        const enemyNm = this.add.text(data.userNameX, data.userNameY, data.userName
            , {font: '16px Red', fill: '#EDFF11'}).setScrollFactor(0);
        const enemy = new Enemy(this.matter.world, data.shipX, data.shipY, 'enemyShip', this.wrapBounds, enemyNm, data.userNo);
        enemy.setCollisionCategory(this.enemiesCollisionCategory);
        this.enemies.push(enemy);
    }

    /**
     * add enemy bullet
     * @param self
     * @param bulletData
     */
    addEnemyBullets(self, bulletData){
        const enemyBullet = new EnemeyBullet(this.matter.world, 0, 0, 'enemyBullet', this.wrapBounds, this.userData.userNo);

        enemyBullet.setCollisionCategory(this.enemiesBulletCollisionCategory);
        /* 플레이어 충돌시 제거 */
        enemyBullet.setCollidesWith([ this.shipCollisionCategory ]);
        enemyBullet.setOnCollide(this.bulletVsEnemy);
        this.enemeyBullets.push(enemyBullet);
        console.log('총알생성 : ' + JSON.stringify(bulletData));
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