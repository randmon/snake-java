package domain;

import java.awt.*;
import java.util.Arrays;

public class Field {
    private final Handler handler;
    private final int width, height; //width and height of each tile
    private final int tileAmount = 50;
    private final int[][] field;
    private final Player player;
    private int emptyCount;
    private String text;

    public Field(Handler handler) {
        this.handler = handler;
        width = handler.getWidth()/ tileAmount;
        height = handler.getHeight()/ tileAmount;
        this.field = new int[tileAmount][tileAmount];
        clearField();
        emptyCount = (field[1].length * field[0].length) - 3;
        player = new Player(tileAmount/2, tileAmount/2);
        spawnPlayer();
        spawnApple();
        text = "Score: 0";
    }

    public void clearField() {
        for(int i = 0; i < tileAmount; ++i) {
            for(int j = 0; j < tileAmount; ++j) {
                field[i][j] = 0;
            }
        }
    }

    public void spawnPlayer() {
        int[] playerHead = player.getHead();
        field[playerHead[1]][playerHead[0]] = 2;
        field[playerHead[1]][playerHead[0]+1] = 2;
        System.out.println("Player at: " + Arrays.toString(playerHead));
    }

    private void spawnApple() {
        int x = (int) Math.floor(Math.random() * tileAmount);
        int y = (int) Math.floor(Math.random() * tileAmount);

        if (field[y][x] == 2) {
            spawnApple();
        } else {
            field[y][x] = 1;
            System.out.println("Apple at: " + x + ", " + y);
        }
    }

    public void tick() {
        if (player.isAlive()){
            if (emptyCount > 0) {
                getInput();
                if (player.getDirection() >= 0) move();
                text = "Score: " + player.getScore();
            } else {
                System.out.println("YOU WIN!!!");
            }
        } else {
            text = "Game over! \r Score: " + player.getScore();
        }
    }

    private void move() {
        int[] newCoord = new int[2];
        newCoord[0] = player.getHead()[0];
        newCoord[1] = player.getHead()[1];
        if(player.getDirection() == 0) newCoord[0] = player.getHead()[0]-1; //left
        else if(player.getDirection() == 1) newCoord[1] = player.getHead()[1]-1; //up
        else if(player.getDirection() == 2) newCoord[0] = player.getHead()[0]+1; //right
        else if(player.getDirection() == 3) newCoord[1] = player.getHead()[1]+1; //down

        if (newCoord[0] < 0) newCoord[0] = tileAmount-1;
        if (newCoord[1] < 0) newCoord[1] = tileAmount-1;
        if (newCoord[0] > tileAmount-1) newCoord[0] = 0;
        if (newCoord[1] > tileAmount-1) newCoord[1] = 0;

        //Change field
        int[] newBlock = player.checkBlock(field[newCoord[1]][newCoord[0]]);


        int[] tail = player.removeTail();
        field[tail[1]][tail[0]] = 0;

        player.move(newCoord);
        field[newCoord[1]][newCoord[0]] = newBlock[0];

        if (newBlock[1] == 1) { //apple found
            spawnApple();
        }
    }

    private void getInput() {
        if(handler.getKeyManager().left) { player.setDirection(0); }
        else if(handler.getKeyManager().up) { player.setDirection(1); }
        else if(handler.getKeyManager().right) { player.setDirection(2);}
        else if(handler.getKeyManager().down) { player.setDirection(3);}
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,handler.getWidth(), handler.getHeight());

        emptyCount = 0;

        for(int i = 0; i < tileAmount; ++i) {
            for(int j = 0; j < tileAmount; ++j) {
                if (field[i][j] == 1) {
                    g.setColor(Color.green);
                    g.fillRect(width*j, height*i, width, height);
                }
                else if (field[i][j] == 2) {
                    g.setColor(Color.white);
                    g.fillRect(width*j, height*i, width, height);
                }

                else if (field[i][j] == 3) {
                    g.setColor(Color.red);
                    g.fillRect(width*j, height*i, width, height);
                } else {
                    emptyCount++;
                }
            }
        }

        g.setColor(Color.white);
        g.drawString(text, 20, tileAmount * height - 20);
    }

    public Player getPlayer() {
        return player;
    }
}
