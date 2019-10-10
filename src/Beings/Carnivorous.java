package Beings;

import java.util.ArrayDeque;

public class Carnivorous extends Animals{

    public Carnivorous(int maxHp, int x, int y, double speed){
        super(maxHp, x, y, speed);
    }

    void reproduction(ArrayDeque<Beings> unit){ //размножение хищников
        double sin, cos;
        Carnivorous mam = (Carnivorous) search(unit, Carnivorous.class);
        if(mam != null) {
            int distance = (int) Math.sqrt(Math.pow(x - mam.x, 2) + Math.pow(y - mam.y, 2));
            sin = Math.abs(y - mam.y) / distance;
            cos = Math.abs(x - mam.x) / distance;
            x += speed * cos;
            y += speed * sin;
            if (Math.abs(x - mam.x) <= 3 && Math.abs(y - mam.y) <= 3) {
                currentHp -= maxHp * 0.15;
                goal = true;
                unit.add(new Carnivorous((int) (maxHp * 0.8 + rand.nextInt((int) Math.abs(maxHp
                        - mam.maxHp))), x + 15, y + 15, (int) (speed * 0.8 + rand.nextInt((int) (Math.abs(speed
                        - mam.speed))))));
            }
        }
    }

    private Herbivorous hunger(ArrayDeque<Beings> unit){ //поиск еды
        Herbivorous temp = (Herbivorous) search(unit, Herbivorous.class);
        if(temp != null) {
            int distance = (int) Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
            if (distance <= 300) {
                return temp;
            }
        }
        return null;
    }

    private void pursuit(ArrayDeque<Beings> unit){ //преследование добычи
        Herbivorous hunger = hunger(unit);
        if(hunger != null) {
            int distance;
            double sin, cos;
            distance = (int) Math.sqrt(Math.pow(x - hunger.x, 2) + Math.pow(y - hunger.y, 2));
            sin = Math.abs(y - hunger.y) / distance;
            cos = Math.abs(x - hunger.x) / distance;
            x += 2 * speed * cos;
            y += 2 * speed * sin;
            currentHp -= 0.002;
            if(Math.abs(x - hunger.x) <= 3 && Math.abs(y - hunger.y) <= 3){
                currentHp += hunger.currentHp * 0.8;
                hunger.currentHp = 0;
            }
        }
    }

    public boolean live(ArrayDeque<Beings> unit){
        check();
        currentHp -= 0.001;
        age += 0.002;
        if(currentHp <= 0){
            return false;
        }
        if(currentHp < maxHp * 0.7){
            pursuit(unit);
        }
        if(currentHp >= maxHp * 0.8 && age >= 5 && age <= 6 && !goal){
            reproduction(unit);
        }
        return true;
    }
}
