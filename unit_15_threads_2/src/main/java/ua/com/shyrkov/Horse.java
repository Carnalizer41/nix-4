package ua.com.shyrkov;

import lombok.Getter;

import java.util.Random;
import java.util.concurrent.Phaser;

@Getter
public class Horse implements Runnable {

    private final int MIN_DISTANCE = 100;
    private final int MAX_DISTANCE = 200;
    private final int MIN_SLEEP = 400;
    private final int MAX_SLEEP = 500;
    private final String name;
    private final Random random;
    private int currentPosition;
    private Phaser phaser;
    private Race race;

    public Horse(String name) {
        this.name = name;
        this.random = new Random();
    }

    public void addHorseToRace(Race race){
        this.race = race;
        this.phaser = race.getPhaser();
        race.getHorses().add(this);
    }

    @Override
    public void run() {
        if (race == null)
            return;
        currentPosition = 0;
        System.out.println("Horse "+name+" is ready");
        phaser.arrive();
        System.out.println("Horse " + name + " is running");
        do {
            move();
            sleep();
        } while (currentPosition < race.getDistance());
        int place = race.getPlaceCounter().getAndIncrement();
        race.getResults().put(this, place);
        System.out.println("Horse " + name + " finished the race with place "+place);
        phaser.arriveAndDeregister();
    }

    private void move() {
        int distance = random.nextInt(MAX_DISTANCE - MIN_DISTANCE + 1) + MIN_DISTANCE;
        currentPosition += distance;
        System.out.println("Horse " + name + " ran " + distance + " meters");
    }

    private void sleep() {
        try {
            Thread.sleep(random.nextInt(MAX_SLEEP - MIN_SLEEP + 1) + MIN_SLEEP);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Horse))
            return false;

        Horse horse = (Horse) obj;

        return name.equals(horse.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
