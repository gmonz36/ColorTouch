package com.example.guillaume.colortouch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import java.util.Random;

/**
 * Created by gmonz_000 on 2018-01-01.
 */

public class GameController {

    private ArrayList<Integer> colorSequence;
    private ArrayList<Integer> playerSequence;
    private Random random;

    public GameController() {
        colorSequence = new ArrayList<Integer>();
        playerSequence = new ArrayList<Integer>();
        random = new Random();
    }

    public void incSequence() {
        Integer next = random.nextInt(4);
        colorSequence.add(next);
    }

    public Iterator<Integer> getSequenceIterator() {
        return colorSequence.iterator();
    }

    public void resetPlayerSequence() {
        playerSequence = new ArrayList<Integer>();
    }

    public Boolean completeSequence() {
        return colorSequence.size()==playerSequence.size();
    }

    public Boolean selectColor(int colorNumber) {
        playerSequence.add(colorNumber);
        int i = 0;
        for(Integer color : playerSequence) {
            if(! color.equals(colorSequence.get(i)) ) {
                System.out.println("wrong sequence");
                return true;
            }
            i++;
        }
        return false;
    }
    public ArrayList<Integer> getList() {
        return colorSequence;
    }

}
