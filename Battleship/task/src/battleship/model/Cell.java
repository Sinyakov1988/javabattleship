package battleship.model;

public class Cell { //architecture error. Cell is using by user. so it's impossible to know who calls it
    char dataOnePlayer;
    char dataTwoPlayer;//remove
    boolean canChangeOnePlayer;
    boolean canChangeTwoPlayer;//remove
    private Ship ship;
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public void setData(char data, int player) {
        switch (player) {//you used switch in setter because of bad architecture.
                        //I remind you that a Cell is using by user. and it shouldn't know about whom exactly
            case 1:
                this.dataOnePlayer = data;
                break;
            case 2:
                this.dataTwoPlayer = data;
        }
    }

    public void setCanChange(boolean canChange, int player) {//the same as above
        switch (player) {
            case 1:
                this.canChangeOnePlayer = canChange;
                break;
            case 2:
                this.canChangeTwoPlayer = canChange;
        }
    }
    public Cell() {//constructor must be between fields and methods
        this.dataOnePlayer = Symbol.FOG.data; //overhead initialization
        this.dataTwoPlayer = Symbol.FOG.data; // you may remove constructor and init field on the place
        canChangeOnePlayer = true; //overhead initialization
        canChangeTwoPlayer = true; //overhead initialization
    }

    public char getData(int player) {
        if (player == 1) {return dataOnePlayer;} //formatting as below
        return dataTwoPlayer;
    }
    // for both methods more short expression -> return player == 1 ? one : two
    public boolean isCanChange(int player) {
       if (player == 1) {
           return canChangeOnePlayer;
       }
       return canChangeTwoPlayer;
    }

    public boolean isBoard(int player) { //what does it mean isBoard? I Don't even have a clue
        //Cell is a part of a board.
        return getData(player) == Symbol.CELL.data || getData(player) == Symbol.HIT.data; // would be better just compare enums instead of their fields
    }
}
