package Beings;

import java.util.ArrayDeque;
import java.util.Random;

abstract class Beings {

    double age = 0;
    double currentHp, maxHp;
    boolean male;
    Random rand = new Random();
    int x, y;

    Beings(double currentHp, double maxHp, int x, int y){
     this.currentHp = currentHp;
     this.maxHp = maxHp;
     this.x = x;
     this.y = y;
     rand.setSeed(0);
     male = rand.nextBoolean();
    }

    Beings search(ArrayDeque<Beings> unit, Class currentClass){ //поиск партнера
        boolean m = currentClass.equals(Plants.class);
        int delta = Integer.MAX_VALUE;
        Beings searching = null;
        for(Beings temp: unit){
            if(temp.getClass().equals(currentClass) && m == temp.male && Math.sqrt(Math.pow(x - temp.x, 2)
                    + Math.pow(y - temp.y, 2)) < delta){
                delta = (int)Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
                searching = temp;
            }
        }
        return searching;
    }

    abstract boolean live(ArrayDeque<Beings> unit);

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public double getCurrentHp(){
        return currentHp;
    }

}

abstract class Animals extends Beings{

    int speed;
    double full = 40;

    Animals(double currentHp, double maxHp, int x, int y, int speed){
        super(currentHp, maxHp, x, y);
        this.speed = speed;
    }
}

class Plants extends Beings{

    Plants(double currentHp, double maxHp, int x, int y){
        super(currentHp, maxHp, x, y);
    }

    boolean live(ArrayDeque<Beings> unit){
        if(currentHp <= 0 ){
            return false;
        }
        int distance;
        Plants dad = (Plants) search(unit, Plants.class);
        age += 0.002;
        if(currentHp < maxHp) {
            currentHp += 0.008;
        }
        if(age > 3 && age < 5 && currentHp > maxHp / 1.5){
            distance = (int)Math.sqrt(Math.pow(x - dad.x, 2) + Math.pow(y - dad.y, 2));
            if(rand.nextInt(distance) == 1){
                unit.add(new Plants(1, maxHp * 0.8 + rand.nextInt((int)Math.abs(maxHp - dad.maxHp)),
                                x - 200 + rand.nextInt(400), y - 200 + rand.nextInt(400)));
            }
        }
        return true;
    }
}

class Herbivorous extends Animals{

    Herbivorous(int currentHp, int maxHp, int x, int y, int speed){
        super(currentHp, maxHp, x, y, speed);
    }

    void reproduction(ArrayDeque<Beings> unit){ //размножение травоядных
        double sin, cos;
        Herbivorous mam = (Herbivorous) search(unit, Herbivorous.class);
        int distance;
        while(x != mam.x && y != mam.y && enemy(unit) == null){
            distance = (int)Math.sqrt(Math.pow(x - mam.x, 2) + Math.pow(y - mam.y, 2));
            sin = Math.abs(y - mam.y) / distance;
            cos = Math.abs(x - mam.x) / distance;
            x += speed * cos;
            y += speed * sin;
        }
        if(enemy(unit) != null){
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

class Carnivorous extends Animals{

    Carnivorous(int currentHp, int maxHp, int x, int y, int speed){
        super(currentHp, maxHp, x, y, speed);
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
        unit.add(new Carnivorous((int)(0.1 * maxHp), (int)(maxHp * 0.8 + rand.nextInt((int)Math.abs(maxHp
                - mam.maxHp))), x + 15, y + 15, (int)(speed * 0.8 + rand.nextInt((Math.abs(speed
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