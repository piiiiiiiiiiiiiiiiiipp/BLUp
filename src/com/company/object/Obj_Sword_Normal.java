package com.company.object;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Sword_Normal extends SuperObject {

    public Obj_Sword_Normal(GamePanel gp) {
        name = "Normal Sword";
        this.gp = gp;
        attackValue = 1;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/sword_normal.png"));
            image = utilityTool.scalesImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
