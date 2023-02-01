package game;

import javax.swing.*;

public class GameWindow extends JFrame {

    private static void configFrame(GameWindow gameWindow){
        gameWindow.setTitle("Snake4j");
        //gameWindow.setLocationRelativeTo(null); // center window
        gameWindow.setResizable(false);
        gameWindow.pack();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);
    }


    public GameWindow(){
        super();
        // Add gamePanel
        this.add(new GamePanel());
        configFrame(this);


    }



}
