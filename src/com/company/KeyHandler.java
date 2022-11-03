package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed,enterPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(gp.gameState == gp.titleState){
            if (code == KeyEvent.VK_W) {
                gp.ui.commandName--;
                if(gp.ui.commandName < 0)
                    gp.ui.commandName = 2;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandName++;
                if(gp.ui.commandName > 2)
                    gp.ui.commandName = 0;
            }
            if (code == KeyEvent.VK_ENTER) {

                if(gp.ui.commandName == 0 ){
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandName == 1 ){
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandName == 2 ){
                    System.exit(0);
                }
            }

        }
       else if (gp.gameState == gp.playState) {

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }
          if(gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
          if(gp.gameState == gp.dialogState){
            if(code == KeyEvent.VK_SPACE){
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
