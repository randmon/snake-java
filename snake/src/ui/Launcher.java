package ui;

import domain.Game;

/**JFrame based snake game
 * @author Cristina Marques
 * @date 13/01/2021
 * @version 0.1
 */

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Snake", 900,900);
        game.start();
    }
}
