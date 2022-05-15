package battleship.model;

public class Game {
    private final int _FULL_SIZE = 10;
    private final int _SHIPS_CNT = 5;
    private final int _COORDINATES_CNT = 4;

    private Cell[][] fstBoard;

    public Game() {
        this.fstBoard = new Cell[_FULL_SIZE][];
        for (int i = 0; i < _FULL_SIZE; i++) {
            fstBoard[i] = new Cell[_FULL_SIZE];
            for (int j = 0; j < _FULL_SIZE; j++) {
                fstBoard[i][j] = new Cell();
            }
        }

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
}
