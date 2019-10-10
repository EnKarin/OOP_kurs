package Main;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Random;
import Beings.*;

public class Main extends JFrame{
    int x = 3500, y = 3500;
    static ArrayDeque<Beings> units;
    static Beings temp;

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

        repaint(16, 0, 0, getWidth(), getHeight());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(Iterator<Beings> it = units.iterator(); it.hasNext(); temp = it.next()){
            if(!temp.live(units)){
                it.remove();
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
                g.fillOval(temp.getX(), temp.getY(), (int)temp.getCurrentHp(), (int)temp.getCurrentHp());
            }
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        rand.setSeed(2);
        units = new ArrayDeque<Beings>();
        for(int i = 0; i < 6; i++){
            units.add(new Plants(rand.nextInt(50), rand.nextInt(7000), rand.nextInt(7000)));
        }
        for(int i = 0; i < 4; i++){
            units.add(new Herbivorous(rand.nextInt(200), rand.nextInt(7000), rand.nextInt(7000),
                    rand.nextInt() * 2 / Integer.MAX_VALUE));
        }
        for(int i = 0; i < 2; i++){
            units.add(new Carnivorous(rand.nextInt(200), rand.nextInt(7000), rand.nextInt(7000),
                    rand.nextInt() * 3));
        }

        new Main();
    }
}
