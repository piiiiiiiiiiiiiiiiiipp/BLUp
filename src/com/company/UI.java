package com.company;

import com.company.object.ObjHeart;
import com.company.object.ObjKey;
import com.company.object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.time.Year;

public class UI  {
    GamePanel gp;

    Graphics2D g2;

    Font kaelia_40, arial_80I, m5x7_40,m5x7_80I;
    BufferedImage keyImage;

    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;

    public int commandName = 0;

    public double playTime;
    DecimalFormat df = new DecimalFormat("#0.0");

    public boolean gameFinish = false;

    public String currentDialog = "";

    public int slotCol = 0;
    public int slotRow = 0;

    int subState = 0;

    BufferedImage heart_full, heart_half, heart_blank;


    public UI(GamePanel gp) {
        this.gp = gp;


        kaelia_40 = new Font("Kaelia", Font.BOLD, 40);
        arial_80I = new Font("Arial", Font.ITALIC, 80);
        getFont();
        m5x7_40 = new Font("m5x7", Font.PLAIN, 64);
        m5x7_80I = new Font("m5x7", Font.ITALIC, 102);


        ObjKey key = new ObjKey(gp);
        keyImage = key.image;


        SuperObject heart = new ObjHeart(gp);
        heart_full = heart.image;
        heart_half = heart.image1;
        heart_blank = heart.image2;

    }
    public void getFont(){
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream myStream = new BufferedInputStream(new FileInputStream("src/com/company/m5x7.ttf"));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
        } catch (IOException |FontFormatException e) {
          e.printStackTrace();
        }
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;

    }

    public void draw (Graphics2D g2){

        this.g2 = g2;
        g2.setFont(m5x7_40);
        g2.setColor(Color.white);
        //TITLE STATE

        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }else {
            if (gameFinish) {

                String text;
                int textLength;
                g2.setFont(m5x7_40);
                g2.setColor(Color.WHITE);
                text = "You found the treasure!";
                int x = getXForCenteredText(text), y = gp.screenHeight / 2 - (gp.tileSize * 3);

                g2.drawString(text, x, y);

                g2.setFont(m5x7_80I);
                g2.setColor(Color.yellow);
                text = "Congratulations!";
                x = getXForCenteredText(text);
                y = gp.screenHeight / 2 + (gp.tileSize * 2);
                g2.drawString(text, x, y);

                gp.gameThread = null;


            } else {
                //message
                if (messageOn) {
                    g2.setFont(g2.getFont().deriveFont(48F));
                    g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                    messageCounter++;

                    if (messageCounter > 120) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
            }

            if (gp.gameState == gp.playState) {
                if (!gameFinish) {
                    g2.setFont(m5x7_40);
                    g2.setColor(Color.white);
                    g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
                    g2.drawString(" x " + gp.player.hasKey, 70, 60);
                }
                drawPlayerLife();

            }
            if (gp.gameState == gp.pauseState) {
                drawPausedScreen();
            }

            /////DIALog state

            if (gp.gameState == gp.dialogState) {
                drawDialogScreen();
                drawPlayerLife();
            }

            //CHARACTER STATE
            if (gp.gameState == gp.characterState) {
                drawCharacterScreen();
                drawInventory();
            }

            //OPTION STATE
            if (gp.gameState == gp.optionState) {
                drawOptionScreen();
            }

            //GAME OVER STATE
            if (gp.gameState == gp.gameOverState) {
                drawGameOverScreen();
            }

        }

    }


    public void drawOptionScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        //SUB WINDOW

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_FullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_endGameconfirmation(frameX, frameY);
                break;
        }
        gp.keyHandler.enterPressed = false;
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // FULL SCREEN
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandName == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyHandler.enterPressed) {
                if (!gp.fullScreenOn) {
                    gp.fullScreenOn = true;
                } else if (gp.fullScreenOn) {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        //MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandName == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("Sound Effects", textX, textY);
        if (commandName == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        //END GAME
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandName == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyHandler.enterPressed) {
                subState = 2;
                commandName = 0;
            }
        }

        //Back
        textY += gp.tileSize * 3;
        g2.drawString("Back", textX, textY);
        if (commandName == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyHandler.enterPressed) {
                gp.gameState = gp.playState;
                commandName = 0;
            }
        }


        //FULL SCREEN CHECKBOX
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        //MUSIC
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SE
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.soundEffect.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);


        gp.config.saveConfig();
    }

    public void options_FullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        currentDialog = "The change will take \neffect after restarting \nthe game";

        for (String line : currentDialog.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //BACk
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandName == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyHandler.enterPressed) {
                subState = 0;
                commandName = 2;
            }
        }


    }

    private void options_endGameconfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize + gp.tileSize / 2;
        int textY = frameY + gp.tileSize * 3;
        currentDialog = "Are you sure to quit\n game and return \nto the title page?";

        for (String line : currentDialog.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }


        String text = "YES";
        textX = getXForCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandName == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyHandler.enterPressed) {
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }
        text = "NO";
        textX = getXForCenteredText(text);
        textY += gp.tileSize / 2;
        g2.drawString(text, textX, textY);
        if (commandName == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyHandler.enterPressed) {
                subState = 0;
                commandName = 3;
            }
        }

    }

    private void drawCharacterScreen() {
        //CREATE A FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        //NAMES

        g2.drawString("Level", textX, textY);
        textY += lineHeight;

        g2.drawString("Life", textX, textY);
        textY += lineHeight;

        g2.drawString("Strength", textX, textY);
        textY += lineHeight;

        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;

        g2.drawString("Attack", textX, textY);
        textY += lineHeight;

        g2.drawString("Defense", textX, textY);
        textY += lineHeight;

        g2.drawString("Exp", textX, textY);
        textY += lineHeight;

        g2.drawString("Next level", textX, textY);
        textY += lineHeight;

        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;

        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;

        g2.drawString("Shield", textX, textY);
        textY += lineHeight;


        //VALUES

        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;


        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevel);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        //WEAPON AND SHIELD IMAGES
        g2.drawImage(gp.player.currentWeapon.image, tailX - gp.tileSize, textY - 15, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.image, tailX - gp.tileSize, textY - 15, null);

    }

    private void drawInventory() {
        //FRAME
        int frameX = gp.tileSize * 12;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        //SLOTS
        final int slotXStart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        //DRAW PLAYER'S ITEMS
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            if (gp.player.inventory.get(i) == gp.player.currentWeapon ||
                    gp.player.inventory.get(i) == gp.player.currentShield) {
                g2.setColor(Color.gray);
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(gp.player.inventory.get(i).image, slotX, slotY, null);

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //CURSOR

        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        g2.setColor(new Color(120, 90, 69));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight + gp.tileSize / 2;
        int dFrameWidth = frameWidth;
        int dframeHeigth = gp.tileSize * 3;

        //DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));
        int itemIndex = getItemIndexOnSlot();
        if (itemIndex < gp.player.inventory.size()) {
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dframeHeigth);
            for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }

    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    private void drawPlayerLife() {


        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2 + gp.tileSize;
        int i = 0;
        //DRAW BLANK HEART
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        //DRAW CURRENT LIFE
        x = gp.tileSize/2;
        y = gp.tileSize/2 + gp.tileSize;
        i = 0;
        //DRAW BLANK HEART
        while (i < gp.player.life){
            g2.drawImage(heart_half, x,y,null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(heart_full, x,y,null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    private void drawTitleScreen() {


        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //TITLE NAME

        g2.setFont((g2.getFont().deriveFont(160F)));
        String text = "BLUp";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;


        ///SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+4,y+4);

        g2.setColor(new Color(255,255,255));
        g2.drawString(text, x,y);
        g2.setFont((g2.getFont().deriveFont(64F)));
        String text2 = "Adventure Time";
        int x2 = getXForCenteredText(text2);
        int y2 = gp.tileSize *4 + gp.tileSize/2 ;

        g2.setColor(new Color(255,255,255));
        g2.drawString(text2, x2,y2);


        //image of player
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize *2;
        g2.drawImage(gp.player.down1, x,y,gp.tileSize*2,gp.tileSize*2,null);

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize *3.5;
        g2.drawString(text, x,y);
        if (commandName == 0 ){
            g2.drawString(">", x-gp.tileSize,y);
        }
        text = "lOAD GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize ;
        g2.drawString(text, x,y);
        if (commandName == 1 ){
            g2.drawString(">", x-gp.tileSize,y);
        }
        text = "QUIT";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x,y);
        if (commandName == 2 ){
            g2.drawString(">", x-gp.tileSize,y);
        }

    }

    public void drawDialogScreen() {
        //window
        int x = gp.tileSize *4;
        int y = gp.tileSize /2;
        int width = gp.screenWidth - (gp.tileSize*8);
        int height = gp.tileSize*4;
        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(32F));
        x +=gp.tileSize;
        y +=gp.tileSize;

        for (String line : currentDialog.split("\n")){
            g2.drawString(line,x,y);
            y += 40;
        }


    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        c = new Color(120,90,69);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,35,35);

    }

    public void drawPausedScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,102));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXForAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);


        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 160F));

        text = " GAME OVER";
        g2.setColor(Color.black);
        x = getXForCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        x = getXForCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x - 4, y - 4);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
        text = "START AGAIN";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandName == 0) {
            g2.drawString(">", x - 40, y);
            if (gp.keyHandler.enterPressed) {


            }
        }

        text = "QUIT";
        x = getXForCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);

        if (commandName == 1) {
            g2.drawString(">", x - 40, y);
            if (gp.keyHandler.enterPressed) {


            }
        }
    }
}
