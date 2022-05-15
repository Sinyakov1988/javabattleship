package battleship.model;

enum Symbol {
    FOG('~'),
    MISS('M'),
    HIT('X'),
    CELL('O');
    char data;

    Symbol(char data) {
        this.data = data;
    }
}
