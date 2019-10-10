package Beings;

import java.util.ArrayDeque;

public class Herbivorous extends Animals{

    Herbivorous(int currentHp, int maxHp, int x, int y, int speed){
        super(currentHp, maxHp, x, y, speed);
    }

    void reproduction(ArrayDeque<Beings> unit){ //размножение травоядных
        double sin, cos;
        Herbivorous mam = (Herbivorous) search(unit, Herbivorous.class);
        if(enemy(unit) == null && mam != null) {
            int distance = (int) Math.sqrt(Math.pow(x - mam.x, 2) + Math.pow(y - mam.y, 2));
            sin = Math.abs(y - mam.y) / distance;
            cos = Math.abs(x - mam.x) / distance;
            x += speed * cos;
            y += speed * sin;
        }
        else{
            escape(unit);
            return;
        }
        unit.add(new Herbivorous((int)(0.1 * maxHp), (int)(maxHp * 0.8 + rand.nextInt((int)Math.abs(maxHp
                - mam.maxHp))), x + 15, y + 15, (int)(speed * 0.8 + rand.nextInt((Math.abs(speed
                - mam.speed))))));
        full -= 15;
        mam.full -= 15;
    }

    private Carnivorous enemy(ArrayDeque<Beings> unit){ //проверка есть ли опасность
        Carnivorous temp = (Carnivorous) search(unit, Carnivorous.class);
        int distance = (int)Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
        if(distance <= 300){
            return temp;
        }
        else return null;
    }

    private boolean escape(ArrayDeque<Beings> unit){ //побег
        int distance;
        double sin, cos;
        Carnivorous enemy = enemy(unit);
        if(enemy != null) {
            do {
                distance = (int) Math.sqrt(Math.pow(x - enemy.x, 2) + Math.pow(y - enemy.y, 2));
                sin = Math.abs(y - enemy.y) / distance;
                cos = Math.abs(x - enemy.x) / distance;
                x -= speed * cos;
                y -= speed * sin;
                currentHp -= 3;
            } while ((enemy = enemy(unit)) != null && currentHp > 0);
            if(currentHp > 0){
                return true;
            }
            else return false;
        }
        return true;
    }

    boolean live(ArrayDeque<Beings> unit){
        currentHp -= 0.005;
        age += 0.002;
        if(currentHp <= 0){
            return false;
        }
        if(currentHp < maxHp * 0.5){
            if(!escape(unit)){
                return false;
            }
        }
        if(currentHp >= maxHp * 0.8 && age >= 5 && age <= 6){
            reproduction(unit);
        }
        return true;

    }
}