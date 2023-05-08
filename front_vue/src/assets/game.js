import Phaser from 'phaser';
//import axios from "axios";
//import io from 'socket.io-client';
// eslint-disable-next-line no-unused-vars
//const socket = io(process.env.VUE_APP_SOCKET_ENDPOINT);

class Bullet extends Phaser.Physics.Matter.Sprite
{
    lifespan;

    constructor (world, x, y, texture, bodyOptions)
    {
        super(world, x, y, texture, null, { plugin: bodyOptions });

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

// eslint-disable-next-line no-unused-vars
class Enemy extends Phaser.Physics.Matter.Sprite
{
    constructor (world, x, y, texture, bodyOptions)
    {
        super(world, x, y, texture, null, { plugin: bodyOptions });

        this.play('eyes');

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

// eslint-disable-next-line no-unused-vars
/*class Stop extends Phaser.Scene {

    constructor ()
    {
        super({ key: 'stop' });
    }

    create (){
        this.add.text(10, 10, 'UserName : ', { font: '16px Courier', fill: '#00ff00' });

        let textEntry = this.add.text(10, 50, '', { font: '32px Courier', fill: '#ffff00' });

        this.input.keyboard.on('keydown', event =>
        {

            if (event.keyCode === 8 && textEntry.text.length > 0)
            {
                textEntry.text = textEntry.text.substr(0, textEntry.text.length - 1);
            }
            else if (event.keyCode === 32 || (event.keyCode >= 48 && event.keyCode < 90))
            {
                textEntry.text += event.key;
            } else if(event.keyCode === 13){
                // keycode enter
                if(this.userName === '' || this.userName === undefined){
                    this.userName = textEntry.text;
                    textEntry.text = '';
                    this.add.text(10, 10, '\nuserPw : ', { font: '16px Courier', fill: '#00ff00' });
                    console.log('userName : ' + this.userName);
                }else if(this.userPw === '' || this.userPw === undefined){
                    this.userPw = textEntry.text;
                    textEntry.text = '';
                    console.log('userPw : ' + this.userPw);
                    
                    //userName, userPw 입력 완료시 로그인 시도
                    this.login();
                }else {
                    alert('error');
                    location.reload();
                }
            }
        });
    }

    /!**
     * 로그인 시도
     *!/
    login(){
        this.scene.start('playGame', {
            userName: this.userName,
            userPw: this.userPw
        });

        /!*axios.post(process.env.VUE_APP_API_ENDPOINT + '/user/login', {
            userName: this.userName,
            userPw: this.userPw
        }, {
            // cors
            withCredentials: true
        }).then(response =>{
            console.log(response);

            if(response.data.success === 'Y'){
                //성공(화면이동)
                this.scene.start('playGame', {
                    userName: response.data.userName,
                    userPw: response.data.userPw
                });
            }else if(response.data.success === 'N'){
                //실패
                alert('login fail : not matched user information.');
            }else{
                //에러
                alert('login fail : error');
                location.reload();
            }
        });*!/
    }
}*/

class PlayGame extends Phaser.Scene
{
    cursors;
    ship;
    bullets;
    asteroids;
    enemies;
    userName;

    shipCollisionCategory;
    bulletCollisionCategory;
    enemiesCollisionCategory;
    asteroidsCollisionCategory;

    constructor ()
    {
        super({ key: 'playGame' });
    }

    preload ()
    {
        this.load.image('ship', '/assets/sprites/thrust_ship.png');
        // this.load.image('bullet', 'assets/sprites/bullets/bullet7.png');
        this.load.image('bullet', '/assets/sprites/shmup-bullet.png');
        this.load.spritesheet('enemy', '/assets/sprites/metalface78x92.png', { frameWidth: 78, frameHeight: 92 });
        this.load.unityAtlas('asteroids', '/assets/sprites/asteroids.png', '/assets/sprites/asteroids.png.meta');
    }

    create ()
    {
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
        this.shipCollisionCategory = this.matter.world.nextCategory();
        this.bulletCollisionCategory = this.matter.world.nextCategory();
        this.asteroidsCollisionCategory = this.matter.world.nextCategory();

        this.ship = this.matter.add.image(400, 300, 'ship', null, { plugin: wrapBounds });

        this.ship.setFrictionAir(0.02);
        this.ship.setFixedRotation();
        this.ship.setCollisionCategory(this.shipCollisionCategory);

        this.ship.setCollidesWith([ this.enemiesCollisionCategory ]);

        this.bullets = [];

        for (let i = 0; i < 64; i++)
        {
            const bullet = new Bullet(this.matter.world, 0, 0, 'bullet', wrapBounds);

            bullet.setCollisionCategory(this.bulletCollisionCategory);
            bullet.setCollidesWith([ this.enemiesCollisionCategory, this.asteroidsCollisionCategory ]);
            bullet.setOnCollide(this.bulletVsEnemy);

            this.bullets.push(bullet);
        }

        this.asteroids = [];

        for (let i = 0; i < 16; i++)
        {
            const x = Phaser.Math.Between(0, 800);
            const y = Phaser.Math.Between(0, 600);
            const frame = Phaser.Math.Between(0, 31);

            const asteroid = new Asteroid(this.matter.world, x, y, `asteroids_${frame}`, wrapBounds);

            asteroid.setCollisionCategory(this.asteroidsCollisionCategory);
            asteroid.setCollidesWith([ this.shipCollisionCategory, this.bulletCollisionCategory ]);

            this.asteroids.push(asteroid);
        }

        /*
        this.enemies = [];
        for (let i = 0; i < 6; i++)
        {
            const enemy = new Enemy(this.matter.world, Phaser.Math.Between(0, 800), Phaser.Math.Between(0, 600), 'enemy', wrapBounds);
            enemy.setCollisionCategory(this.enemiesCollisionCategory);
            enemy.setCollidesWith([ this.shipCollisionCategory, this.bulletCollisionCategory ]);
            this.enemies.push(enemy);
        }
        */

        this.cursors = this.input.keyboard.createCursorKeys();

        this.input.keyboard.on('keydown-SPACE', () => {

            const bullet = this.bullets.find(bullet => !bullet.active);

            if (bullet)
            {
                bullet.fire(this.ship.x, this.ship.y, this.ship.rotation, 5);
            }

        });
    }

    bulletVsEnemy (collisionData)
    {
        const bullet = collisionData.bodyA.gameObject;
        const enemy = collisionData.bodyB.gameObject;

        bullet.setActive(false);
        bullet.setVisible(false);
        bullet.world.remove(bullet.body, true);

        enemy.setActive(false);
        enemy.setVisible(false);
        enemy.world.remove(enemy.body, true);
    }

    update ()
    {
        if (this.cursors.left.isDown)
        {
            this.ship.setAngularVelocity(-0.15);
        }
        else if (this.cursors.right.isDown)
        {
            this.ship.setAngularVelocity(0.15);
        }
        else
        {
            this.ship.setAngularVelocity(0);
        }

        if (this.cursors.up.isDown)
        {
            this.ship.thrust(0.001);
        }
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
const GameData = new Phaser.Game(config);

export {Phaser, PlayGame, config, GameData};