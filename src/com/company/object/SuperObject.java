package com.company.object;

import com.company.GamePanel;
import com.company.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject implements Cloneable {

    public BufferedImage image;
    public BufferedImage image1;
    public BufferedImage image2;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    protected GamePanel gp;
    UtilityTool utilityTool = new UtilityTool();

    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int type;
    public final int type_sword = 0;
    public final int type_shield = 1;
    public final int type_axe = 2;
    public final int type_consumable = 2;


    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }

    }

    @Override
    public SuperObject clone() {
        try {
            SuperObject clone = (SuperObject) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
