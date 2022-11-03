package com.company;

import com.company.monster.MON_GreenSlime;
import com.company.object.ObjBoot;
import com.company.object.ObjChest;
import com.company.object.ObjDoor;
import com.company.object.ObjKey;
import com.entity.OldMan;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObj() {
       gp.obj[0] = new ObjKey(gp);
       gp.obj[0].worldX = 23 * gp.tileSize;
       gp.obj[0].worldY = 7 * gp.tileSize;

       gp.obj[1] = new ObjKey(gp);
       gp.obj[1].worldX = 23 * gp.tileSize;
       gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new ObjKey(gp);
        gp.obj[2].worldX = 38 * gp.tileSize;
        gp.obj[2].worldY = 8 * gp.tileSize;

        gp.obj[3] = new ObjDoor(gp);
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 12 * gp.tileSize;

        gp.obj[4] = new ObjDoor(gp);
        gp.obj[4].worldX = 8 * gp.tileSize;
        gp.obj[4].worldY = 28 * gp.tileSize;

        gp.obj[5] = new ObjDoor(gp);
        gp.obj[5].worldX = 12 * gp.tileSize;
        gp.obj[5].worldY = 23 * gp.tileSize;

        gp.obj[6] = new ObjChest(gp);
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 9 * gp.tileSize;

        gp.obj[7] = new ObjBoot(gp);
        gp.obj[7].worldX = 37 * gp.tileSize;
        gp.obj[7].worldY = 42 * gp.tileSize;
    }

    public void setNpc() {
      gp.npc[0] = new OldMan(gp);
      gp.npc[0].worldX = gp.tileSize *21;
      gp.npc[0].worldY = gp.tileSize *21;

    }

    public void setMonster() {
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize *23;
        gp.monster[0].worldY = gp.tileSize *36;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.tileSize *23;
        gp.monster[1].worldY = gp.tileSize *37;

        gp.monster[2] = new MON_GreenSlime(gp);
        gp.monster[2].worldX = gp.tileSize *11;
        gp.monster[2].worldY = gp.tileSize *11;

        gp.monster[3] = new MON_GreenSlime(gp);
        gp.monster[3].worldX = gp.tileSize *11;
        gp.monster[3].worldY = gp.tileSize *10;


    }
}