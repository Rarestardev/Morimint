package com.rarestardev.morimint.ApplicationSetup;

public class ChargeEnergyCounter {
    int value;
    int maxValue;
    int minValue;
    int step;

    public ChargeEnergyCounter(int value, int maxValue, int minValue, int step) {
        this.value = value;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.step = step;
    }

    public void increment(int maxEnergy) {
        if (value < maxValue) {
            value += step;
        }
        if (value > maxValue) {
            value = maxEnergy;
        }
    }

    public void decrement(int mint) {
        if (value >= 1) {
            value -= mint;
            if (value < minValue) {
                value = minValue;
            }
        }
    }

    public boolean mintStop(int mint) {
        return value < mint;
    }

    public void increase(int currentTime, int last_energy,int maxEnergy) {
        int added_energy = currentTime * step;

        value = Math.min(last_energy + added_energy, maxEnergy);
        if (value > maxValue){
            value = maxEnergy;
        }
    }

    public int getValue() {
        return value;
    }
}
