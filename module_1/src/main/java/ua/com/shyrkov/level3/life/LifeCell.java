package ua.com.shyrkov.level3.life;

public class LifeCell {

    private boolean isAlive;

    public LifeCell(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public String toString() {
        if(isAlive)
            return "1";
        else
            return "0";

    }
}
