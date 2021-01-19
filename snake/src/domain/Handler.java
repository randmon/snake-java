package domain;

public class Handler {

    private Game game;
    private Field field;

    public Handler(Game game) {
        this.game = game;
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public int getWidth() {
        return game.getWidth();
    }
    public int getHeight() {
        return game.getHeight();
    }

    public Game getGame() {
        return game;
    }

    public Field getField() {
        return field;
    }

    public Player getPlayer() {
        return field.getPlayer();
    }
}
