package model;

import javafx.animation.AnimationTimer;

public class World extends AnimationTimer{
    private float speed;

    @Override
    public void handle(long xpto){

    }

    public void changeSpeed(float speed){
        if (speed < 0) {
            stop();
            return;
        }

        this.speed = speed;
    }
}