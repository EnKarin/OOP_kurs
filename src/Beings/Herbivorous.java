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
                currentHp -= maxHp * 0.15;
                goal = true;
                unit.add(new Herbivorous((int) (maxHp * 0.8 + rand.nextInt((int) Math.abs(maxHp
                        - mam.maxHp))), x + 15, y + 15, speed * 0.8 + rand.nextInt((int)(Math.abs(speed
                        - mam.speed)))));
            }
        }
        else if (enemy(unit) != null) {
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

    private void escape(ArrayDeque<Beings> unit) { //побег
        live = true;
        int distance;
        double sin, cos;
        Carnivorous enemy = enemy(unit);
        distance = (int) Math.sqrt(Math.pow(x - enemy.x, 2) + Math.pow(y - enemy.y, 2));
        sin = Math.abs(y - enemy.y) / distance;
        cos = Math.abs(x - enemy.x) / distance;
        x -= 2 * speed * cos;
        y -= 2 * speed * sin;
        currentHp -= 0.01;
        if(Math.abs(x - enemy.x) <= 3 && Math.abs(y - enemy.y) <= 3){
            live = false;
        }
    }

    private void hunger(ArrayDeque<Beings> unit){ //поиск еды
        Plants temp = (Plants) search(unit, Plants.class);
        int distance = (int)Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
        double sin = Math.abs(y - temp.y) / distance;
        double cos = Math.abs(x - temp.x) / distance;
        x += speed * cos;
        y += speed * sin;
        currentHp -= 0.005;
        if(Math.abs(x - temp.x) <= currentHp && Math.abs(y - temp.y) <= currentHp){
            currentHp += temp.currentHp * 0.7;
            temp.currentHp = 0;
        }
    }

    boolean live(ArrayDeque<Beings> unit){
        currentHp -= 0.001;
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
        else if(currentHp >= maxHp * 0.8 && age >= 5 && age <= 6 && !goal){
            reproduction(unit);
        }
        return true;
    }
}