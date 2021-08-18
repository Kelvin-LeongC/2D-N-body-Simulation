package view_classes;

import entities.Star;
import entities.Background;

import javax.swing.*;
import java.awt.*;

public class Animation extends JPanel {
    private final Background background;
    private final int canvasWidth;
    private final int canvasHeight;
    private final Star[] starsArray;
    private final double distanceScale;

    public Animation(Background background, int canvasWidth, int canvasHeight, Star[] starsArray, double distanceScale){
        this.background = background;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.starsArray = starsArray;
        this.distanceScale = distanceScale;
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        background.draw(g2d);
        g2d.translate(canvasWidth/2, canvasHeight/2);
        g2d.scale(1, -1);
        for(Star star: starsArray){
            star.draw(g2d, distanceScale);
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return(new Dimension(canvasWidth, canvasHeight));
    }
}
