package com.entity;

import com.company.GamePanel;
import com.company.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity implements EntityFactory{
    public int worldX;
    public int worldY;
    public String name;

    public int speed;

    public BufferedImage up1, up2, right1, right2, left1, left2, down1, down2;
    public BufferedImage attackUp1, attackUp2, attackRight1, attackRight2, attackLeft1, attackLeft2, attackDown1, attackDown2;

    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(8, 14, 48, 48);
    public Rectangle attackArea = new Rectangle(0,0,0,0 );
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;



    public  boolean invincible = false;
    public  int invincibleCounter = 0;
    boolean attacking = false;



    public int type ; // 0 player, 1 npc, 2 monster;


    String[] dialogues = new String[20];
    int dialogueIndex;
    GamePanel gp;

    //CHARACTER STATES

    public int maxLife;
    public int life;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/" + imagePath + ".png")); //wall tile trial
            image = utilityTool.scalesImage(image, width,height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction(){}

    @Override
    public Entity createEntity() {
        return this;
    }

    public void speak(){
        if (dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialog = dialogues[dialogueIndex];
        dialogueIndex++;


        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void update(){

        setAction();
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this,false);
       boolean contactPlayer =  gp.collisionChecker.checkPlayer(this);
        gp.collisionChecker.checkEntity(this,gp.monster);
        gp.collisionChecker.checkEntity(this,gp.npc);

        if ( this.type == 2 && contactPlayer){
            if (!gp.player.invincible){
                //can give damage
                gp.player.life -=1;
                gp.player.invincible = true;
            }
        }

        //if collision is false, player can move
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 14) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        //outside of if statement
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }


    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {


            switch (direction) {
                case "up":
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    break;
                case "down":
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    break;
                case "left":
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                    break;
                case "right":
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                    break;
            }

            if (invincible) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }


    }
}