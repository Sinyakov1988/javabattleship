package battleship.model;

public class Cell {
    char dataOnePlayer;
    char dataTwoPlayer;
    boolean canChangeOnePlayer;
    boolean canChangeTwoPlayer;
    private Ship ship;
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public void setData(char data, int player) {
        switch (player) {
            case 1:
                this.dataOnePlayer = data;
                break;
            case 2:
                this.dataTwoPlayer = data;
        }
    }

    public void setCanChange(boolean canChange, int player) {
        switch (player) {
            case 1:
                this.canChangeOnePlayer = canChange;
                break;
            case 2:
                this.canChangeTwoPlayer = canChange;
        }
    }
    public Cell() {
        this.dataOnePlayer = Symbol.FOG.data;
        this.dataTwoPlayer = Symbol.FOG.data;
        canChangeOnePlayer = true;
        canChangeTwoPlayer = true;
    }

    public char getData(int player) {
        if (player == 1) {return dataOnePlayer;}
        return dataTwoPlayer;
    }

    public boolean isCanChange(int player) {
       if (player == 1) {
           return canChangeOnePlayer;
       }
       return canChangeTwoPlayer;
    }

    public boolean isBoard(int player) {
        return getData(player) == Symbol.CELL.data || getData(player) == Symbol.HIT.data;
    }
}
