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

    Main(){
        setBounds(0, 0, 1370, 727);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Panel panel = new Panel(set);
        add(panel);
        panel.setVisible(true);


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if(e.getKeyCode() == KeyEvent.VK_RIGHT && x < 1630 ){
                    x+=50;
                    panel.setxy(x, y);
                    repaint();
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT && x > 0){
                    x-= 50;
                    panel.setxy(x, y);
                    repaint();
                }
                else if(e.getKeyCode() == KeyEvent.VK_UP && y > 0){
                    y-=50;
                    panel.setxy(x, y);
                    repaint();
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN && y < 2273){
                    y+=50;
                    panel.setxy(x, y);
                    repaint();
                }
            }
        });
        while (true){
            repaint(16, 0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        Random num = new Random();
        num.setSeed(400);
        rand.setSeed(2);
        for(int i = 0, r = num.nextInt(800); i < r; i++){
            set.add(new Plants(new Random().nextBoolean(), rand.nextInt(35), rand.nextInt(3000), rand.nextInt(3000)));
        }
        num.setSeed(70);
        for(int i = 0, r = num.nextInt(300); i < r; i++){
            set.add(new Herbivorous(new Random().nextBoolean(), rand.nextInt(50), rand.nextInt(3000), rand.nextInt(3000),
                    rand.nextDouble() * 10 + 1));
        }
        num.setSeed(40);
        for(int i = 0, r = num.nextInt(80); i < r; i++){
            set.add(new Carnivorous(new Random().nextBoolean(), rand.nextInt(65), rand.nextInt(3000), rand.nextInt(3000),
                    rand.nextDouble() * 10 + 2));
        }

        new Main();
    }
}

class Panel extends JPanel{
    int x, y;
    Color red;
    Set<Beings> set;

    Panel(Set<Beings> set){
        red = new Color(0xFF1109);
        this.set = set;
    }

    void setxy(int xWindow, int yWindow){
        x = xWindow;
        y = yWindow;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(x == 0){
            g.setColor(red);
            g.drawLine(1, 0, 1, getHeight());
        }
        else if(x > 1630){
            g.setColor(red);
            g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
        }
        if(y == 0){
            g.setColor(red);
            g.drawLine(1, 1, getWidth() - 1, 1);
        }
        else if(y > 2273){
            g.setColor(red);
            g.drawLine(0, getHeight() - 1 ,getWidth(), getHeight() - 1);
        }
        for(Beings temp: set){
            if(!temp.live(set)){
                set.remove(temp);
            }
            else if (temp.getX() > x && temp.getX() < getWidth() + x && temp.getY() > y && temp.getY() < getHeight() + y){
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
                g.fillOval(temp.getX() - x, temp.getY() - y, (int)(temp.getCurrentHp() / 2), (int)(temp.getCurrentHp() / 2));
            }
        }
    }
}