package domain;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private boolean alive = true;
    private int direction, score;
    private List<int[]> body = new ArrayList<>();

    public Player(int x, int y) {
        direction = -1;
        score = 0;
        body.add(new int[] {x, y});//head
        body.add(new int[] {x+1, y});//tail
    }

    public boolean isAlive() {
        return alive;
    }

    public int[] checkBlock(int block) {
        if(block == 1) { //apple
            body.add(0,getHead());
            score++;
            return new int[]{2,1};
        }
        else if(block == 2) {
            alive = false; //snake!
            System.out.println("Game over!!!");
            System.out.println("Score: " + score);
            return new int[]{3,0};
        }
        //else empty
        return new int[]{2,0};
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        if (direction == 2 && this.direction == -1) return;
        if (Math.abs(this.direction - direction) == 2) return; //don't turn in opposite direction, you will eat your neck!
        this.direction = direction;
        System.out.println(direction);
    }

    public void move(int[] newCoord) {
        body.add(0, newCoord);
    }

    public int[] getHead() {
        return body.get(0);
    }

    public int[] removeTail() {
        int[] tail = body.get(body.size()-1);
        body.remove(body.size()-1);
        return tail;
    }
}
