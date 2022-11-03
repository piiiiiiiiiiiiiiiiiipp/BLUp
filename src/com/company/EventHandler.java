package com.company;

import java.awt.*;

public class EventHandler {

    GamePanel gp;

    EventRect[][] eventRect;


    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col =0 , row =0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if (col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }


    }

    public void checkEvent(){
        if(hit(27, 16, "right")){
            damagePit(gp.dialogState);
        }
//        if(hit(27, 16, "right")){
//            teleport(gp.dialogState);
//        }
        if(hit(23, 12, "up")){
            healingPoll(gp.dialogState);
        }


    }

    private void teleport(int gameState) {

        gp.gameState = gameState;
        gp.ui.currentDialog = "Teleportation!!!!!";
        gp.player.worldX = gp.tileSize *37;
        gp.player.worldY = gp.tileSize *10;


    }

    private void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialog = "You fall into a pit!";
        gp.player.life -=1;

    }

    private void healingPoll(int gameState) {
        if (gp.keyHandler.enterPressed){
            if(gp.player.life < gp.player.maxLife){
            gp.gameState = gameState;
            gp.ui.currentDialog = "You drank the healing water.";
            gp.player.life +=1;
            }
            else{
            gp.ui.showMessage( "The healing water is here.");
            }
        }
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection){

        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[eventCol][eventRow].x = eventCol*gp.tileSize + eventRect[eventCol][eventRow].x;
        eventRect[eventCol][eventRow].y = eventRow*gp.tileSize + eventRect[eventCol][eventRow].y;


        if (gp.player.solidArea.intersects(eventRect[eventCol][eventRow])){
            if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")){
                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].eventRectDefaultX;
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].eventRectDefaultY;

        return hit;
    }
}
