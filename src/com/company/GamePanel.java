package com.company;

import com.company.object.SuperObject;
import com.entity.Entity;
import com.entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int origTileSize = 16;
    final int scale = 3;
    public final int tileSize = origTileSize * scale;

    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenWidth2 = screenWidth; //full screen
    public final int screenHeight = tileSize * maxScreenRow;
    public final int screenHeight2 = screenHeight;

    BufferedImage tempScreen;
    Graphics2D g2;


    //world map settings

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;


    //FPS
    int FPS = 60;

    //SYSTEM
    Thread gameThread;

    public KeyHandler keyHandler = new KeyHandler(this);
    TileManager tileManager = new TileManager(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();

    public EventHandler eventHandler = new EventHandler(this);

    public UI ui = new UI(this);

    //ENTITY AND OBJECT
    public Entity entity = new Entity(this).createEntity();

    public Player player = new Player(this, keyHandler);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];
    public  Entity[] monster = new Entity[20];
    public AssetSetter assetSetter = new AssetSetter(this);


    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {

        assetSetter.setObj();
        assetSetter.setNpc();
        assetSetter.setMonster();

        playMusic(0);

        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
    }

    public void startGameThread() {
        gameThread = GameThread.getInstance(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drewInterval = (double) (1000000000 / FPS);
        double nextDrawTime = System.nanoTime() + drewInterval;


        //game loop
        while (gameThread != null) {

            update();

//            repaint();

            drawToTempScreen(g2);

            drawToScreen();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drewInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        //update the image of player for walking

        if (gameState == playState) {
            player.update();

            //npc
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
            //monster
            for (Entity entity : monster) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
        if (gameState == pauseState) {
            //nothing for now
        }


    }

    public void drawToTempScreen(Graphics2D g2) {

        //title state

        if (gameState == titleState) {
            ui.draw(g2);
        }
        //
        else {
            //map
            tileManager.draw(g2);

            //object
            for (SuperObject superObject : obj) {
                if (superObject != null) {
                    superObject.draw(g2, this);
                }
            }
            //
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            for (Entity entity : monster) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            //player
            player.draw(g2);

            //UI

            ui.draw(g2);


        }
    }

//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//
//
//        //title state
//
//        if(gameState == titleState){
//            ui.draw(g2);
//        }
//        //
//        else{
//            //map
//            tileManager.draw(g2);
//
//            //object
//            for (SuperObject superObject : obj) {
//                if (superObject != null) {
//                    superObject.draw(g2, this);
//                }
//            }
//            //
//            for (Entity entity : npc) {
//                if (entity != null) {
//                    entity.draw(g2);
//                }
//            }
//     for (Entity entity : monster) {
//        if (entity != null) {
//            entity.draw(g2);
//        }
//    }
//            //player
//            player.draw(g2);
//
//            //UI
//
//            ui.draw(g2);
//
//        }
//
//
//
//
//        g2.dispose();
//    }

    public void drawToScreen() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();


    }


    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();

    }

    public void playSE(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

}
