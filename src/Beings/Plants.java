package Beings;

import java.util.ArrayDeque;

public class Plants extends Beings{

    public Plants(double maxHp, int x, int y){
        super(maxHp, x, y);
    }

    public boolean live(ArrayDeque<Beings> unit){
        check();
        if(currentHp <= 0 ){
            return false;
        }
        int distance;
        Plants dad = (Plants) search(unit, Plants.class);
        age += 0.002;
        if(currentHp < maxHp) {
            currentHp += 0.008;
        }
        if(age > 3 && age < 5 && currentHp > maxHp / 1.5 && dad != null){
            distance =(int)Math.sqrt(Math.pow(x - dad.x, 2) + Math.pow(y - dad.y, 2));
            if(rand.nextInt(distance) == 1){
                unit.add(new Plants(maxHp * 0.8 + rand.nextInt((int)Math.abs(maxHp - dad.maxHp)),
                        x - 200 + rand.nextInt(400), y - 200 + rand.nextInt(400)));
            }
        }
        return true;
    }
}
