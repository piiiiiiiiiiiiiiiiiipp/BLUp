package com.entity;

import com.company.Context;
import com.company.GamePanel;
import com.company.KeyHandler;
import com.company.object.ObjKey;
import com.company.object.Obj_Shield_Wood;
import com.company.object.Obj_Sword_Normal;
import com.company.object.SuperObject;
import com.company.strategies.AxeShield;
import com.company.strategies.SwordShield;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity implements EntityFactory {
    public KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;

    public boolean attackCanceled = true;

    public ArrayList<SuperObject> inventory = new ArrayList<>();
    public final int inventorySize = 20;

    public Context context;

    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);

        this.keyHandler = keyHandler;

        screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);
        screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 14, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 36;
        attackArea.height = 36;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        name = "Player";


        //PLAYER STATES
        maxLife = 6; // 1 = half heart,  6 = 3 full hearts
        life = maxLife;

        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevel = 5;
        coin = 0;
        currentWeapon = new Obj_Sword_Normal(gp);
        currentShield = new Obj_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();


    }

    public void setDefaultPositions() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }

    public void restoreLife() {
        life = maxLife;
        invincible = false;
    }

    public void setItems() {
        inventory.clear();
        inventory.add(currentShield);
        inventory.add(currentWeapon);
    }

    public int getDefense() {
        if (currentWeapon.type == 0) {
            context = new Context(new SwordShield(), gp);
            return context.getDefense();
        } else if (currentWeapon.type == 2) {
            context = new Context(new AxeShield(), gp);
            return context.getDefense();
        }
        return defense = dexterity * currentShield.defenseValue;
    }

    public int getAttack() {
        if (currentWeapon.type == 0) {
            context = new Context(new SwordShield(), gp);
            return context.getAttack();
        } else if (currentWeapon.type == 2) {
            context = new Context(new AxeShield(), gp);
            return context.getAttack();
        }
        return attack = strength * currentWeapon.attackValue;
    }

    public void getPlayerImage() {
        up1 = setup("player/up1", gp.tileSize, gp.tileSize);
        up2 = setup("player/up2", gp.tileSize, gp.tileSize);
        down1 = setup("player/down1", gp.tileSize, gp.tileSize);
        down2 = setup("player/down2", gp.tileSize, gp.tileSize);
        left1 = setup("player/left1", gp.tileSize, gp.tileSize);
        left2 = setup("player/left2", gp.tileSize, gp.tileSize);
        right1 = setup("player/right1", gp.tileSize, gp.tileSize);
        right2 = setup("player/right2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackRight1 = setup("player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        attackLeft1 = setup("player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
    }

    public void update() {
        if (attacking) {
            attackingMethod();
        }
        else if (keyHandler.upPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.downPressed || keyHandler.enterPressed) {
            if (keyHandler.upPressed) {
                direction = "up";

            } else if (keyHandler.downPressed) {
                direction = "down";

            } else if (keyHandler.leftPressed) {
                direction = "left";

            } else if (keyHandler.rightPressed) {
                direction = "right";

            }

            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);


            //CHECK EVENT
            gp.eventHandler.checkEvent();



            //if collision is false, player can move
            if (!collisionOn && !keyHandler.enterPressed) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;

                        break;
                    case "left":
                        worldX -= speed;

                        break;
                    case "right":
                        worldX += speed;

                        break;
                }
            }

            if (keyHandler.enterPressed && !attackCanceled) {
                attacking = true;
                spriteCounter = 0;

            }

            attackCanceled = false;

            gp.keyHandler.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 14) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else{
            standCounter++;
            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }

        //outside of if statement
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.playSE(9);
            gp.stopMusic();
        }
    }

    private void attackingMethod() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            //CURRENT WORLD PLACE
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //PLAYER ATTACK X AND Y
            switch (direction){
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;

                    break;
                case "left":
                    worldX -= attackArea.width;

                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            //ATTACK AREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.collisionChecker.checkEntity(this,gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void damageMonster(int i) {
        if (i != 999) {
            if (!gp.monster[i].invincible){
                gp.playSE(6);

                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;

                }
                gp.monster[i].life -= damage;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    exp += gp.monster[i].exp;
                    gp.ui.showMessage("Exp + " + gp.monster[i].exp);
                    checkLevelup();
                }
            }
        }
    }

    public void checkLevelup() {
        if (exp >= nextLevel) {
            level++;
            nextLevel = nextLevel * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(7);
            gp.gameState = gp.dialogState;
            gp.ui.currentDialog = "You are level " + level + " now!\n";
        }
    }

    private void contactMonster(int i) {
        if (i != 999) {
//            if (gp.keyHandler.enterPressed) {
//                gp.gameState = gp.dialogState;
//                gp.monster[i].speak();
//            }

            if (!invincible) {
                gp.playSE(5);
                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;

                }
                life -= damage;
                invincible = true;
            }
        }
    }

    public void interactNPC(int i) {
        if (gp.keyHandler.enterPressed) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogState;
                gp.npc[i].speak();
            }

        }

    }

    public void pickUpObject(int i) {
        if (i != 999) {


            String objName = gp.obj[i].name;
            switch (objName) {
                case "key":
                    if (inventory.size() != inventorySize) {
                        gp.playSE(1);
                        hasKey++;
                        inventory.add(gp.obj[i]);
                        gp.obj[i] = null;

                        gp.ui.showMessage("You've found a key!");
                    } else {
                        gp.ui.showMessage("Your inventory is full :(");
                    }
                    break;
                case "Iron Axe":
                    if (inventory.size() != inventorySize) {
                        gp.playSE(1);
                        inventory.add(gp.obj[i]);
                        gp.obj[i] = null;
                        gp.ui.showMessage("You've found a axe!");
                    } else {
                        gp.ui.showMessage("Your inventory is full :(");
                    }
                    break;
                case "door":
                    for (SuperObject l : gp.player.inventory) {

                        if (l.getClass().getSimpleName().equals("ObjKey") && gp.player.inventory.contains(l)) {
                            if (hasKey > 0) {
                                gp.playSE(3);
                                gp.player.inventory.remove(l);
                                gp.obj[i] = null;
                                hasKey--;
                                gp.ui.showMessage("You've opened the door");
                                break;
                            }
                        } else
                            gp.ui.showMessage("You need a key!");


                    }
                    break;
                case "boot":
                    if (inventory.size() != inventorySize) {
                        gp.playSE(2);
                        speed += 1;
                        inventory.add(gp.obj[i]);
                        gp.obj[i] = null;
                        gp.ui.showMessage("Run, baby, run");

                    } else {
                        gp.ui.showMessage("Your inventory is full :(");
                    }
                    break;
                case "chest":
                    gp.ui.gameFinish = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot();
        if (itemIndex < inventory.size()) {
            SuperObject selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == selectedItem.type_sword
                    || selectedItem.type == selectedItem.type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
            }
            if (selectedItem.type == selectedItem.type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
        }
    }

    public void draw(Graphics2D g2) {


        //correction of direction of animation for player

        BufferedImage image = null;


        //FOR ATTACKING SPRITES
        int tempX = screenX;
        int tempY = screenY;

        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    tempY = screenY - gp.tileSize;
                    showAttackArea(g2,tempX,tempY);
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
            }
            case "down" -> {
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    showAttackArea(g2,tempX,tempY);
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
            }
            case "left" -> {
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    showAttackArea(g2,tempX,tempY);
                    tempX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
            }
            case "right" -> {
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    showAttackArea(g2,tempX,tempY);
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
            }
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempX, tempY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    public void showAttackArea(Graphics2D g2,int tempX,int tempY) {
        // AttackArea
        tempX = screenX + solidArea.x;
        tempY = screenY + solidArea.y;
        switch (direction) {
            case "up" -> tempY = screenY - attackArea.height;
            case "down" -> tempY = screenY + gp.tileSize;
            case "left" -> tempX = screenX - attackArea.width;
            case "right" -> tempX = screenX + gp.tileSize;
        }
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(tempX, tempY, attackArea.width, attackArea.height);
    }

}
