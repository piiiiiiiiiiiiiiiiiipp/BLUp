package com.entity;

import com.company.GamePanel;
import com.company.UtilityTool;
import com.company.object.SuperObject;

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


    public boolean invincible = false;
    public int invincibleCounter = 0;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    int deadCounter = 0;


    public boolean hpBarOn = false;
    public int barCounter;


    public int type; // 0 player, 1 npc, 2 monster;


    String[] dialogues = new String[20];
    int dialogueIndex;
    GamePanel gp;

    //CHARACTER STATES

    public int maxLife;
    public int life;
    public int level = 1;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevel;
    public int coin;
    public SuperObject currentWeapon;
    public SuperObject currentShield;

    //ITEM ATTRIBUTE


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/" + imagePath + ".png")); //wall tile trial
            image = utilityTool.scalesImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {
    }

    public void damageReaction() {

    }

    @Override
    public Entity createEntity() {
        return this;
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
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
                gp.playSE(6);
                int damage = attack - gp.player.defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.player.life -= damage;
                gp.player.invincible = true;
            }
        }

        //if collision is false, player can move
        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
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

            //HP
            if (type == 2 && hpBarOn) {

                double oneScale = (double) gp.tileSize / maxLife;
                double hpValue = oneScale * life;


                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpValue, 10);
                barCounter++;

                if (barCounter > 420) {
                    barCounter = 0;
                    hpBarOn = false;
                }

            }

            if (invincible) {
                hpBarOn = true;
                barCounter = 0;
                changeAlpha(g2, 0.4f);
            }
            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            changeAlpha(g2, 1f);
        }


    }

    public void dyingAnimation(Graphics2D g2) {

        deadCounter++;
        int i = 5;

        if (deadCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (deadCounter > i && deadCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (deadCounter > i * 2 && deadCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (deadCounter > i * 3 && deadCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (deadCounter > i * 4 && deadCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (deadCounter > i * 5 && deadCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (deadCounter > i * 6 && deadCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (deadCounter > i * 7 && deadCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (deadCounter > i * 8) {
            dying = false;
            alive = false;
        }

    }

    public void changeAlpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }
}