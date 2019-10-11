package Beings;

import java.util.Set;
import java.util.Random;

public class Plants extends Beings{

    public Plants(boolean male, double maxHp, int x, int y){
        super(male, maxHp, x, y);
    }

    public boolean live(Set<Beings> unit){
        check();
        if(currentHp <= 0 ){
            return false;
        }
        final Plants dad = (Plants) search(unit, Plants.class);
        age += 0.002;
        if(currentHp < maxHp) {
            currentHp += 0.008;
        }
        if(age > 3 && age < 3.5 && currentHp > maxHp / 1.5 && dad != null){
            final int distance =(int)Math.sqrt(Math.pow(x - dad.x, 2) + Math.pow(y - dad.y, 2));
            if(new Random().nextInt(distance * 5 + 1) == 0){
                unit.add(new Plants(new Random().nextBoolean(),maxHp * 0.8 + new Random().nextInt((int)Math.abs(maxHp - dad.maxHp) + 1),
                        x - 200 + new Random().nextInt(400), y - 200 + new Random().nextInt(400)));
            }
        }
        return true;
    }
}
