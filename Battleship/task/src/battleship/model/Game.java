package battleship.model;

public class Game {
    private final int _FULL_SIZE = 10;
    private final int _SHIPS_CNT = 5;

    private final Cell[][] cells;
    private final Ship[] ships;

    private int healthOnePlayer;
    private int healthTwoPlayer;
    private int currentShipIndex;

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    private int currentPlayer;
    GameStatus status;

    public Game() {
        healthOnePlayer = 0;
        healthTwoPlayer = 0;

        this.cells = new Cell[_FULL_SIZE][];
        for (int i = 0; i < _FULL_SIZE; i++) {
            cells[i] = new Cell[_FULL_SIZE];
            for (int j = 0; j < _FULL_SIZE; j++) {
                cells[i][j] = new Cell();
            }
        }
        ships = new Ship[_SHIPS_CNT];
        initShips();
        currentShipIndex = 0;
        status = GameStatus.WAITING_INPUT_ONE_PLAYER;
        currentPlayer = 1;
        sayPlaceYourShips(currentPlayer);
        printBoard();
        sayEnterCoordinate();

    }
    private int getOpponent() {
        if (getCurrentPlayer() == 1) {
            return 2;
        }
        return 1;
    }
    public void printBoard() {
        _print(false, getCurrentPlayer());
    }
    public void printOpponentFogBoard() {
        _print(true, getOpponent());
        System.out.println("---------------------");
    }
    public void _print(boolean withFog, int player) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < _FULL_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < _FULL_SIZE; j++) {
                char buffer = cells[i][j].getData(player);
                System.out.print(buffer == Symbol.CELL.data && withFog ? Symbol.FOG.data + " " : buffer + " ");
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
    void sayPlaceYourShips(int player){
        System.out.println("\nPlayer " + player + ", place your ships on the game field\n");
    }
    void sayTakeShot(){
        System.out.println("\nPlayer " + getCurrentPlayer() + ", it's your turn:\n");
    }
    void sayPressEnter() {
        System.out.println("Press Enter and pass the move to another player\n" +
                "...");
    }
    void saveCoordinate(String input) {
     String[] words = input.split(" ");
     int aPointX = words[0].charAt(0) - 'A';
     int aPointY = Integer.parseInt(words[0].substring(1)) - 1;

     int bPointX = words[1].charAt(0) - 'A';
     int bPointY = Integer.parseInt(words[1].substring(1)) - 1;
        checkCoordinate(aPointX, aPointY, bPointX, bPointY);
        fillBoard(aPointX, aPointY, bPointX, bPointY);
    }
    void checkCoordinate(int aX, int aY, int bX, int bY) {
        if (aX < 0 || aX >= _FULL_SIZE ||
                aY < 0 || aY >= _FULL_SIZE ||
                bX < 0 || bX >= _FULL_SIZE ||
                bY < 0 || bY >= _FULL_SIZE) {
            throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
        }

        if ((aX - bX == 0 && Math.abs(aY - bY) + 1 != ships[currentShipIndex].getSize()) ||
                (aY - bY == 0 && Math.abs(aX - bX) + 1 != ships[currentShipIndex].getSize()) ||
                (aX != bX && aY != bY)) {
            throw new IllegalArgumentException("Error! Wrong length of the Submarine! Try again:");
        }
        if (!cells[aX][aY].isCanChange(currentPlayer) || !cells[bX][bY].isCanChange(getCurrentPlayer())) {
            throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
        }
    }

    private int getHealth(int player){
        if (player == 1) { return healthOnePlayer;}
        return healthTwoPlayer;
    }

    private void subtractHealth(int player){
        if (player == 1) {
            healthOnePlayer--;
        } else {
            healthTwoPlayer--;
        }
    }
    private void addHealth(){
        if (getCurrentPlayer() == 1) {
            healthOnePlayer++;
        } else {
            healthTwoPlayer++;
        }
    }

    private boolean isLastShip(int player){
        return getHealth(player) == 0;
    }

    private void takeHit(int x, int y, int player){

        if (cells[x][y].getData(player) == Symbol.CELL.data) {
            subtractHealth(player);
            cells[x][y].getShip().takeHit(player);
        }
    }

    void markUsedCells(int x, int y) {
        cells[x][y].setCanChange(false, getCurrentPlayer());
        if (x > 0) {
            cells[x - 1][y].setCanChange(false, getCurrentPlayer());
            if (y > 0) {
                cells[x][y - 1].setCanChange(false, getCurrentPlayer());
                cells[x - 1][y - 1].setCanChange(false, getCurrentPlayer());
            }
            if (y < _FULL_SIZE - 1) {
                cells[x][y + 1].setCanChange(false, getCurrentPlayer());
                cells[x - 1][y + 1].setCanChange(false, getCurrentPlayer());
            }
        }
        if (x < _FULL_SIZE - 1) {
            cells[x + 1][y].setCanChange(false, getCurrentPlayer());
            if (y > 0) {
                cells[x + 1][y - 1].setCanChange(false, getCurrentPlayer());
            }
            if (y < _FULL_SIZE - 1) {
                cells[x + 1][y + 1].setCanChange(false, getCurrentPlayer());
            }
        }
    }
    void fillBoard(int aX, int aY, int bX, int bY) {
        int size = ships[currentShipIndex].getSize();
        while (size > 0) {
            size--;
            cells[aX][aY].setData(Symbol.CELL.data, getCurrentPlayer());
            cells[aX][aY].setShip(ships[currentShipIndex]);
            addHealth();
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
    void swapPlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        }
        else {
            currentPlayer = 1;
        }
    }
    public void processCommand(String command) {
        switch (status) {
            case WAITING_END:
                break;
            case WAITING_INPUT_ONE_PLAYER:
                if (currentShipIndex < _SHIPS_CNT) {
                    try {
                        saveCoordinate(command);
                        goNextShip();
                        printBoard();
                        if (currentShipIndex != _SHIPS_CNT) {
                            sayEnterCoordinate();
                        } else {

                            currentShipIndex = 0;
                            sayPressEnter();
                            status = GameStatus.WAITING_ENTER_TWO_PLAYER;
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    status = GameStatus.WAITING_END;
                }
                break;
            case WAITING_INPUT_TWO_PLAYER:
                if (currentShipIndex < _SHIPS_CNT) {
                    try {
                        saveCoordinate(command);
                        goNextShip();
                        printBoard();
                        if (currentShipIndex != _SHIPS_CNT) {
                            sayEnterCoordinate();
                        } else {
                            sayPressEnter();
                            status = GameStatus.WAITING_ENTER;
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    status = GameStatus.WAITING_END;
                }
                break;
            case WAITING_ENTER_TWO_PLAYER:
                swapPlayer();
                sayPlaceYourShips(getCurrentPlayer());
                printBoard();
                sayEnterCoordinate();
                status = GameStatus.WAITING_INPUT_TWO_PLAYER;
                break;
            case WAITING_SHOT:
                try {
                    processShot(command);
                    if (isLastShip(getOpponent())) {
                        status = GameStatus.WAITING_END;
                    } else {
                        status = GameStatus.WAITING_ENTER;
                        sayPressEnter();
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case WAITING_ENTER:
                swapPlayer();
                printOpponentFogBoard();
                printBoard();
                sayTakeShot();
                status = GameStatus.WAITING_SHOT;
                break;
            default:
                status = GameStatus.WAITING_END;
        }
    }

    private void processShot(String command) {
        int aPointX = command.charAt(0) - 'A';
        int aPointY = Integer.parseInt(command.substring(1)) - 1;

        if (aPointX < 0 || aPointX >= _FULL_SIZE ||
                aPointY < 0 || aPointY >= _FULL_SIZE) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
        if (cells[aPointX][aPointY].isBoard(getOpponent())) {
            takeHit(aPointX, aPointY, getOpponent());
            cells[aPointX][aPointY].setData(Symbol.HIT.data, getOpponent());
            if (cells[aPointX][aPointY].getShip().isKilled(getOpponent())) {
                if (!isLastShip(getOpponent())) {
                    System.out.println("\nYou sank a ship!");
                } else {
                    System.out.println("\nYou sank the last ship. You won. Congratulations!");
                }
            } else {
                System.out.println("\nYou hit a ship!");
            }

        } else {
            cells[aPointX][aPointY].setData(Symbol.MISS.data, getOpponent());
            System.out.println("\nYou missed!");
        }
    }
}
