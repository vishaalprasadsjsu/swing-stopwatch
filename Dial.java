import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Dial implements Icon {

    private int size;
    private boolean threeLevels;
    private Color color;

    //location of this dial, set when draw() is called
    private int x;
    private int y;

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

        this.x = x;
        this.y = y;

        double radius = size / 2.0d;

        // (Ax, Ay) represents the center of the circle
        // the ticks are NOT drawn from this point
        double Ax = x + size / 2.0d;
        double Ay = y + size / 2.0d;

        /*
            Represents the outer endpoint of each tick (the tick ends here)
            (along the circle of the clock)
         */
        double Bx, By;

        // Represents the inner endpoint of each tick (the tick starts here)
        double Cx = 0, Cy = 0;

        //LEVEL 1 & 2 ticks
        for (int deg = 0; deg < 360; deg += 6) {
            double angle = Math.toRadians(deg); //angle in radians
            double cosine = Math.cos(angle);
            double sine = Math.sin(angle);

            Bx = (radius * cosine) + Ax;
            By = (radius * sine) + Ay;

            if (deg % 30 == 0) { //LEVEL 1 ticks
                Cx = (0.80 * radius * cosine) + Ax;
                Cy = (0.80 * radius * sine) + Ay;
                g2.draw(new Line2D.Double(Cx, Cy, Bx, By));
            } else if (deg % 6 == 0) { //LEVEL 2 ticks
                Cx = (0.89 * radius * cosine) + Ax;
                Cy = (0.89 * radius * sine) + Ay;
                g2.draw(new Line2D.Double(Cx, Cy, Bx, By));
            }
        }

        // LEVEL 3 ticks
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

//        draw the number labels
        for (int i = 0; i < 12; i++) {
            int deg = i * 30 - 90;

            double angle = Math.toRadians(deg); //angle in radians
            double cosine = Math.cos(angle);
            double sine = Math.sin(angle);

            // (Bx, By) represents where we want to print the label
            Bx = (0.78 * radius * cosine) + Ax;
            By = (0.78 * radius * sine) + Ay;

            JLabel label = new JLabel();
            label.setText(Integer.toString(i * 5));
            label.setFont(g2.getFont());
//            label.setHorizontalAlignment(JLabel.CENTER);

            Dimension dim = label.getPreferredSize();
            Rectangle2D r = new Rectangle2D.Double(Bx, By, dim.getWidth(), dim.getHeight());

            label.setBounds((int) Bx, (int) By, (int) r.getWidth(), (int) r.getHeight());
            g2.translate(r.getX(), r.getY());
            label.paint(g2);
            g2.translate(-r.getX(), -r.getY());
        }

    }

    /**
     * @param angle in DEGREES where 0 is RIGHT, 90 is DOWN
     * @param g2    Graphics2D reference used for drawing
     */
    public void setAngle(double angleDeg, Graphics2D g2) {

        double angleRad = Math.toRadians(angleDeg);

        double radius = size / 2.0d;
        double Ax = x + size / 2.0d;
        double Ay = y + size / 2.0d;

        double Bx, By; //represents the endpoint of the arrow
        Bx = 0.95 * radius * Math.cos(angleRad) + Ax;
        By = 0.95 * radius * Math.sin(angleRad) + Ay;

        final double ARROW_ANGLE = Math.PI / 8;
        final double ARROW_LENGTH = radius * 0.08d; //should depend on size

        double x1 = Bx - ARROW_LENGTH * Math.cos(angleRad + ARROW_ANGLE);
        double y1 = By - ARROW_LENGTH * Math.sin(angleRad + ARROW_ANGLE);
        double x2 = Bx - ARROW_LENGTH * Math.cos(angleRad - ARROW_ANGLE);
        double y2 = By - ARROW_LENGTH * Math.sin(angleRad - ARROW_ANGLE);

        g2.setColor(this.color);
        g2.setStroke(new BasicStroke(1.4f));
        g2.draw(new Line2D.Double(Ax, Ay, Bx, By));
        g2.draw(new Line2D.Double(Bx, By, x1, y1));
        g2.draw(new Line2D.Double(Bx, By, x2, y2));
//        g2.draw(new Line2D.Double(x1, y1, x2, y2));


    }
}