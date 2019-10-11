package Beings;

import java.util.Random;
import java.util.Set;

abstract class Animals extends Beings {

    double speed;
    boolean live = true;
    boolean goal = false;
    double globalsin, globalcos;

    Animals(boolean male, double maxHp, int x, int y, double speed) {
        super(male, maxHp, x, y);
        this.speed = speed;
    }

    void movement() {
        final double sin = new Random().nextInt(100) / 100;
        final double cos = new Random().nextInt(100) / 100;
        x += speed * cos;
        y += speed * sin;
    }

    abstract void reproduction(Set<Beings> unit);
}