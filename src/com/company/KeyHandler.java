package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
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

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }

        //PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        //PAUSE STATE
        else if (gp.gameState == gp.dialogState) {
            dialogueState(code);
        }

        //CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }

        //OPTION STATE
        else if (gp.gameState == gp.optionState) {
            optionState(code);
        }

        //GAME OVER STATE

        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
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

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandName--;
            if (gp.ui.commandName < 0)
                gp.ui.commandName = 2;
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandName++;
            if (gp.ui.commandName > 2)
                gp.ui.commandName = 0;
        }
        if (code == KeyEvent.VK_ENTER) {

            if (gp.ui.commandName == 0) {
                gp.gameState = gp.playState;
            }
            if (gp.ui.commandName == 1) {
                gp.gameState = gp.playState;
            }
            if (gp.ui.commandName == 2) {
                System.exit(0);
            }
        }

    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_SPACE) {
            gp.gameState = gp.playState;
        }
    }

    public void playState(int code) {
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
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionState;
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                gp.playSE(8);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
                gp.playSE(8);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
                gp.playSE(8);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
                gp.playSE(8);
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }

    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void optionState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0:
                maxCommandNum = 4;
                break;
            case 2:
                maxCommandNum = 1;
                break;

        }
        if (code == KeyEvent.VK_W) {
            gp.ui.commandName--;
            gp.playSE(8);
            if (gp.ui.commandName < 0)
                gp.ui.commandName = maxCommandNum;
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandName++;
            gp.playSE(8);
            if (gp.ui.commandName > maxCommandNum)
                gp.ui.commandName = 0;
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandName == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(8);
                }
                if (gp.ui.commandName == 2 && gp.music.volumeScale > 0) {
                    gp.soundEffect.volumeScale--;
                    gp.playSE(8);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandName == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(8);
                }
                if (gp.ui.commandName == 2 && gp.music.volumeScale < 5) {
                    gp.soundEffect.volumeScale++;
                    gp.playSE(8);
                }
            }

        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandName--;
            if (gp.ui.commandName < 0) {
                gp.ui.commandName = 1;
            }
            gp.playSE(8);
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandName++;
            if (gp.ui.commandName > 1) {
                gp.ui.commandName = 0;
            }
            gp.playSE(8);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandName == 0) {
                gp.gameState = gp.playState;
                gp.retry();
            } else if (gp.ui.commandName == 1) {
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
}
