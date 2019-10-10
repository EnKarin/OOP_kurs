package Main;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import Beings.*;

public class Main extends JFrame{
    int x = 0, y = 0;
    static Set<Beings> set = Collections.newSetFromMap(new ConcurrentHashMap<>());
    Color red;

    Main(){
        setBounds(0, 0, 1370, 727);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if(e.getKeyCode() == KeyEvent.VK_RIGHT && x > 0){
                    x--;
                    repaint();
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT && x < 5630){
                    x++;
                    repaint();
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN && y > 0){
                    y--;
                    repaint();
                }
                else if(e.getKeyCode() == KeyEvent.VK_UP && y < 6273){
                    y++;
                    repaint();
                }
                /*
                else if(e.getKeyCode() == KeyEvent.VK_SUBTRACT){
                    repaint();
                }
                else if(e.getKeyCode() == KeyEvent.VK_ADD){
                    repaint();
                }*/
            }
        });

        while (true){
            repaint();
            try{
                Thread.sleep(16);
            }
            catch (InterruptedException e){
                Thread.interrupted();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //for debug
        System.out.println("x = " + x + "\tsize = " + set.size() + "\ty = " + y);

        if(x == 0){
            g.setColor(red);
            g.drawLine(1, 0, 1, getHeight());
        }
        else if(x == 5630){
            g.setColor(red);
            g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
        }
        if(y == 0){
            g.setColor(red);
            g.drawLine(0, 1, getWidth(), 1);
        }
        else if(y == 6273){
            g.setColor(red);
            g.drawLine(0, getHeight() - 1 ,getWidth(), getHeight() - 1);
        }
        for(Beings temp: set){
            if(!temp.live(set)){
                set.remove(temp);
            }
            else {
                if(temp.getClass().equals(Plants.class)){
                    if(temp.isMale()){
                        g.setColor(new Color(50, 177, 24));
                    }
                    else{
                        g.setColor(new Color(156, 255, 17, 221));
                    }
                }
                else if(temp.getClass().equals(Herbivorous.class)){
                    if(temp.isMale()){
                        g.setColor(new Color(0x216D89));
                    }
                    else{
                        g.setColor(new Color(0x85247385, true));
                    }
                }
                else {
                    if(temp.isMale()){
                        g.setColor(new Color(0x7C1923));
                    }
                    else{
                        g.setColor(new Color(0xAE7A282D, true));

                    }
                }
                g.fillOval(temp.getX() + x, temp.getY() + y, (int)temp.getCurrentHp(), (int)temp.getCurrentHp());
            }
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        rand.setSeed(2);
        for(int i = 0; i < 150; i++){
            set.add(new Plants(rand.nextInt(50), rand.nextInt(700), rand.nextInt(700)));
        }
        for(int i = 0; i < 70; i++){
            set.add(new Herbivorous(rand.nextInt(200), rand.nextInt(700), rand.nextInt(700),
                    rand.nextInt() * 2 / Integer.MAX_VALUE));
        }
        for(int i = 0; i < 50; i++){
            set.add(new Carnivorous(rand.nextInt(200), rand.nextInt(700), rand.nextInt(700),
                    rand.nextInt() * 3));
        }

        new Main();
    }
}
