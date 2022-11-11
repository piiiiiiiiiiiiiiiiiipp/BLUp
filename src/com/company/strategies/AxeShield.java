package com.company.strategies;

import com.company.AttackDefenseStrategy;

public class AxeShield implements AttackDefenseStrategy {
    @Override
    public int getDefense() {
        return 1;
    }

    @Override
    public int getAttack() {
        return 2;
    }
}
