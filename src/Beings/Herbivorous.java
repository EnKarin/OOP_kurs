package Beings;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.Set;

public class Herbivorous extends Animals {

    public Herbivorous(boolean male, int maxHp, int x, int y, double speed) {
        super(male, maxHp, x, y, speed);
    }

    private void reproduction(Set<Beings> unit) { //размножение травоядных
        double sin, cos;
        Herbivorous mam = (Herbivorous) search(unit, Herbivorous.class);
        if (enemy(unit) == null && mam != null) {
            int distance = (int) Math.sqrt(Math.pow(x - mam.x, 2) + Math.pow(y - mam.y, 2));
            sin = Math.abs(y - mam.y) / distance;
            cos = Math.abs(x - mam.x) / distance;
            x += speed * cos;
            y += speed * sin;
            if (Math.abs(x - mam.x) <= 3 && Math.abs(y - mam.y) <= 3) {
                currentHp -= maxHp * 0.15;
                goal = true;
                unit.add(new Herbivorous(new Random().nextBoolean(),(int)(maxHp * 0.8 + new Random().nextInt((int) Math.abs(maxHp
                        - mam.maxHp))), x + 15, y + 15, speed * 0.8 + new Random().nextInt((int)(Math.abs(speed
                        - mam.speed)))));
            }
        }
        else if (enemy(unit) != null) {
            escape(unit);
        }
    }

    private Carnivorous enemy(Set<Beings> unit) { //проверка есть ли опасность
        Carnivorous temp = (Carnivorous) search(unit, Carnivorous.class);
        if (temp != null && (int) Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2)) <= 300) {
            return temp;
        }
        else return null;
    }

    private void escape(Set<Beings> unit) { //побег
        int distance;
        double sin, cos;
        Carnivorous enemy = enemy(unit);
        distance = (int) Math.sqrt(Math.pow(x - enemy.x, 2) + Math.pow(y - enemy.y, 2));
        sin = (double)Math.abs(y - enemy.y) / distance;
        cos = (double)Math.abs(x - enemy.x) / distance;
        x -= 2 * speed * cos;
        y -= 2 * speed * sin;
        currentHp -= 0.01;
        if(Math.abs(x - enemy.x) <= 3 && Math.abs(y - enemy.y) <= 3){
            live = false;
        }
    }

    private void hunger(Set<Beings> unit){ //поиск еды
        Plants temp = (Plants) search(unit, Plants.class);
        if(temp != null) {
            int distance = (int) Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
            double sin = Math.abs(y - temp.y) / distance;
            double cos = Math.abs(x - temp.x) / distance;
            x += speed * cos;
            y += speed * sin;
            currentHp -= 0.005;
            if (Math.abs(x - temp.x) <= currentHp && Math.abs(y - temp.y) <= currentHp) {
                currentHp += temp.currentHp * 0.7;
                temp.currentHp = 0;
            }
        }
    }

    public boolean live(Set<Beings> unit){
        check();

        currentHp -= 0.01;
        age += 0.002;
        if(currentHp <= 0){
            return false;
        }
        else if(enemy(unit) != null){
            escape(unit);
            return live;
        }
        else if(currentHp < maxHp * 0.7){
            hunger(unit);
        }
        else if(currentHp >= maxHp * 0.8 && age >= 2 && age <= 2.1 && !goal){
            reproduction(unit);
        }
        else {
            movement();
        }
        return true;
    }
}