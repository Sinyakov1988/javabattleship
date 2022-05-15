package battleship.model;

public class Ship {
    private final String _NAME;
    private final int _SIZE;
    private boolean killed;

    public boolean isKilled() {
        return killed;
    }

    public void setKilled() {
        this.killed = true;
    }
//int[][] coordinate;

    public Ship(String _NAME, int _SIZE) {
        this._NAME = _NAME;
        this._SIZE = _SIZE;
        killed = false;
        //this.coordinate = coordinate;
    }

    public Ship() {
        this._NAME = "";
        this._SIZE = 0;
        //this.coordinate = coordinate;
    }

    public String get_NAME() {
        return _NAME;
    }

    public int get_SIZE() {
        return _SIZE;
    }
}
