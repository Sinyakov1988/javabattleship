package battleship.model;

public class Cell {
    char data;
    boolean canChange;

    public void setData(char data) {
        this.data = data;
    }

    public void setCanChange(boolean canChange) {
        this.canChange = canChange;
    }
    public Cell() {
        this.data = Symbol.FOG.data;
        canChange = true;
    }

    public Cell(char data) {
        this.data = data;
        canChange = true;
    }

    public char getData() {
        return data;
    }

    public boolean isCanChange() {
        return canChange;
    }

    public boolean isBoard() {
        return getData() == Symbol.CELL.data;
    }
}
