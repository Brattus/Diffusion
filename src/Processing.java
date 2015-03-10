import processing.core.PApplet;

public class Processing extends PApplet {


    MovingCircle[] myCircleArray = new MovingCircle[200];
    float previousX = 0;
    float previousY = 0;
    float x = 0;
    float y = 0;
    boolean loading = true;


    public static void main(String args[]) {
        PApplet.main(new String[]{"--present", "Processing"});
    }

    public void setup() {
        size(400, 400);
        frameRate(300);
        noStroke();
        smooth();
        for (int i = 0; i < myCircleArray.length; i++) {
            float r1 = random(150, 200);
            float r2 = random(100, 300);
            // (o.o)
            myCircleArray[i] = new MovingCircle(width/2, height/2, 2);

        }
    }

    public void draw() {
        background(color(244, 255, 255));
        while(loading){
            for(int i = 0; i < myCircleArray.length; i++){
                findPlace(myCircleArray[i]);
            }
            loading = false;
        }

        for (int i = 0; i < myCircleArray.length; i++) {

            myCircleArray[i].move();
            myCircleArray[i].display();
            println(millis());
        }
    }

    private void findPlace(MovingCircle movingCircle) {
        float currentX = getX();
        float currentY = getY();
        float random;
        boolean placeFound = false;

        while(!placeFound) {
            if (currentX == previousX || currentX == 200) {
                random = random(-1, 1);
                if (random < 0) {
                    currentX -= 1;
                } else currentX += 1;
                previousX = currentX;
                x = currentX;
            }
            if (currentY == previousY || currentY == 200) {
                random = random(-1, 1);
                if (random < 0) {
                    currentY -= 1;
                } else currentY += 1;
                previousY = currentY;
                y = currentY;
            }
            if(currentX != previousX || currentY != previousY) {placeFound = true; }
        }
    }



    class MovingCircle {
        float xSpeed;
        float ySpeed;
        float circleSize;

        MovingCircle(float xpos, float ypos, float csize) {
            x = xpos;
            y = ypos;

            circleSize = csize;


        }

        void move() {
            float movement = 1;
            float r = random(0, 4);

            //OPP
            if (r >= 0 && r < 1) {
                y = y + movement;
                if (y == height) {
                    y = y - movement;
                }
            }
            //HØGRE
            if (r >= 1 && r < 2) {
                x = x + movement;
                if (x == width) {
                    x = x - movement;
                }
            }
            //NED
            if (r >= 2 && r < 3) {
                y = y - movement;
                if (y == 0) {
                    y = y + movement;
                }
            }
            //VENSTRE
            if (r >= 3 && r < 4) {
                x = x - movement;
                if (x == 0) {
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

        void checkCollisions() {

            float r = circleSize / 2;

            if ((x < r) || (x > width - r)) {
                xSpeed = -xSpeed;
            }

            if ((y < r) || (y > height - r)) {
                ySpeed = -ySpeed;
            }


        }


        void display() {
            fill(color(255, 0, 0));

            ellipse(x, y, circleSize, circleSize);


        }



    }
}