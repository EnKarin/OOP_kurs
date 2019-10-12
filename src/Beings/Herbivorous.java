package Beings;

import java.util.Set;

public class Herbivorous extends Animals {

    public Herbivorous(boolean male, int maxHp, int x, int y, double speed) {
        super(male, maxHp, x, y, speed);
    }

    void reproduction(Set<Beings> unit) { //размножение травоядных
        final Herbivorous mam = (Herbivorous) searchPartner(unit, Herbivorous.class);
        if (enemy(unit) == null && mam != null) {
            int distance = (int) Math.sqrt(Math.pow(x - mam.x, 2) + Math.pow(y - mam.y, 2));
            final double sin = (mam.y - y) / distance;
            final double cos = (mam.x - x) / distance;
            x += speed * cos;
            y += speed * sin;
            if (Math.abs(x - mam.x) <= 3 && Math.abs(y - mam.y) <= 3) {
                currentHp -= maxHp * 0.15;
                unit.add(new Herbivorous(rand.nextBoolean(),(int)(maxHp * 0.8 + rand.nextInt((int) Math.abs(maxHp
                        - mam.maxHp) + 1)), (int)(x + 15), (int)(y + 15), speed * 0.8 + rand.nextInt((int)(Math.abs(speed
                        - mam.speed)) + 1)));
            }
        }
        else if (enemy(unit) != null) {
            escape(unit);
        }
        else {
            movement();
        }
    }

    private Carnivorous enemy(Set<Beings> unit) { //проверка есть ли опасность
        final Carnivorous temp = (Carnivorous) search(unit, Carnivorous.class);
        if (temp != null && (int) Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2)) <= 300
                && temp.currentHp >= currentHp) {
            return temp;
        }
        else return null;
    }

    private void escape(Set<Beings> unit) { //побег
        final Carnivorous enemy = enemy(unit);
        final int distance = (int) Math.sqrt(Math.pow(x - enemy.x, 2) + Math.pow(y - enemy.y, 2));
        final double sin = (enemy.y - y) / distance;
        final double cos = (enemy.x - x)/ distance;
        x -= 2.3 * speed * cos;
        y -= 2.3 * speed * sin;
        currentHp -= 0.01;
        if(Math.abs(x - enemy.x) <= 1 && Math.abs(y - enemy.y) <= 1){
            live = false;
        }
    }

    private void hunger(Set<Beings> unit){ //поиск еды
        Plants temp = (Plants) search(unit, Plants.class);
        if(temp != null) {
            final int distance = (int) Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
            final double sin = (temp.y - y) / distance;
            final double cos = (temp.x - x) / distance;
            x += 2 * speed * cos;
            y += 2 * speed * sin;
            currentHp -= 0.005;
            if (Math.abs(x - temp.x) <= currentHp && Math.abs(y - temp.y) <= currentHp) {
                currentHp += temp.currentHp * 0.7;
                temp.currentHp = 0;
            }
        }
        else {
            movement();
        }
    }

    public boolean live(Set<Beings> unit){
        check();

        currentHp -= 0.01;
        age += 0.002;
        if(currentHp <= 0){
            return false;
        }
        else if(rand.nextInt((int)age + 1) == 7){
            return false;
        }
        else if(enemy(unit) != null){
            escape(unit);
            return live;
        }
        else if(currentHp < maxHp * 0.7){
            hunger(unit);
        }
        else if(currentHp >= maxHp * 0.5 && age >= 0.5 && age <= 1){
            reproduction(unit);
        }
        else {
            movement();
        }
        return true;
    }
}