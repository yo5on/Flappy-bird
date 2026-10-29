import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    //images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //bird class
    int birdX = boardWidth/8;
    int birdY = boardWidth/2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //pipe class
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;  //scaled by 1/6
    int pipeHeight = 512;
    
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    //game logic
    Bird bird;
    int velocityX = -4; //move pipes to the left speed (simulates bird moving right)
    int velocityY = 0; //move bird up/down speed.
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        //place pipes timer
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              // Code to be executed
              placePipes();
            }
        });
        placePipeTimer.start();
        
		//game timer
		gameLoop = new Timer(1000/60, this); //how long it takes to start timer, milliseconds gone between frames 
        gameLoop.start();
	}
    
    void placePipes() {
        //(0-1) * pipeHeight/2.
        // 0 -> -128 (pipeHeight/4)
        // 1 -> -128 - 256 (pipeHeight/4 - pipeHeight/2) = -3/4 pipeHeight
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;
    
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);
    
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y  + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }
    
    
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
        Graphics2D canvas = (Graphics2D) g.create();
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvas.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Background, bird and pipes keep the original game artwork.
        canvas.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        canvas.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
        for (Pipe pipe : pipes) {
            canvas.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Compact arcade-style score plate.
        canvas.setColor(new Color(10, 24, 35, 165));
        canvas.fillRoundRect(14, 14, 92, 52, 18, 18);
        canvas.setColor(new Color(255, 255, 255, 90));
        canvas.drawRoundRect(14, 14, 92, 52, 18, 18);
        canvas.setFont(new Font("SansSerif", Font.BOLD, 27));
        String scoreText = Integer.toString((int) score);
        FontMetrics metrics = canvas.getFontMetrics();
        canvas.setColor(new Color(0, 0, 0, 110));
        canvas.drawString(scoreText, 61 - metrics.stringWidth(scoreText) / 2 + 1, 49);
        canvas.setColor(Color.WHITE);
        canvas.drawString(scoreText, 60 - metrics.stringWidth(scoreText) / 2, 48);

        if (gameOver) {
            // Keep the world visible behind a clear, centred end-state card.
            canvas.setColor(new Color(4, 15, 25, 145));
            canvas.fillRect(0, 0, boardWidth, boardHeight);
            int cardW = 286;
            int cardH = 174;
            int cardX = (boardWidth - cardW) / 2;
            int cardY = (boardHeight - cardH) / 2;
            canvas.setColor(new Color(13, 36, 48, 238));
            canvas.fillRoundRect(cardX, cardY, cardW, cardH, 24, 24);
            canvas.setColor(new Color(255, 255, 255, 65));
            canvas.drawRoundRect(cardX, cardY, cardW, cardH, 24, 24);
            canvas.setColor(new Color(255, 215, 85));
            canvas.setFont(new Font("SansSerif", Font.BOLD, 12));
            canvas.drawString("FLIGHT ENDED", cardX + 22, cardY + 34);
            canvas.setColor(Color.WHITE);
            canvas.setFont(new Font("SansSerif", Font.BOLD, 29));
            canvas.drawString("Game over", cardX + 22, cardY + 72);
            canvas.setColor(new Color(218, 231, 235));
            canvas.setFont(new Font("SansSerif", Font.PLAIN, 16));
            canvas.drawString("Score  " + (int) score, cardX + 22, cardY + 103);
            canvas.setColor(new Color(255, 215, 85));
            canvas.setFont(new Font("SansSerif", Font.BOLD, 12));
            canvas.drawString("PRESS SPACE TO RETRY", cardX + 22, cardY + 143);
        } else {
            canvas.setColor(new Color(255, 255, 255, 210));
            canvas.setFont(new Font("SansSerif", Font.BOLD, 10));
            canvas.drawString("SPACE  /  FLAP", 16, boardHeight - 18);
        }
        canvas.dispose();
    }

    public void move() {
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); //apply gravity to current bird.y, limit the bird.y to top of the canvas

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5; //0.5 because there are 2 pipes! so 0.5*2 = 1, 1 for each set of pipes
                pipe.passed = true;
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
               a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
               a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
               a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        move();
        repaint();
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }  

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // System.out.println("JUMP!");
            velocityY = -9;

            if (gameOver) {
                //restart game by resetting conditions
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }

    //not needed
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
