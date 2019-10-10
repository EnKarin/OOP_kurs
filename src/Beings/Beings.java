package Beings;

import java.util.ArrayDeque;
import java.util.Random;

public abstract class Beings {

    double age = 0;
    double currentHp, maxHp;
    boolean male;
    Random rand = new Random();
    int x, y;

    Beings(double maxHp, int x, int y){
     this.currentHp = maxHp * 0.1;
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