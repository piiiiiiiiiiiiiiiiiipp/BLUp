package com.company.object;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjHeart extends SuperObject{
    GamePanel gp;
    public ObjHeart(GamePanel gp) {
        name = "door";
        this.gp = gp;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_full.png"));
            image1 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_half.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_blank.png"));
            image = utilityTool.scalesImage(image,gp.tileSize,gp.tileSize);
            image1 = utilityTool.scalesImage(image1,gp.tileSize,gp.tileSize);
            image2 = utilityTool.scalesImage(image2,gp.tileSize,gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
