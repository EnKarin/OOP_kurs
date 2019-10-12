package Beings;

import java.util.Set;
import java.util.Random;

public class Plants extends Beings{

    public Plants(boolean male, double maxHp, int x, int y){
        super(male, maxHp, x, y);
    }

    @Override
    public boolean live(Set<Beings> unit){
        Random rand = new Random();
        check();
        if(currentHp <= 0.0001 ){
            return false;
        }
        else if(rand.nextInt((int)age * 5000 + 1) == 5000){
            return false;
        }
        final Plants dad = (Plants) searchPartner(unit, Plants.class);
        age += 0.002;
        if(currentHp < maxHp) {
            currentHp += 0.008;
        }
        if(age > 0.4 && age < 0.63 && dad != null){
            final int distance =(int)Math.sqrt(Math.pow(x - dad.x, 2) + Math.pow(y - dad.y, 2));
            if(rand.nextInt(distance * 5 + 1) == 0){
                unit.add(new Plants(rand.nextBoolean(),maxHp * 0.8
                        + rand.nextInt((int)Math.abs(maxHp - dad.maxHp) + 1),
                        (int)(x - 200 + rand.nextInt(400)), (int)(y - 200 + rand.nextInt(400))));
            }
        }
        return true;
    }
}
