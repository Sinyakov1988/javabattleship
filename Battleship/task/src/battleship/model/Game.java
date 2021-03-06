package battleship.model;

public class Game {
    private final int _FULL_SIZE = 10; //FULL_SIZE
    private final int _SHIPS_CNT = 5; //strict all or nothing so SHIPS_COUNT

    private final Cell[][] cells;
    private final Ship[] ships;

    private int healthOnePlayer; // this field should be a class Player
    private int healthTwoPlayer; // it's not a Game class property
    private int currentShipIndex; // currently, can't catch your idea. name seems strange

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    private int currentPlayer;
    GameStatus status;

    public Game() {
        healthOnePlayer = 0; //excessive line for init field.
        healthTwoPlayer = 0; //excessive line for init field.
        // you can initialize your fields right away after declaration
        this.cells = new Cell[_FULL_SIZE][]; // Cell[][] cells = new Cell[SIZE][SIZE]
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
    private void printBoard() {
        _print(false, getCurrentPlayer());
    }
    private void printOpponentFogBoard() {
        _print(true, getOpponent());
        System.out.println("---------------------");
    }
    private void _print(boolean withFog, int player) { //print. it's Java
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

    private void initShips() {//enum for ships seems better.
        ships[0] = new Ship("Aircraft Carrier", 5);
        ships[1] = new Ship("Battleship", 4);
        ships[2] = new Ship("Submarine", 3);
        ships[3] = new Ship("Cruiser", 3);
        ships[4] = new Ship("Destroyer", 2);
    }
    private void goNextShip(){//naming - just nextShip
        currentShipIndex++;
    }

    public GameStatus getStatus() {
        return status;
    }
    private void sayEnterCoordinate(){
        System.out.println("\nEnter the coordinates of the " + ships[currentShipIndex].getName()
                + " (" + ships[currentShipIndex].getSize() + " cells)");
    }
    private void sayPlaceYourShips(int player){
        System.out.println("\nPlayer " + player + ", place your ships on the game field\n");
    }
    private void sayTakeShot(){
        System.out.println("\nPlayer " + getCurrentPlayer() + ", it's your turn:\n");
    }
    private void sayPressEnter() {
        System.out.println("Press Enter and pass the move to another player\n" +
                "...");
    }
    private void saveCoordinate(String input) {
     String[] words = input.split(" ");
     int aPointX = words[0].charAt(0) - 'A';
     int aPointY = Integer.parseInt(words[0].substring(1)) - 1;

     int bPointX = words[1].charAt(0) - 'A';
     int bPointY = Integer.parseInt(words[1].substring(1)) - 1;
        checkCoordinate(aPointX, aPointY, bPointX, bPointY);
        fillBoard(aPointX, aPointY, bPointX, bPointY);
    }
    private void checkCoordinate(int aX, int aY, int bX, int bY) {
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

    private void markUsedCells(int x, int y) {
        cells[x][y].setCanChange(false, getCurrentPlayer());
        //imagine that the Cell class is smart. and you can just call .block() method and the Cell will change its state by itself
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
    private void fillBoard(int aX, int aY, int bX, int bY) {
        // how mark used cells ?
        /*
        aX = aX == 0 ? aX : --aX;
        aY = aY == 0 ? aY : --aY;
        bX = bX == _FULL_SIZE - 1 ? bX : ++bX;
        bY = bY == _FULL_SIZE - 1 ? bY : ++bY;

        for( ;aX <= bX; aX++){
            for (; aY <= bY; aY++) {
                cells[aX][aY].block()
            }
        }
        1 2 3 4 5 // if your ship has coordinates 3 2 : 3 5
        1 *       // so blocked(*) cells are all in rectangle from 2 1 to 4 6
        2   s     // just loop through mounted mini rectangle and block all cells
        3   s
        4   s
        5   s
        6     *

         */


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
    private void swapPlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        }
        else {
            currentPlayer = 1;
        }
    }
    public void processCommand(String command) {
        switch (status) {
            case WAITING_END: //one method gor each case action
                                // better to create a class for each action and remove switch
                break;
            case WAITING_INPUT_ONE_PLAYER:
                if (currentShipIndex < _SHIPS_CNT) { //avoid so deep expressions
                                                    // you are missing space for coding =)
                                                    // also it's just had to read
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
        //shift responsibility from here to a Cell class. just call cells[x][y].shoot();
        //a Cell should be smart to understand is it kill or hit or miss.
        // along with sending properly message
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
