package Beings;

import java.util.ArrayDeque;

public class Carnivorous extends Animals{

    public Carnivorous(int maxHp, int x, int y, double speed){
        super(maxHp, x, y, speed);
    }

    void reproduction(ArrayDeque<Beings> unit){ //размножение хищников
        double sin, cos;
        Carnivorous mam = (Carnivorous) search(unit, Carnivorous.class);
        int distance;
        while(x != mam.x && y != mam.y){
            distance = (int)Math.sqrt(Math.pow(x - mam.x, 2) + Math.pow(y - mam.y, 2));
            sin = Math.abs(y - mam.y) / distance;
            cos = Math.abs(x - mam.x) / distance;
            x += speed * cos;
            y += speed * sin;
        }
        unit.add(new Carnivorous((int)(maxHp * 0.8 + rand.nextInt((int)Math.abs(maxHp
                - mam.maxHp))), x + 15, y + 15, (int)(speed * 0.8 + rand.nextInt((int)(Math.abs(speed
                - mam.speed))))));
        full -= 15;
        mam.full -= 15;
    }

    private Herbivorous hunger(ArrayDeque<Beings> unit){ //поиск еды
        Herbivorous temp = (Herbivorous) search(unit, Herbivorous.class);
        int distance = (int)Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
        if(distance <= 300){
            return temp;
        }
        else return null;
    }

    private boolean pursuit(ArrayDeque<Beings> unit){ //преследование добычи
        Herbivorous hunger = hunger(unit);
        if(hunger != null) {
            int distance;
            double sin, cos;
            do {
                distance = (int) Math.sqrt(Math.pow(x - hunger.x, 2) + Math.pow(y - hunger.y, 2));
                sin = Math.abs(y - hunger.y) / distance;
                cos = Math.abs(x - hunger.x) / distance;
                x += speed * cos;
                y += speed * sin;
                currentHp -= 5;
            } while (x == hunger.x && y == hunger.y && (hunger = hunger(unit)) != null && currentHp >= 20);
            if(x == hunger.x && y == hunger.y){
                return true;
            }
        }
        return false;
    }

    boolean live(ArrayDeque<Beings> unit){
        currentHp -= 0.008;
        age += 0.002;
        if(currentHp <= 0){
            return false;
        }
        if(currentHp < maxHp * 0.5){
            if(!pursuit(unit) && currentHp <= 0){
                return false;
            }
        }
        if(currentHp >= maxHp * 0.8 && age >= 5 && age <= 6){
            reproduction(unit);
        }
        return true;
    }
}
