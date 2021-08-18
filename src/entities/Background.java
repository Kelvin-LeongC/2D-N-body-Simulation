package entities;

import java.awt.*;

public class Background {
    private final int minX, maxX, minY, maxY;
    private final Color backgroundColor;

    public Background(int startX, int startY, int width, int height, Color color){
        this.minX = startX;
        this.maxX = startX + width;
        this.minY = startY;
        this.maxY = startY + height;
        this.backgroundColor = color;

    }

    public void draw(Graphics g){
        g.setColor(backgroundColor);
        g.fillRect(minX, minY, maxX-minX, maxY-minY);
    }
}
