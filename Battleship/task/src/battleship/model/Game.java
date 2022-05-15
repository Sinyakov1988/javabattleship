package battleship.model;

public class Game {
    private final int _FULL_SIZE = 10;
    private final int _SHIPS_CNT = 5;
    private final int _COORDINATES_CNT = 4;

    private Cell[][] fstBoard;
    private Ship[] ships;

    public Game() {
        this.fstBoard = new Cell[_FULL_SIZE][];
        for (int i = 0; i < _FULL_SIZE; i++) {
            fstBoard[i] = new Cell[_FULL_SIZE];
            for (int j = 0; j < _FULL_SIZE; j++) {
                fstBoard[i][j] = new Cell();
            }
        }
        ships = new Ship[_SHIPS_CNT];
        initShips();
    }
    public void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < _FULL_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < _FULL_SIZE; j++) {
                System.out.print(fstBoard[i][j].data + " ");
            }
            System.out.println();
        }
    }

    void initShips() {
        ships[0] = new Ship("Aircraft Carrier", 5);
        ships[1] = new Ship("Battleship", 4);
        ships[2] = new Ship("Submarine", 3);
        ships[3] = new Ship("Cruiser", 3);
        ships[4] = new Ship("Destroyer", 2);
    }
}
