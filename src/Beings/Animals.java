package Beings;

import java.util.Random;
import java.util.Set;

abstract class Animals extends Beings {

    double speed;
    boolean live = true;
    boolean goal = false;
    double globalsin = 0, globalcos = 0;
    final Random rand = new Random();

    Animals(boolean male, double maxHp, int x, int y, double speed) {
        super(male, maxHp, x, y);
        this.speed = speed;
    }

    void movement() {
        rand.setSeed(0);
        if(rand.nextInt(30) == 1 || globalcos == 0){
            globalcos = rand.nextFloat() / Float.MAX_VALUE;
            globalsin = 1 - globalcos;
        }
        x += speed / 2 * globalcos;
        y += speed / 2 * globalsin;
    }

    abstract void reproduction(Set<Beings> unit);
}