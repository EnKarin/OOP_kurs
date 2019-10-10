package Beings;

abstract class Animals extends Beings{

    double speed;
    double full = 40;
    boolean live;
    boolean goal;

    Animals(double maxHp, int x, int y, double speed){
        super(maxHp, x, y);
        this.speed = speed;
    }
}