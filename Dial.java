import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * A Dial to be used in a Stopwatch
 * Consists of a hand of a custom color
 */
public class Dial implements Icon {

    private int size;
    private boolean threeLevels;
    private Color color;
    private static final Color TICK_COLOR = Color.BLACK;

    //location of this dial, set when draw() is called
    private int x;
    private int y;

    /**
     * Creates a dial of the given parameters
     *
     * @param size        DIAMETER of the Dial
     * @param threeLevels whether to paint the third level of ticks
     * @param color       of the hand
     */
    public Dial(int size, boolean threeLevels, Color color) {
        this.size = size;
        this.threeLevels = threeLevels;
        this.color = color;
    }

    /**
     * @return the color of this Dial's hands
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return the height of this Dial
     */
    public int getIconHeight() {
        return this.size;
    }

    /**
     * @return the width of this Dial
     */
    public int getIconWidth() {
        return this.size;
    }

    //x,y is 0,0 for larger one
    //x,y is drawn in the middle for the smaller one

    /**
     * Paints this Dial with the given parameters
     *
     * @param c null okay
     * @param g Graphics for painting
     * @param x TOP LEFT X-coordinate location of the icon
     * @param y TOP LEFT Y-coordinate location of the icon
     */
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

        g2.setColor(TICK_COLOR);

        //LEVEL 1 & 2 ticks
        for (int deg = 0; deg < 360; deg += 6) {
            double angle = Math.toRadians(deg); //angle in radians
            double cosine = Math.cos(angle);
            double sine = Math.sin(angle);

            Bx = (radius * cosine) + Ax;
            By = (radius * sine) + Ay;

            if (deg % 30 == 0) { //LEVEL 1 ticks
                Cx = (0.82 * radius * cosine) + Ax;
                Cy = (0.82 * radius * sine) + Ay;
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

            MultiLineString mMLS = new MultiLineString();
            mMLS.setText(Integer.toString(i * 5));


            if (this.threeLevels) {
                // (Bx, By) represents where we want to print the label
                Bx = (0.75 * radius * cosine) + Ax;
                By = (0.75 * radius * sine) + Ay;

                mMLS.setSize(MultiLineString.NORMAL);
                Rectangle2D bounds = mMLS.getBounds(g2);
                int wd = (int) bounds.getWidth();
                int ht = (int) bounds.getHeight();
                mMLS.draw(g2, new Rectangle2D.Double(Bx - (wd * 0.5d), By - (ht * 0.5d), wd, ht));
            } else {
                // (Bx, By) represents where we want to print the label
                Bx = (0.68 * radius * cosine) + Ax;
                By = (0.68 * radius * sine) + Ay;

                mMLS.setSize(MultiLineString.SMALL);
                Rectangle2D bounds = mMLS.getBounds(g2);

                int wd = (int) bounds.getWidth();
                int ht = (int) bounds.getHeight();
                mMLS.draw(g2, new Rectangle2D.Double(Bx - (wd * 0.5d), By - (ht * 0.5d), wd, ht));
            }
        }
    }

    /**
     * Draws the Dial's hand to represent the current Degree
     *
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

        /*
            The following logic of code that is used to draw the arrows
            is inspired by the work of Cay Horstmann in the program
            Violet - A program for editing UML diagrams
         */
        final double ARROW_TIP_ANGLE = Math.PI / 8;
        final double ARROW_TIP_LENGTH = radius * 0.08d; //should depend on size

        double x1 = Bx - ARROW_TIP_LENGTH * Math.cos(angleRad + ARROW_TIP_ANGLE);
        double y1 = By - ARROW_TIP_LENGTH * Math.sin(angleRad + ARROW_TIP_ANGLE);
        double x2 = Bx - ARROW_TIP_LENGTH * Math.cos(angleRad - ARROW_TIP_ANGLE);
        double y2 = By - ARROW_TIP_LENGTH * Math.sin(angleRad - ARROW_TIP_ANGLE);

        g2.setColor(this.color);
        g2.setStroke(new BasicStroke(1.4f));
        g2.draw(new Line2D.Double(Ax, Ay, Bx, By));
        g2.draw(new Line2D.Double(Bx, By, x1, y1));
        g2.draw(new Line2D.Double(Bx, By, x2, y2));
    }
}