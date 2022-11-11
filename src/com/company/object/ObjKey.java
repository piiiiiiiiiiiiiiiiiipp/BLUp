package com.company.object;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjKey extends SuperObject{

    GamePanel gp;

    public ObjKey(GamePanel gp) {
        name = "key";
        this.gp = gp;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
            image = utilityTool.scalesImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
