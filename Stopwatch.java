import java.awt.*;
import java.time.*;

/**
 * A Stopwatch that keeps track of time up to 60 hours
 */
public class Stopwatch implements MoveableShape {

    private int radius;
    private boolean running;
    private boolean frozenDisp;
    private Instant startInstant;
    private Instant pauseInstant;
    private long pausedMillis;
    private long totalMillis;

    private Dial secDial;
    private Dial minDial;

    private double secsDisp;
    private double minsDisp;

    /**
     * Creates a new Stopwatch with the supplied radius
     *
     * @param radius of the larger dial
     */
    public Stopwatch(int radius) {
        this.radius = radius;
        this.running = false;
        this.frozenDisp = false;
        this.startInstant = null;

        this.secDial = new Dial(radius * 2, true, Color.RED);
        this.minDial = new Dial((int) (radius * 0.8), false, Color.BLUE);
    }

    /**
     * Simulates the top button being pressed of a stopwatch
     * Namely, it pauses or resumes the stopwatch
     */
    public void topButtonPressed() {
        if (startInstant == null) //starting from a reset state
            startInstant = Instant.now();
        else if (running) //running, we need to pause
            this.pauseInstant = Instant.now();
        else if (!running) { //paused, now resuming
            pausedMillis += Duration.between(pauseInstant, Instant.now()).toMillis();
            pauseInstant = null;
        }

        this.running = !this.running;
    }

    /**
     * Simulates the second button being pressed of a stopwatch
     * Namely, if the watch is paused, it will be reset.
     * Otherwise, the display will freeze
     */
    public void secondButtonPressed() {
        if (this.running && this.frozenDisp)
            this.frozenDisp = false;

        else if (this.running && !this.frozenDisp)
            this.frozenDisp = true;

        else if (!this.running) {  // RESET
            this.running = false;
            this.frozenDisp = false;
            this.startInstant = null;
            this.pausedMillis = 0;
            this.totalMillis = 0;
            this.secsDisp = 0;
            this.minsDisp = 0;
        }
    }

    /**
     * Updates the time of this stopwatch object
     * Accounts for all time frozen
     * This method does nothing if the watch is frozen or not running
     */
    @Override
    public void move() {
        if (running && !frozenDisp) {
            //total number of seconds
            this.totalMillis = Duration.between(startInstant, Instant.now()).toMillis();

            long runningMillis = totalMillis - pausedMillis;
            double secs = (double) runningMillis / 1000.0d;
            double mins = ((secs / 60.0d) % 60);
            secs %= 60; //mins and secs are now in range 0-60

            this.minsDisp = mins;
            this.secsDisp = secs % 60;
        }
        //else: NOT running --> do nothing
    }

    /**
     * Updates the UI with the current time stored in this stopwatch object
     * If the watch's current state is paused or frozen this method will
     * appear to do nothing -- it is still redrawing the watch
     *
     * @param g2 Graphics2D used for drawing
     */
    @Override
    public void draw(Graphics2D g2) {

        secDial.paintIcon(null, g2, 0, 0);
        secDial.setAngle(this.secsDisp * 6 - 90, g2);

        minDial.paintIcon(null, g2, (int) (radius * 0.6d), radius / 3);
        minDial.setAngle(this.minsDisp * 6 - 90, g2);
    }
}