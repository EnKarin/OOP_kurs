package Beings;

abstract class Animals extends Beings{

    double speed;
    boolean live = true;
    boolean goal = false;

    Animals(boolean male, double maxHp, int x, int y, double speed){
        super(male, maxHp, x, y);
        this.speed = speed;
    }
}