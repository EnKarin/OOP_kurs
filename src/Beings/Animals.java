package Beings;

abstract class Animals extends Beings{

    double speed;
    boolean live = true;
    boolean goal = false;

    Animals(double maxHp, int x, int y, double speed){
        super(maxHp, x, y);
        this.speed = speed;
    }
}