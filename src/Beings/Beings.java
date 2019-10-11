package Beings;

import java.util.Set;

public abstract class Beings {

    double age = 0;
    double currentHp, maxHp;
    private boolean male;
    double x, y;

    Beings(boolean male, double maxHp, int x, int y){
     this.currentHp = maxHp * 0.8;
     this.maxHp = maxHp;
     this.x = x;
     this.y = y;
     this.male = male;
    }

    Beings search(Set<Beings> unit, Class searchingClass){
        int delta = Integer.MAX_VALUE;
        Beings searching = null;
        for(Beings temp: unit){
            if(temp.getClass().equals(searchingClass) && (int)(Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2))) < delta) {
                delta = (int)Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
                searching = temp;
            }
        }
        return searching;
    }

    Beings searchPartner(Set<Beings> unit, Class currentClass){ //поиск партнера
        int delta = Integer.MAX_VALUE;
        Beings searching = null;
        for(Beings temp: unit){
            if(temp.getClass().equals(currentClass) && male != temp.male && Math.sqrt(Math.pow(x - temp.x, 2)
                    + Math.pow(y - temp.y, 2)) < delta){
                delta = (int)Math.sqrt(Math.pow(x - temp.x, 2) + Math.pow(y - temp.y, 2));
                searching = temp;
            }
        }
        return searching;
    }

    void check(){
        if(x < 0)
            x = 3000;
        else if(x > 3000)
            x = 0;
        if(y < 0)
            y = 3000;
        else if(y > 3000)
            y = 0;
    }

    public abstract boolean live(Set<Beings> unit);

    public boolean isMale(){
        return male;
    }

    public int getX(){
        return (int)x;
    }

    public int getY(){
        return (int)y;
    }

    public double getCurrentHp(){
        return currentHp;
    }
}