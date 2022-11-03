package com.company.object;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjBoot extends SuperObject{
    GamePanel gp;

    public ObjBoot(GamePanel gp) {
        name = "boot";
        this.gp = gp;
        try{

            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boot.png"));
            utilityTool.scalesImage(image,gp.tileSize,gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
