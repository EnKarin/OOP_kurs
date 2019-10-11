package Beings;

import java.util.Set;

public abstract class Beings {

    double age = 0;
    double currentHp, maxHp;
    boolean male;
    int x, y;

    Beings(boolean male, double maxHp, int x, int y){
     this.currentHp = maxHp * 0.4;
     this.maxHp = maxHp;
     this.x = x;
     this.y = y;
     this.male = male;
    }

    Beings search(Set<Beings> unit, Class currentClass){ //поиск партнера
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

    public abstract boolean live(Set<Beings> unit);

    void check(){
        if(x < 0)
            x = 7000;
        else if(x > 7000)
            x = 0;
        if(y < 0)
            y = 7000;
        else if(y > 7000)
            y = 0;
    }

    public boolean isMale(){
        return male;
    }

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