package com.company.object;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Shield_Wood extends SuperObject {

    public final GamePanel gp;

    public Obj_Shield_Wood(GamePanel gp) {
        name = "Wood Shield";
        this.gp = gp;
        defenseValue = 1;
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/shield_wood.png"));
            image = utilityTool.scalesImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
