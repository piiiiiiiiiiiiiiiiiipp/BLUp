package com.company.strategies;

import com.company.AttackDefenseStrategy;

public class SwordShield implements AttackDefenseStrategy {
    @Override
    public int getDefense() {
        return 1;
    }

    @Override
    public int getAttack() {
        return 1;
    }
}
