package model;

public class PlayerWallet {
    private double money;

    public PlayerWallet(double initialMoney) {
        this.money = initialMoney;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void addMoney(double amount) {
        money += amount;
    }

    public boolean spendMoney(double amount) {
        if (money < amount) return false;
        money -= amount;
        return true;
    }
}
