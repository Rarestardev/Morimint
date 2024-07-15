package com.rarestardev.morimint.UsersManagement;

import com.rarestardev.morimint.Constants.MintValues;

public class ChargeEnergyCounter {
    private int value;
    private int maxValue;
    private int minValue;
    private int step;

    public ChargeEnergyCounter(int value, int maxValue, int minValue, int step) {
        this.value = value;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.step = step;
    }

    public void increment() {
        if (value < maxValue) {
            value += step;
        }
        if (value > maxValue) {
            value = MintValues.MaxEnergy;
        }
    }

    public void decrement() {
        if (value >= 1) {
            value -= MintValues.mint;
            if (value < minValue) {
                value = minValue;
            }
        }
    }

    public boolean mintStop() {
        return value < MintValues.mint;
    }

    public void increase(int currentTime, int last_energy) {
        int added_energy = currentTime * step;

        value = Math.min(last_energy + added_energy, MintValues.MaxEnergy);
        if (value > maxValue){
            value = MintValues.MaxEnergy;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
