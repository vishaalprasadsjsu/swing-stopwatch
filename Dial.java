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

    public Color getColor() {
        return this.color;
    }

    public int getIconHeight() {
        return this.size;
    }

    public int getIconWidth() {
        return this.size;
    }

    //x,y is 0,0 for larger one
    //x,y is drawn in the middle for the smaller one
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;

        double radius = size / 2.0d;

        // (Ax, Ay) represents the center of the circle
        // the ticks are NOT drawn from this point
        double Ax = size / 2.0d;
        double Ay = size / 2.0d;

        /*
            Represents the outer endpoint of each tick (the tick ends here)
            (along the circle of the clock)
         */
        double Bx, By;  //represents the outer endpoint of each tick

        /*
            Represents the inner endpoint of each tick (the tick starts here)
         */
        double Cx = 0, Cy = 0;

        //LEVEL 1 & 2 ticks
        for (int deg = 0; deg < 360; deg += 6) {
            double angle = Math.toRadians(deg); //angle in radians
            double cosine = Math.cos(angle);
            double sine = Math.sin(angle);

            Bx = (radius * cosine) + Ax;
            By = (radius * sine) + Ay;

            if (deg % 30 == 0) { //LEVEL 1 ticks
                System.out.println("LEVEL 1 DEG: [" + deg + "]");
                Cx = (0.80 * radius * cosine) + Ax;
                Cy = (0.80 * radius * sine) + Ay;
                g2.draw(new Line2D.Double(Cx, Cy, Bx, By));
            } else if (deg % 6 == 0) { //LEVEL 2 ticks
//                    System.out.println("LEVEL 2 DEG: [" + deg + "]");
                Cx = (0.89 * radius * cosine) + Ax;
                Cy = (0.89 * radius * sine) + Ay;
                g2.draw(new Line2D.Double(Cx, Cy, Bx, By));
            }
        }

        if (this.threeLevels) {
            for (double deg = 0; deg < 360; deg += 1.2d) {
                double angle = Math.toRadians(deg); //angle in radians
                double cosine = Math.cos(angle);
                double sine = Math.sin(angle);

                Bx = (radius * cosine) + Ax;
                By = (radius * sine) + Ay;

                Cx = (0.95 * radius * cosine) + Ax;
                Cy = (0.95 * radius * sine) + Ay;

                g2.draw(new Line2D.Double(Cx, Cy, Bx, By));
            }
        }
    }

    public void setAngle(double angle){
        
    }
}