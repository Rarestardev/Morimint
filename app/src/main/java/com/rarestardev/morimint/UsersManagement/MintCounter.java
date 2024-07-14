package com.rarestardev.morimint.UsersManagement;

public class MintCounter {

    private int TotalBalance = 0;
    private int Mint;

    public MintCounter(int mint) {
        this.Mint = mint;
    }

    public void IncreaseBalance(int click, boolean turboMode) {
        if (turboMode) {
            TotalBalance = Mint * click * 3;
        } else {
            TotalBalance = Mint * click;
        }
    }

    public int getTotalBalance() {
        return TotalBalance;
    }
}
