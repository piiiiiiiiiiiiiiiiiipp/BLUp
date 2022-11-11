package com.company;

import com.company.AttackDefenseStrategy;

public class Context {
    private AttackDefenseStrategy strategy;
    GamePanel gp;

    public Context(AttackDefenseStrategy strategy, GamePanel gp) {
        this.strategy = strategy;
    }

    public int getDefense() {
//        return gp.player.defense = gp.player.dexterity * gp.player.currentShield.defenseValue;
        return strategy.getDefense();
    }

    public int getAttack() {
        return strategy.getAttack();
//        return gp.player.attack = gp.player.strength * gp.player.currentWeapon.attackValue;
    }

}
