package br.rafaeleustaquio.snake4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    private static void configGamePanel(GamePanel gamePanel) {
        gamePanel.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        gamePanel.setBackground(Color.black);
        gamePanel.setFocusable(true);
    }

    public static Color randomColor(){
        final float hue = random.nextFloat();
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
        return Color.getHSBColor(hue, saturation, luminance);

        //return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    // Static attributes
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int BLOCK_SIZE = 25;
    static final int BLOCK_AREA = BLOCK_SIZE * BLOCK_SIZE;
    static final int COUNT_BLOCKS = (int) (SCREEN_WIDTH * SCREEN_HEIGHT) / BLOCK_AREA;
    static final int DELAY = 6;
    static final Random random = new Random();

    // Private attributes

    private Snake snake;
    private Fruit fruit;

    private Timer timer;

    private int tmp = 0;

    public GamePanel() {

        configGamePanel(this);
        this.addKeyListener(new SnakeKeyAdapter());

        snake = new Snake();
        fruit = new Fruit(snake);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        //Draw borders
        paintBorders(g);


//        // Draw the checkered mesh
//        g.setColor(Color.gray);
//        for (int i = 0; i < SCREEN_WIDTH; i += BLOCK_SIZE) {
//            g.drawLine(i, 0, i, SCREEN_HEIGHT);
//        }
//        for (int i = 0; i < SCREEN_HEIGHT; i += BLOCK_SIZE) {
//            g.drawLine(0, i, SCREEN_WIDTH, i);
//        }

        snake.drawSnake(g);
        fruit.drawFruit(g);
    }



    public void paintBorders(Graphics g){
        g.setColor(Color.blue);
        for(int i = 0; i < SCREEN_WIDTH; i+= BLOCK_SIZE){
            g.fillRect(i, 0, BLOCK_SIZE, BLOCK_SIZE);
            g.fillRect(i, SCREEN_HEIGHT-BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }

        for(int i = 0; i < SCREEN_HEIGHT; i+=BLOCK_SIZE){
            g.fillRect(0, i, BLOCK_SIZE, BLOCK_SIZE);
            g.fillRect(SCREEN_WIDTH-BLOCK_SIZE, i, BLOCK_SIZE, BLOCK_SIZE);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(tmp % 10 == 0){
            snake.move();
            snake.checkFruitCollision(fruit);
            if(snake.checkCollisions()) gameOver();
            tmp = 1;
        }
        repaint();
        tmp++;
    }

    public void gameOver(){
        timer.stop();
    }
    public class SnakeKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            var direction = snake.getDirection();
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT){
                       snake.setDirection(Direction.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT){
                        snake.setDirection(Direction.RIGHT);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN){
                        snake.setDirection(Direction.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP){
                        snake.setDirection(Direction.DOWN);
                    }
                    break;
            }
        }
    }


}
