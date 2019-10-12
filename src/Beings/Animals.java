package Beings;

import java.util.Random;
import java.util.Set;

abstract class Animals extends Beings {

    double speed;
    boolean live = true;
    private double globalSin = 0, globalCos = 0;
    final Random rand = new Random();

    Animals(boolean male, double maxHp, int x, int y, double speed) {
        super(male, maxHp, x, y);
        this.speed = speed;
    }

    void movement() {
        rand.setSeed(0);
        if(rand.nextInt(30) == 1 || globalCos == 0){
            globalCos = rand.nextFloat() / Float.MAX_VALUE;
            globalSin = 1 - globalCos;
        }
        x += speed / 2 * globalCos;
        y += speed / 2 * globalSin;
    }

    abstract void reproduction(Set<Beings> unit);
}