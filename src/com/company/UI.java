package com.company;

import com.company.object.ObjHeart;
import com.company.object.ObjKey;
import com.company.object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;

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

    public  String currentDialog = "";



    BufferedImage heart_full,heart_half, heart_blank;


    public UI(GamePanel gp){
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
                g2.setFont(m5x7_40);
                g2.setColor(Color.white);
                g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
                g2.drawString(" x " + gp.player.hasKey, 70, 60);


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

        }

    }

    private void drawPlayerLife() {


        int x = gp.tileSize/2;
        int y = gp.tileSize/2 + gp.tileSize;
        int i = 0;
        //DRAW BLANK HEART
        while (i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x,y,null);
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


        //TITLE NAME

        g2.setFont((g2.getFont().deriveFont(160F)));
        String text = "BLUp";
        int x = getXForCenteredText(text);
        int y = gp.tileSize *3;


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
        int y = gp.screenHeight/2;
        g2.drawString(text,x,y);

    }
    public int getXForCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 -length/2;
        return x;
    }




}
