import java.awt.*;
import java.time.*;

public class Stopwatch implements MoveableShape {

    private int x;
    private int y;
    private int radius;
    private boolean running;
    private boolean frozen;
    private Instant startTime;
    private long pauseDuration;

    private int currentMins;
    private long currentSecs;

    public Stopwatch(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.running = false;
        this.frozen = false;
        this.startTime = null;
    }

    public Stopwatch(int radius) {
        this(0, 0, radius);

    public void topButtonPressed() {
        if (startTime == null) //starting from a reset state
            startTime = Instant.now();

        if (running) {

        }

        this.running = !this.running;
    }

    public void secondButtonPressed() {
        if (this.running && this.frozen)
            this.frozen = false;

        else if (this.running && !this.frozen)
            this.frozen = true;

        else if (!this.running) { //not running --> RESET
            this.running = false;
            this.frozen = false;
            this.startTime = null;
        }
    }

    @Override
    public void move() {
        if (running) {
            //total number of seconds
            this.currentSecs = Duration.between(startTime, Instant.now()).getSeconds();

            //total number of minutes
            this.currentMins = (int) (this.currentSecs / 60);
            this.currentMins %= 60; //just in case it runs for >1 hour

            //just seconds, (between 0-60)
            this.currentSecs %= 60;
        } else {
            

        }


        //else: NOT running --> do nothing
    }

    @Override
    public void draw(Graphics2D g2) {
//        Dial minDial;
//        Dial secDial;

        if (running)
            System.out.println(this.currentMins + ":" + this.currentSecs);

    }

    //for MoveableShape implement METHODS: draw() and move()


}