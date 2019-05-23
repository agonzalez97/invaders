package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;
import com.mygdx.game.Controls;

import java.util.concurrent.locks.Condition;

public class Ship {

    enum State {
        IDLE, LEFT, RIGHT, SHOOT, DEAD, DYING;
    }

    Vector2 position;

    int vida = 5;
    State state;
    float stateTime;
    float speed = 5;

    TextureRegion frame;

    Weapon weapon;

    Ship(int initialPosition){
        position = new Vector2(initialPosition, 10);
        state = State.IDLE;
        stateTime = 0;

        weapon = new Weapon();
    }


    void setFrame(Assets assets){
        switch (state){
            case IDLE:
                frame = assets.naveidle.getKeyFrame(stateTime, true);
                break;
            case LEFT:
                frame = assets.naveleft.getKeyFrame(stateTime, true);
                break;
            case RIGHT:
                frame = assets.naveright.getKeyFrame(stateTime, true);
                break;
            case SHOOT:
                frame = assets.naveshoot.getKeyFrame(stateTime, true);
                break;
            case DYING:
                frame = assets.navedie.getKeyFrame(stateTime, false);
                break;
            default:
                frame = assets.naveidle.getKeyFrame(stateTime, true);
                break;
        }
    }

    void render(SpriteBatch batch){
        batch.draw(frame, position.x, position.y);

        weapon.render(batch);
    }

    public void update(float delta, Assets assets) {
        stateTime += delta;

        System.out.println(vida);

        switch (state){
            case IDLE:
                if(Controls.isLeftPressed()){
                    state = State.LEFT;
                    moveLeft();
                }
                if(Controls.isRightPressed()){
                    state = State.RIGHT;
                    moveRight();
                }
                if (Controls.isShootPressed()){
                    state = State.SHOOT;
                    shoot();
                    assets.shootSound.play();
                }
                break;

            case LEFT:
                if(Controls.isLeftPressed()){
                    state = State.LEFT;
                    moveLeft();
                } else {
                    state = State.IDLE;
                    stateTime = 0;
                }

                if(Controls.isRightPressed()){
                    state = State.RIGHT;
                    moveRight();
                }else {
                    state = State.IDLE;
                    stateTime = 0;
                }

                if (Controls.isShootPressed()){
                    state = State.SHOOT;
                    shoot();
                    assets.shootSound.play();
                }else {
                    state = State.IDLE;
                    stateTime = 0;
                }

                break;

            case RIGHT: if(Controls.isLeftPressed()){
                    state = State.LEFT;
                    stateTime = 0;
                    moveLeft();
                }else {
                state = State.IDLE;
                stateTime = 0;
                }
                if(Controls.isRightPressed()){
                    state = State.RIGHT;
                    stateTime = 0;
                    moveRight();
                }else {
                    state = State.IDLE;
                    stateTime = 0;
                }
                if (Controls.isShootPressed()){
                    state = State.SHOOT;
                    stateTime = 0;
                    shoot();
                    assets.shootSound.play();
                }else {
                    state = State.IDLE;
                    stateTime = 0;
                }
                break;

            case SHOOT:
                if(Controls.isLeftPressed()){
                    state = State.LEFT;
                    stateTime = 0;
                    moveLeft();
                }else {
                    state = State.IDLE;
                    stateTime = 0;
                }

                if(Controls.isRightPressed()){
                    state = State.RIGHT;
                    stateTime = 0;
                    moveRight();
                }else {
                    state = State.IDLE;
                    stateTime = 0;
                }

                if (Controls.isShootPressed()){
                    shoot();
                    assets.shootSound.play();
                }else {
                    state = State.IDLE;
                    stateTime = 0;
                }

                break;

            case DYING:
                if(assets.navedie.isAnimationFinished(stateTime)){
                    state = State.DEAD;
                    stateTime = 0;
                }
                break;

            case DEAD:
                vida --;
                idle();

                break;
        }
        setFrame(assets);

        if(vida <= 0 ){

        }
        weapon.update(delta, assets);

    }

    void idle(){
        state = State.IDLE;
    }

    void kill(){
        state = State.DYING;
        stateTime = 0;
    }

    void moveLeft(){
        position.x -= speed;
    }

    void moveRight(){
        position.x += speed;
    }

    void shoot(){
        weapon.shoot(position.x +16);
    }

}
