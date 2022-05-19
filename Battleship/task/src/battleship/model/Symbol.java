package battleship.model;

enum Symbol {
    FOG('~'),
    MISS('M'),
    HIT('X'),
    CELL('O');
    char data; //pay attention on highlighted text. You aren't at a disco party; final char data;

    Symbol(char data) {
        this.data = data;
    }
}
