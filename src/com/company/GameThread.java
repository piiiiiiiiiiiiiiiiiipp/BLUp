package com.company;

class GameThread extends Thread{

    // private field that refers to the object
    private static Thread gameThread;

    private GameThread() {
        // constructor of the Singleton
    }

    public static Thread getInstance(GamePanel gp) {
        gameThread =   new Thread(gp);
        return gameThread;
    }
}