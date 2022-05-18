package battleship.model;

public class Ship {
    private final String _NAME;
    private final int _SIZE;
    private int hitsOnePlayer;
    private int hitsTwoPlayer;


    public boolean isKilled(int player) {
        if (player == 1) { return hitsOnePlayer == 0;}
        return hitsTwoPlayer == 0;
    }
    public void takeHit(int player) {
        if (player == 1) { hitsOnePlayer--;}
        else { hitsTwoPlayer--;}

    }

    public Ship(String _NAME, int _SIZE) {
        this._NAME = _NAME;
        this._SIZE = _SIZE;
        hitsOnePlayer = _SIZE;
        hitsTwoPlayer = _SIZE;
    }

    public Ship() {
        this._NAME = "";
        this._SIZE = 0;
    }

    public String getName() {
        return _NAME;
    }

    public int getSize() {
        return _SIZE;
    }
}
