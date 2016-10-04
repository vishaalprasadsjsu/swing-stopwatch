import java.time.*;

public class Stopwatch implements MoveableShape {

    private int x;
    private int y
    private int radius;
    private boolean running;
    private boolean frozen;
    private Instant startTime;

    public Stopwatch(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.running = false;
        this.frozen = false;
        this.startTime = null;
    }

    public Stopwatch(int radius) {
        this(0, 0, radius); //// TODO: 10/3/16 assume x=0 and y=0?
    }

    public void topButtonPressed() {
        if (startTime == null) //starting from a reset state
            startTime = Instant.now();

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
            int minutes; //the number of minutes to display (0-60)
            int seconds; //number of seconds to display (0-60)

            //total number of seconds
            seconds = Duration.between(startTime, Instant.now());

            //total number of minutes
            minutes = seconds / 60;
            minutes %= 60; //just in case it runs for >1 hour

            //just seconds, (between 0-60)
            seconds %= 60;
        }

        //else: NOT running --> do nothing
    }

    @Override
    public void draw(Graphics2D g2) {
        //create all the elements


        //g2.draw(<each element>)

    }

    //for MoveableShape implement METHODS: draw() and move()


}