package com.company.object;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Axe extends SuperObject {
    public Obj_Axe(GamePanel gp) {
        name = "Iron Axe";
        this.gp = gp;
        attackValue = 2;
        type = type_axe;

        description = "[" + name + "] \nAn iron axe.\n";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/axe.png"));
            image = utilityTool.scalesImage(image, gp.tileSize, gp.tileSize);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
