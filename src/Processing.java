import processing.core.PApplet;

public class Processing extends PApplet {

    public static void main(String args[]) {
        PApplet.main(new String[]{"--present", "Processing"});
    }

    int nParticles = 1000;
    MovingCircle[] myCircleArray = new MovingCircle[nParticles];
    float totalSteps = 0;
    int stepsRun = 0;
    int particularSteps = 0;
    boolean edgeReached = false;
    float seconds;
    float timeToEdge;
    int left, right, up, down;
    boolean saved = false;
    boolean pause = false;
    float xVal = 0; float yVal = 0;
    int numbSelect;
    double probNorth, probSouth, probEast, probWest;

    public void setup() {
        // Keep track of steps made in x direction.
        left = 0;
        right = 0;
        up = 0;
        down = 0;
        numbSelect = 4;
        probNorth = 1.0;
        probSouth = 1.0;
        probEast = 1.0;
        probWest = 1.0;
        size(500, 500);
        frameRate(1000);
        noStroke();
        smooth();
        for (int i = 0; i < myCircleArray.length; i++) {
            float r1 = random(150, 200);
            float r2 = random(100, 300);

            myCircleArray[i] = new MovingCircle(width / 2, height / 2, 2);

        }
    }


    public void draw() {
        seconds = (millis() / 1000);
        stepsRun++;
        if(stepsRun == 1000){pause = !pause;}
        if (pause)
            noLoop();
        else
            loop();

        background(color(244, 255, 255));

        // Viss i er lik 5, øker verdien. Vi følger altså en spesifikk partikkel.
        for (int i = 0; i < myCircleArray.length; i++) {
            if (i == 0) {
                particularSteps++;
            }
            // Viss i er like 5, farger vi den blå og legger tekst til - for å kunne se den enklere.
            myCircleArray[i].move();
            if (i == 0) {
                myCircleArray[i].distinguish(1);
                xVal = myCircleArray[i].getX();
                yVal = myCircleArray[i].getY();
            } else if (i == 19) {
                myCircleArray[i].distinguish(2);
            } else if (i == 420) {
                myCircleArray[i].distinguish(3);
            } else myCircleArray[i].display();

        }
        if (edgeReached) {
            if (!saved) {
                timeToEdge = seconds;
                saved = true;
            }
            fill(0);
            textSize(15);
            text("Time to edge: " + timeToEdge + "s", 5, 115);

        }
        fill(0);
        textSize(18);
        float ksteps = totalSteps/1000;
        String skk = nf(ksteps, 1, 1);
        if(ksteps >= 1000){
            ksteps = ksteps/1000;
            String sk = nf(ksteps, 1, 1);
            text("Total steps made: " + sk + " M", 10, height-10);
        } else text("Total steps made: " + skk + " K", 10, height-10);

        double totDist = getSquaredDist();
        String sqrDist = Double.toString(totDist);

        double exDiffCoeff = getExpectedDiffCoeff(1);
        String diffCoeff = Double.toString(exDiffCoeff);

        double estDiff = getEstimatedDiffusion();
        String estDiffStr = Double.toString(estDiff);

        double exDist = getExpectedDistance(stepsRun);
        String exDistStr = Double.toString(exDist);

        text("X: " + xVal + "   Y: " + (height-yVal), 5, 15);
        text("Total dist: " + sqrDist, 5, 35);
        text("Ex dist: " + exDistStr, 5, 55);
        text("ExDiffCoeff: " + diffCoeff, 5, 75);
        text("EstDiff: " + estDiffStr, 5, 95);
    }

    // Pause, un-pause the drawing loop.
    public void keyPressed() {
        pause = !pause;
        if (pause)
            noLoop();
        else
            loop();
    }

    public double getSquaredDist()
    {
        double totalSquaredDistance = 0;
        float origoX = width/2;
        float origoY = height/2;
        float xDist = 0;
        float yDist = 0;

        for (int i = 0; i < myCircleArray.length; i++)
        {
            MovingCircle circle = myCircleArray[i];
            if(circle.getX() > origoX){
                xDist = circle.getX() - origoX;
            }
            else xDist = (circle.getX()*-1) + origoX;
            if(circle.getY() > origoY){
                yDist = circle.getY() - origoY;
            }
            else yDist = (circle.getY()*-1) + origoY;

            float distance = (xDist*xDist + yDist*yDist);

            totalSquaredDistance += distance;
        }
        return totalSquaredDistance/nParticles;
    }


    public double getExpectedDistance(int steps)
    {
        double dist = Math.sqrt(2);
        return steps*((probNorth/numbSelect)+(probEast/numbSelect)+(probSouth/numbSelect)+(probWest/numbSelect));
    }

    public double getExpectedDiffCoeff(int steps)
    {
        double squaredDist = getExpectedDistance(steps);
        int dimensions = 2;
        return 1/((2*dimensions*steps)/squaredDist);
    }

    public double getEstimatedDiffusion()
    {
        double squaredDist = getSquaredDist();
        int dimensions = 2;
        return 1/((2*dimensions*stepsRun)/squaredDist);
    }










    class MovingCircle {
        float x;
        float y;
        float xSpeed;
        float ySpeed;
        float circleSize;

        MovingCircle(float xpos, float ypos, float csize) {
            x = xpos;
            y = ypos;

            circleSize = csize;
        }

        public float getX(){ return x; }
        public float getY(){ return y; }

        void move() {
            // Variable holding total number of moves/steps made.
            totalSteps++;

            float movement = 1;
            float r = random(0, numbSelect);
            double probUp = probNorth;
            double probRight = (probUp + probEast);
            double probDown = (probRight + probSouth);
            double probLeft = (probDown + probWest);


            //OPP
            if (r >= 0 && r < probUp) {
                up++;
                y = y + movement;
                if (y == height) {
                    if (!edgeReached) {
                        println(seconds);
                    }
                    edgeReached = true;
                    y = y - movement;
                }
            }
            //HØGRE
            if (r >= probUp && r < probRight) {
                right++;
                x = x + movement;
                if (x == width) {
                    if (!edgeReached) {
                        println(seconds);
                    }
                    edgeReached = true;
                    x = x - movement;
                }
            }
            //NED
            if (r >= probRight && r < probDown) {
                down++;
                y = y - movement;
                if (y == 0) {
                    if (!edgeReached) {
                        println(seconds);
                    }
                    edgeReached = true;
                    y = y + movement;
                }
            }
            //VENSTRE
            if (r >= probDown && r < probLeft) {
                left++;
                x = x - movement;
                if (x == 0) {
                    if (!edgeReached) {
                        println(seconds);
                    }
                    edgeReached = true;
                    x = x + movement;
                }
            }

/*
            if(x >= width) {
                x -= movement;
            }
            else if(x <= width) {
                x += movement;
            }

            if(y >= height) {
                y -= movement;
            }
            else if(y <= height) {
                y += movement
                ;
            }*/

        }
/*
        void checkCollisions() {

            float r = circleSize / 2;

            if ((x < r) || (x > width - r)) {
                xSpeed = -xSpeed;
            }

            if ((y < r) || (y > height - r)) {
                ySpeed = -ySpeed;
            }

*/

        void display() {
            fill(color(255, 0, 0));


            ellipse(x, y, circleSize, circleSize);


        }

        void distinguish(int no) {
            if (no == 1) {
                fill(color(0, 0, 255));
                ellipse(x, y, circleSize, circleSize);
                textSize(12);
                text("Ole-Martin", x + 7, y);
            }
            if (no == 2) {
                fill(color(0, 255, 0));
                ellipse(x, y, circleSize, circleSize);
                textSize(12);
                text("Robin", x + 7, y);
            }
            if (no == 3) {
                fill(color(9, 112, 84));
                ellipse(x, y, circleSize, circleSize);
                textSize(12);
                text("Gøran", x + 7, y);
            }
        }
    }
}
