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
        Random random = new Random();
        if(random.nextInt(240) == 1 || globalCos < 0.00001){
            globalCos = random.nextDouble() * 2 - 1;
            globalSin = Math.sqrt(1 - Math.pow(globalCos, 2)) * (random.nextBoolean()? 1: -1);
        }
        x += speed / 4 * globalCos;
        y += speed / 4 * globalSin;
    }

    abstract void reproduction(Set<Beings> unit);
}