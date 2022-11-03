package com.entity;

import com.company.GamePanel;
import com.company.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class OldMan extends Entity{

    public OldMan(GamePanel gp) {
        super(gp);
         direction ="down";
         name = "Old Man";
         speed =1;
         getImage();
        setDialog();
    }

    public  void getImage() {
        up1 = setup("oldMan/up1",gp.tileSize, gp.tileSize);
        up2 = setup("oldMan/up2",gp.tileSize, gp.tileSize);
        down1 = setup("oldMan/down1",gp.tileSize, gp.tileSize);
        down2 = setup("oldMan/down2",gp.tileSize, gp.tileSize);
        left1 = setup("oldMan/left1",gp.tileSize, gp.tileSize);
        left2 = setup("oldMan/left2",gp.tileSize, gp.tileSize);
        right1 = setup("oldMan/right1",gp.tileSize, gp.tileSize);
        right2 = setup("oldMan/right2",gp.tileSize, gp.tileSize);
    }

    @Override
    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick number from 1 to 100

            if (i <= 25)
                direction = "up";
            if (i > 25 && i <= 50)
                direction = "down";
            if (i > 50 && i <= 75)
                direction = "left";
            if (i > 75 && i <= 100)
                direction = "right";
            actionLockCounter = 0;
        }
    }

    public void speak(){
       super.speak();
    }

    public void setDialog(){
        dialogues[0] = "Hello,my friend.";
        dialogues[1] = "So you've come to this island\nto find treasure?";
        dialogues[2] = "I used to be a great wizard but now...\nI'm too old for an adventures.";
        dialogues[3] = "You need to find \nall three keys to open doors.\nGood luck, my friend!";
    }
}
