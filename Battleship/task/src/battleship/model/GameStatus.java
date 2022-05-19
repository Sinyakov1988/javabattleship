package battleship.model;

public enum GameStatus {//remove at least the "waiting" part
    WAITING_INPUT_ONE_PLAYER,
    WAITING_INPUT_TWO_PLAYER,
    WAITING_ENTER_TWO_PLAYER,

    WAITING_ENTER,
    WAITING_SHOT,
    WAITING_END
}
