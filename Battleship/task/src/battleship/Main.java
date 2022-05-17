package battleship;

import battleship.model.Game;
import battleship.model.GameStatus;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        while (game.getStatus() != GameStatus.WAITING_END) {
            game.processCommand(scanner.nextLine());
        }
    }
}
