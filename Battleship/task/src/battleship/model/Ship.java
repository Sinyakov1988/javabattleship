package battleship.model;

public class Ship {
    private final String _NAME;
    private final int _SIZE;
    private int hits;


    public boolean isKilled() {
        return hits == 0;
    }
    public void takeHit() {
        hits--;
    }

    public Ship(String _NAME, int _SIZE) {
        this._NAME = _NAME;
        this._SIZE = _SIZE;
        hits = _SIZE;
        //this.coordinate = coordinate;
    }

    public Ship() {
        this._NAME = "";
        this._SIZE = 0;
        //this.coordinate = coordinate;
    }

    public String getName() {
        return _NAME;
    }

    public int getSize() {
        return _SIZE;
    }
}
