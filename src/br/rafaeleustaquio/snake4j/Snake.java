package br.rafaeleustaquio.snake4j;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Snake {

    private LinkedList<CellCoordinate> snakeBody;
    private int snakeSize;
    private Direction direction;
    private Random random = GamePanel.random;
    private int fruitsEaten;
    private Direction lastMovedDirection;


    public Snake() {
        genRandomSnake();
    }

    public LinkedList<CellCoordinate> getSnakeBody() {
        return snakeBody;
    }

    public int getSnakeSize() {
        return snakeSize;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getFruitsEaten() {
        return fruitsEaten;
    }

    public void fattenSnake(Color color) {
        CellCoordinate last = snakeBody.get(snakeSize - 1);
        CellCoordinate bflast = snakeBody.get(snakeSize - 2);

        if (last.getY() == bflast.getY()) {
            if (last.getX() > bflast.getX()) {
                snakeBody.add(new CellCoordinate(last.getX() + GamePanel.BLOCK_SIZE, last.getY(), color));
            } else {
                snakeBody.add(new CellCoordinate(last.getX() - GamePanel.BLOCK_SIZE, last.getY(), color));
            }
        } else {
            if (last.getY() > bflast.getY()) {
                snakeBody.add(new CellCoordinate(last.getX(), last.getY() + GamePanel.BLOCK_SIZE, color));
            } else {
                snakeBody.add(new CellCoordinate(last.getX(), last.getY() - GamePanel.BLOCK_SIZE, color));
            }
        }
        snakeSize++;
    }

    public void genRandomSnake() {
        int headX, headY, secondX, secondY;

        // Random gen snake head and your daughter
        //int randomSnakeSize = random.nextInt(4, 8);
        int randomSnakeSize = 2;

        headX = random.nextInt(randomSnakeSize, (GamePanel.SCREEN_WIDTH - randomSnakeSize * GamePanel.BLOCK_SIZE) / GamePanel.BLOCK_SIZE) * GamePanel.BLOCK_SIZE;
        headY = random.nextInt(randomSnakeSize, (GamePanel.SCREEN_HEIGHT - randomSnakeSize * GamePanel.BLOCK_SIZE) / GamePanel.BLOCK_SIZE) * GamePanel.BLOCK_SIZE;


        if (random.nextBoolean()) {
            secondX = headX;
            if (random.nextBoolean()) {
                secondY = headY + GamePanel.BLOCK_SIZE;
                direction = Direction.UP;
            } else {
                secondY = headY - GamePanel.BLOCK_SIZE;
                direction = Direction.DOWN;
            }
        } else {
            secondY = headY;
            if (random.nextBoolean()) {
                secondX = headX + GamePanel.BLOCK_SIZE;
                direction = Direction.LEFT;
            } else {
                secondX = headX - GamePanel.BLOCK_SIZE;
                direction = Direction.RIGHT;
            }
        }

        // Add the snake head and your daughter
        snakeBody = new LinkedList<CellCoordinate>();
        snakeBody.add(new CellCoordinate(headX, headY, Color.orange));
        snakeBody.add(new CellCoordinate(secondX, secondY, GamePanel.randomColor()));
        snakeSize = 2;

        // Add the rest
        for (int i = 2; i < randomSnakeSize; i++) {
            fattenSnake(GamePanel.randomColor());
        }

        // Set lastMovedDirection
        lastMovedDirection = direction;
    }

    public void drawSnake(Graphics g) {
        //Draw snake body
        for (CellCoordinate c : snakeBody) {
            g.setColor(c.getColor());
            g.fillOval(c.getX() + (int)GamePanel.BLOCK_SIZE /4, c.getY() + (int)GamePanel.BLOCK_SIZE /4, (int)GamePanel.BLOCK_SIZE /2, (int)GamePanel.BLOCK_SIZE /2);

            g.setColor(Color.red);
            g.drawRect(c.getX(), c.getY(), GamePanel.BLOCK_SIZE, GamePanel.BLOCK_SIZE);
        }

        //Strong snake head
        g.setColor(snakeBody.get(0).getColor());
        g.fillRect(snakeBody.get(0).getX(), snakeBody.get(0).getY(), GamePanel.BLOCK_SIZE, GamePanel.BLOCK_SIZE);
    }

    public void move() {


        // Check blocked movements
        switch (direction) {
            case UP:
                if (lastMovedDirection == Direction.DOWN) direction = lastMovedDirection;
                break;
            case DOWN:
                if (lastMovedDirection == Direction.UP) direction = lastMovedDirection;
                break;
            case LEFT:
                if (lastMovedDirection == Direction.RIGHT) direction = lastMovedDirection;
                break;
            case RIGHT:
                if (lastMovedDirection == Direction.LEFT) direction = lastMovedDirection;
                break;
        }

        //Move body
        for (int i = snakeSize - 1; i > 0; i--) {
            CellCoordinate last = snakeBody.get(i);
            CellCoordinate bflast = snakeBody.get(i - 1);

            last.setX(bflast.getX());
            last.setY(bflast.getY());
        }

        CellCoordinate head = snakeBody.get(0);
        //Move head
        switch (direction) {
            case UP:
                head.setY(head.getY() - GamePanel.BLOCK_SIZE);
                break;
            case DOWN:
                head.setY(head.getY() + GamePanel.BLOCK_SIZE);
                break;
            case LEFT:
                head.setX(head.getX() - GamePanel.BLOCK_SIZE);
                break;
            case RIGHT:
                head.setX(head.getX() + GamePanel.BLOCK_SIZE);
                break;
        }

        lastMovedDirection = direction;
    }

    public boolean checkCollisions() {
        CellCoordinate head = snakeBody.get(0);

        // Check if snake suicides
        for (int i = 1; i < snakeSize; i++) {
            CellCoordinate d = snakeBody.get(i);
            if ((head.getX() == d.getX()) && (head.getY() == d.getY())) {
                //Check if snake cordinate is in border to fix bug
                if ((head.getX() <= GamePanel.BLOCK_SIZE)
                        || (head.getX() >= GamePanel.SCREEN_WIDTH - GamePanel.BLOCK_SIZE)
                        || (head.getY() <= GamePanel.BLOCK_SIZE)
                        || (head.getY() >= GamePanel.SCREEN_HEIGHT - GamePanel.BLOCK_SIZE)) {
                    continue;
                }
                return true;
            }
        }


        if (head.getX() < GamePanel.BLOCK_SIZE && direction == Direction.LEFT) { // Check if head touches left border
            head.setX(GamePanel.SCREEN_WIDTH - GamePanel.BLOCK_SIZE * 2);
        } else if (head.getX() > GamePanel.SCREEN_WIDTH - GamePanel.BLOCK_SIZE * 2 && direction == Direction.RIGHT) { // Check if head touches right border
            head.setX(GamePanel.BLOCK_SIZE);
        } else if (head.getY() < GamePanel.BLOCK_SIZE && direction == Direction.UP) { // Check if head touches top border
            head.setY(GamePanel.SCREEN_HEIGHT - GamePanel.BLOCK_SIZE * 2);
        } else if (head.getY() > GamePanel.SCREEN_HEIGHT - GamePanel.BLOCK_SIZE * 2 && direction == Direction.DOWN) { // Check if head touches bottom border
            head.setY(GamePanel.BLOCK_SIZE);
        }
        return false;
    }

    public void checkFruitCollision(Fruit fruit) {

        var fruitLocate = fruit.getFruitLocate();
        if ((fruitLocate.getX() == snakeBody.get(0).getX())
                && (fruitLocate.getY() == snakeBody.get(0).getY())) {
            fattenSnake(fruit.getFruitLocate().getColor());
            fruit.genNewFruit(this);
            fruitsEaten++;
        }
    }
}
