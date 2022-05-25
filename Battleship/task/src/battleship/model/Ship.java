package battleship.model;

public class Ship {
    private final String _NAME; // name . uppercase using only for constants which must be static final
    private final int _SIZE; // the same
    private int hitsOnePlayer; //create a class Player and give it ships. Every player should work with their own ships fleet
    private int hitsTwoPlayer;


    public boolean isKilled(int player) {//one ship can be killed by two players or what is happening here?
        if (player == 1) { return hitsOnePlayer == 0;}
        return hitsTwoPlayer == 0;
    }//remove method parameter int

    public void takeHit(int player) {//one ship can be hit by two players or what is happening here?
        if (player == 1) { hitsOnePlayer--;}
        else { hitsTwoPlayer--;}

    }//remove method parameter int

    public Ship(String _NAME, int _SIZE) {
        this._NAME = _NAME;
        this._SIZE = _SIZE;
        hitsOnePlayer = _SIZE;
        hitsTwoPlayer = _SIZE;
    }
    public String getName() {
        return _NAME;
    }

    public int getSize() {
        return _SIZE;
    }
}
