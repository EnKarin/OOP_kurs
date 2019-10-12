package Beings;

import java.util.Random;
import java.util.Set;

public class Carnivorous extends Animals{
    private int count = 0;

    public Carnivorous(boolean male, int maxHp, int x, int y, double speed){
        super(male, maxHp, x, y, speed);
    }

    @Override
    Beings search(Set<Beings> unit, Class searchingClass) {
        int delta = Integer.MAX_VALUE;
        Beings searching = null;
        for(Beings temp: unit){
            if(temp.getClass().equals(searchingClass) && (int)(Math.sqrt(Math.pow(x - temp.x, 2) +
                    Math.pow(y - temp.y, 2))) < delta && temp.currentHp <= currentHp) {
                delta = (int)Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
                searching = temp;
            }
        }
        return searching;
    }

    @Override
    void reproduction(Set<Beings> unit){ //размножение хищников
        Random rand = new Random();
        final Carnivorous mam = (Carnivorous) searchPartner(unit, Carnivorous.class);
        if(mam != null) {
            int distance = (int) Math.sqrt(Math.pow(x - mam.x, 2) + Math.pow(y - mam.y, 2));
            final double sin = (mam.y - y) / distance;
            final double cos = (mam.x - x) / distance;
            x += speed * cos;
            y += speed * sin;
            if (Math.abs(x - mam.x) <= 3 && Math.abs(y - mam.y) <= 3) {
                count++;
                currentHp -= currentHp * 0.15;
                unit.add(new Carnivorous(rand.nextBoolean(),(int)(maxHp * 0.75
                        + rand.nextInt((int) Math.abs(maxHp - mam.maxHp) + 1)), (int)(x + 15), (int)(y + 15),
                        speed * 0.7 + rand.nextInt((int)(Math.abs(speed - mam.speed)) + 1)));
            }
        }
        else {
            movement();
        }
    }

    private Herbivorous hunger(Set<Beings> unit){ //поиск еды
        Herbivorous temp = (Herbivorous) search(unit, Herbivorous.class);
        if(temp != null) {
            int distance = (int) Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
            if (distance <= 500) {
                return temp;
            }
        }
        return null;
    }

    private void pursuit(Set<Beings> unit){ //преследование добычи
        final Herbivorous hunger = hunger(unit);
        if(hunger != null) {
            final int distance = (int) Math.sqrt(Math.pow(x - hunger.x, 2) + Math.pow(y - hunger.y, 2));
            final double sin = (hunger.y - y) / distance;
            final double cos = (hunger.x - x) / distance;
            x += 1.3 * speed * cos;
            y += 1.3 * speed * sin;
            currentHp -= currentHp * 0.002;
            if(Math.abs(x - hunger.x) <= 3 && Math.abs(y - hunger.y) <= 3){
                if(currentHp + hunger.currentHp > maxHp){
                    currentHp = maxHp;
                }
                else {
                    currentHp += hunger.currentHp;
                }
                hunger.currentHp = 0;
            }
        }
        else {
            movement();
        }
    }

    @Override
    public boolean live(Set<Beings> unit){
        Random rand = new Random();
        check();

        currentHp -= currentHp * 0.002;
        age += 0.002;
        if(currentHp <= 0.0001){
            return false;
        }
        else if(rand.nextInt((int)age * 100 + 1) == 500){
            return false;
        }
        else if(currentHp < maxHp * 0.7){
            pursuit(unit);
        }
        else if(currentHp >= maxHp * 0.5 && age >= 0.5 && age <= 0.6 && count < 3){
            reproduction(unit);
        }
        else {
            movement();
        }
        return true;
    }
}
