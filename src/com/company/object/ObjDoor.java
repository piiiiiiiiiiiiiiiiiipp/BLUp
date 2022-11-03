package com.company.object;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjDoor extends SuperObject{
    GamePanel gp;
    public ObjDoor(GamePanel gp) {
        name = "door";
        this.gp = gp;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
            utilityTool.scalesImage(image,gp.tileSize,gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
