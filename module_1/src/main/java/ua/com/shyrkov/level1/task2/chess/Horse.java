package ua.com.shyrkov.level1.task2.chess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Horse {

    private int posX;
    private int posY;

    public Horse(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public boolean canMoveToPosition(int targetPosX, int targetPosY){
        int subtractedX = posX-targetPosX;
        int subtractedY = posY-targetPosY;
        return Math.abs(subtractedX) + Math.abs(subtractedY) == 3;
    }
}
