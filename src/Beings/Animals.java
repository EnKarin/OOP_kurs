package Beings;

import java.util.Random;

abstract class Animals extends Beings{

    double speed;
    boolean live = true;
    boolean goal = false;

    Animals(boolean male, double maxHp, int x, int y, double speed){
        super(male, maxHp, x, y);
        this.speed = speed;
    }

    void movement (){
        double sin = new Random().nextInt(100) / 100;
        double cos = new Random().nextInt(100) / 100;
        if(new Random().nextBoolean()) {
            x += speed * cos;
            y += speed * sin;
        }
        else {
            x -= speed * cos;
            y -= speed * sin;
        }
    }
}