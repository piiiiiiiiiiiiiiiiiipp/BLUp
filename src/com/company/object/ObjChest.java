package com.company.object;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjChest extends SuperObject{
    GamePanel gp;
    public ObjChest(GamePanel gp) {
        name = "chest";
        this.gp = gp;
        try{

            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
            utilityTool.scalesImage(image,gp.tileSize,gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
