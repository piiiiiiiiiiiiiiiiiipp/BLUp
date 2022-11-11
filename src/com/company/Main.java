package com.company;

import javax.swing.*;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        // create a window/frame
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("BLUp");

        //create and game loop and basic mechanics
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null); //flexible position
        window.setVisible(true);//visibility on

        gamePanel.setupGame();
        gamePanel.startGameThread(); //game loop


    }
}
