package game;

import java.awt.*;
import java.util.Random;

public class Fruit {

    public CellCoordinate fruitLocate;
    final private Random random = GamePanel.random;


    public Fruit(Snake snake){
        genNewFruit(snake);
    }

    public CellCoordinate getFruitLocate() {
        return fruitLocate;
    }

    public void genNewFruit(Snake snake) {
        int x, y, i;
        var snakeSize = snake.getSnakeSize();
        var snakeBody = snake.getSnakeBody();


        // Random gen x position
        while (true) {
            x = random.nextInt(1, (int) (GamePanel.SCREEN_WIDTH / GamePanel.BLOCK_SIZE) -1) * GamePanel.BLOCK_SIZE;

            for (i = 0; i < snakeSize; i++) {
                if (snakeBody.get(i).getX() == x) break;
            }
            if (i == snakeSize) break;
        }

        // Random gen y position
        while (true) {
            y = random.nextInt(1,(int) (GamePanel.SCREEN_HEIGHT / GamePanel.BLOCK_SIZE) -1) * GamePanel.BLOCK_SIZE;
            for (i = 0; i < snakeSize; i++) {
                if (snakeBody.get(i).getY() == y) break;
            }
            if (i == snakeSize) break;
        }

        //Gen new apple
        fruitLocate = new CellCoordinate(x, y, GamePanel.randomColor());
    }

    public void drawFruit(Graphics g){
        g.setColor(fruitLocate.getColor());
        g.fillOval(fruitLocate.getX(), fruitLocate.getY(), GamePanel.BLOCK_SIZE, GamePanel.BLOCK_SIZE);
    }

}
