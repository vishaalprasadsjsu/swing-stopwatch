import java.awt.*;
import java.time.*;

public class Stopwatch implements MoveableShape {

    private int x;
    private int y;
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

    public Stopwatch(int radius) {
        this.radius = radius;
        this.running = false;
        this.frozenDisp = false;
        this.startInstant = null;

        this.secDial = new Dial(radius * 2, true, Color.BLACK);
        this.minDial = new Dial((int) (radius * 0.8), false, Color.BLACK);
    }

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

    @Override
    public void draw(Graphics2D g2) {

        secDial.paintIcon(null, g2, 0, 0);
        secDial.setAngle(this.secsDisp * 6 - 90, g2);

        minDial.paintIcon(null, g2, (int) (radius * 0.6d), radius / 3);
        minDial.setAngle(this.minsDisp * 6 - 90, g2);

    }


}