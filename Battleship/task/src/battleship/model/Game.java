package battleship.model;

public class Game {
    private final int _FULL_SIZE = 10;
    private final int _SHIPS_CNT = 5;
    private final int _COORDINATES_CNT = 4;

    private final Cell[][] fstBoard;
    private final Ship[] ships;
    private int currentShipIndex;
    GameStatus status;

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
        currentShipIndex = 0;
        status = GameStatus.WAITING_INPUT;
        printBoard();
        sayEnterCoordinate();
    }
    public void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < _FULL_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < _FULL_SIZE; j++) {
                System.out.print(fstBoard[i][j].getData() + " ");
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
    void goNextShip(){
        currentShipIndex++;
    }

    public GameStatus getStatus() {
        return status;
    }
    void sayEnterCoordinate(){
        System.out.println("\nEnter the coordinates of the " + ships[currentShipIndex].getName()
                + " (" + ships[currentShipIndex].getSize() + " cells)");
    }
    void saveCoordinate(String input) {
     String[] words = input.split(" ");
     int aPointX = words[0].charAt(0) - 'A';
     int aPointY = Integer.parseInt(words[0].substring(1)) - 1;

     int bPointX = words[1].charAt(0) - 'A';
     int bPointY = Integer.parseInt(words[1].substring(1)) - 1;
     try {
         checkCoordinate(aPointX, aPointY, bPointX, bPointY);
     } catch (IllegalArgumentException e) {
         throw e;
     }
     fillBoard(aPointX, aPointY, bPointX, bPointY);
    }
    void checkCoordinate(int aX, int aY, int bX, int bY) {
        if (aX < 0 || aX >= _FULL_SIZE ||
                aY < 0 || aY >= _FULL_SIZE ||
                bX < 0 || bX >= _FULL_SIZE ||
                bY < 0 || bY >= _FULL_SIZE) {
            throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
        }
        int sizeY = Math.abs(aY - bY);
        int sizeX = Math.abs(aX - bX);
        if ((aX - bX == 0 && Math.abs(aY - bY) + 1 != ships[currentShipIndex].getSize()) ||
                (aY - bY == 0 && Math.abs(aX - bX) + 1 != ships[currentShipIndex].getSize()) ||
                (aX != bX && aY != bY)) {
            throw new IllegalArgumentException("Error! Wrong length of the Submarine! Try again:");
        }
        if (!fstBoard[aX][aY].isCanChange() || !fstBoard[bX][bY].isCanChange()) {
            throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
        }
    }

    void markUsedCells(int x, int y) {
        fstBoard[x][y].setCanChange(false);
        if (x > 0) {
            fstBoard[x - 1][y].setCanChange(false);
            if (y > 0) {
                fstBoard[x][y - 1].setCanChange(false);
                fstBoard[x - 1][y - 1].setCanChange(false);
            }
            if (y < _FULL_SIZE - 1) {
                fstBoard[x][y + 1].setCanChange(false);
                fstBoard[x - 1][y + 1].setCanChange(false);
            }
        }
        if (x < _FULL_SIZE - 1) {
            fstBoard[x + 1][y].setCanChange(false);
            if (y > 0) {
                fstBoard[x + 1][y - 1].setCanChange(false);
            }
            if (y < _FULL_SIZE - 1) {
                fstBoard[x + 1][y + 1].setCanChange(false);
            }
        }
    }
    void fillBoard(int aX, int aY, int bX, int bY) {
        int size = ships[currentShipIndex].getSize();
        while (size > 0) {
            size--;
            fstBoard[aX][aY].setData(Symbol.CELL.data);
            markUsedCells(aX, aY);
            if (aX == bX) {
                if (aY < bY) {
                    aY++;
                } else {
                    aY--;
                }
            } else {
                if (aX < bX) {
                    aX++;
                } else {
                    aX--;
                }
            }
        }
    }
    public void processCommand(String command) {
        switch (status) {
            case WAITING_END:
                break;
            case WAITING_INPUT:
                if (currentShipIndex < _SHIPS_CNT) {
                    try {
                        saveCoordinate(command);
                        goNextShip();
                        printBoard();
                        if (currentShipIndex != _SHIPS_CNT) {
                            sayEnterCoordinate();
                        } else {
                            status = GameStatus.WAITING_END;
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    status = GameStatus.WAITING_END;
                }
                break;
            default:
                status = GameStatus.WAITING_END;
        }
    }
}
