import processing.core.PApplet;

public class Processing extends PApplet {


    MovingCircle[] myCircleArray = new MovingCircle[1];
    int totalSteps = 0;
    int particularSteps = 0;
    boolean edgeReached = false;
    float time;
    float seconds;
    float timeToEdge;
    int left, right, up, down;


    public void setup() {
        // Keep track of steps made in x direction.
        left = 0; right = 0; up = 0; down = 0;
        size(50, 50);
        frameRate(1000);
        noStroke();
        smooth();
        for (int i = 0; i < myCircleArray.length; i++) {
            float r1 = random(150, 200);
            float r2 = random(100, 300);

            myCircleArray[i] = new MovingCircle(width/2, height/2, 2);

        }
    }


    public void draw() {
        seconds = (millis()/1000);

        background(color(244, 255, 255));

        // Viss i er lik 5, øker verdien. Vi følger altså en spesifikk partikkel.
        for (int i = 0; i < myCircleArray.length; i++) {
            if(i == 0) {
                particularSteps++;
            }
        // Viss i er like 5, farger vi den blå og legger tekst til - for å kunne se den enklere.
            myCircleArray[i].move();
            if(i == 0){
                myCircleArray[i].distinguish();
            }
            else myCircleArray[i].display();

        }
        // Skriver ut diverse informasjon i konsoll, etter endt kjøring.
        /*println("###STATISTICS###");
        println("Total steps made: " + totalSteps);
        println("Ole Martin made " + particularSteps + " of those steps!");
        println("Time to get to edge: " + timeToEdge);
*/
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

        void move() {
            // Variable holding total number of moves/steps made.
            totalSteps++;

            float movement = 1;
            float r = random(0, 4);

            //OPP
            if (r >= 0 && r < 1) {
                up++;
                y = y + movement;
                if (y == height) {
                    if(!edgeReached){
                        timeToEdge = seconds;
                        println(seconds);
                    }
                    edgeReached = true;
                    y = y - movement;
                }
            }
            //HØGRE
            if (r >= 1 && r < 2) {
                right++;
                x = x + movement;
                if (x == width) {
                    if(!edgeReached){
                        timeToEdge = seconds;
                        println(seconds);
                    }
                    edgeReached = true;
                    x = x - movement;
                }
            }
            //NED
            if (r >= 2 && r < 3) {
                down++;
                y = y - movement;
                if (y == 0) {
                    if(!edgeReached){
                        timeToEdge = seconds;
                        println(seconds);
                    }
                    edgeReached = true;
                    y = y + movement;
                }
            }
            //VENSTRE
            if (r >= 3 && r < 4) {
                left++;
                x = x - movement;
                if (x == 0) {
                    if(!edgeReached){
                        timeToEdge = seconds;
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

        void distinguish() {
            fill(color(0, 0, 255));
            ellipse(x, y, circleSize, circleSize);
            textSize(12);
            text("Ole Martin", x+7, y);
        }
    }




}
