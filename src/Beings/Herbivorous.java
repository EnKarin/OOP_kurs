package Beings;

import java.util.ArrayDeque;

public class Herbivorous extends Animals {

    public Herbivorous(int maxHp, int x, int y, double speed) {
        super(maxHp, x, y, speed);
    }

    void reproduction(ArrayDeque<Beings> unit) { //размножение травоядных
        double sin, cos;
        Herbivorous mam = (Herbivorous) search(unit, Herbivorous.class);
        if (enemy(unit) == null && mam != null) {
            int distance = (int) Math.sqrt(Math.pow(x - mam.x, 2) + Math.pow(y - mam.y, 2));
            sin = Math.abs(y - mam.y) / distance;
            cos = Math.abs(x - mam.x) / distance;
            x += speed * cos;
            y += speed * sin;
            if (x == mam.x && y == mam.y) {
                full -= 15;
                goal = true;
                unit.add(new Herbivorous((int) (maxHp * 0.8 + rand.nextInt((int) Math.abs(maxHp
                        - mam.maxHp))), x + 15, y + 15, speed * 0.8 + rand.nextInt((int)(Math.abs(speed
                        - mam.speed)))));
            }
        } else if (enemy(unit) != null) {
            escape(unit);
        }
    }

    private Carnivorous enemy(ArrayDeque<Beings> unit) { //проверка есть ли опасность
        Carnivorous temp = (Carnivorous) search(unit, Carnivorous.class);
        if ((int) Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2)) <= 300 && temp != null) {
            return temp;
        }
        else return null;
    }

    private boolean escape(ArrayDeque<Beings> unit) { //побег
        int distance;
        double sin, cos;
        Carnivorous enemy = enemy(unit);
        distance = (int) Math.sqrt(Math.pow(x - enemy.x, 2) + Math.pow(y - enemy.y, 2));
        sin = Math.abs(y - enemy.y) / distance;
        cos = Math.abs(x - enemy.x) / distance;
        x -= speed * cos;
        y -= speed * sin;
        currentHp -= 3;
        if () {
            return true;
        }
        else return false;
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