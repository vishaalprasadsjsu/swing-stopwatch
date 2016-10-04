import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Dial implements Icon {

    private int size;
    private boolean threeLevels;
    private Color color;

    public Dial(int size, boolean threeLevels, Color color) {
        this.size = size;
        this.threeLevels = threeLevels;
        this.color = color;
    }

    public Color getColor() { return this.color; }

    public int getIconHeight(){
        return this.size;
    }

    public int getIconWidth(){
        return this.size;
    }

    public void paintIcon(Component c, Graphics g, int x, int y){
        Graphics2D g2 = (Graphics2D) g;



    }
}