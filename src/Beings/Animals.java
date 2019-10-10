package Beings;

abstract class Animals extends Beings{

    int speed;
    double full = 40;
    boolean live;
    boolean goal;

    Animals(double currentHp, double maxHp, int x, int y, int speed){
        super(currentHp, maxHp, x, y);
        this.speed = speed;
    }
}