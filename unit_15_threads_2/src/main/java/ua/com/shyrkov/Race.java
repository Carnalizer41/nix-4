package ua.com.shyrkov;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Race {

    private final int distance;
    private final Set<Horse> horses;
    private final AtomicInteger placeCounter;
    private final Map<Horse, Integer> results;
    private final Phaser phaser;

    public Race(int distance) {
        this.distance = distance;
        this.horses = ConcurrentHashMap.newKeySet();
        this.placeCounter = new AtomicInteger();
        this.results = new ConcurrentHashMap<>();
        this.phaser = new Phaser();
    }

    public void startRace(){
        placeCounter.set(1);
        phaser.bulkRegister(horses.size());
        final int startPhase = phaser.getPhase();
        for (Horse horse : horses) {
            new Thread(horse, horse.getName()+" horse thread").start();
        }
        final int finishPhase = phaser.awaitAdvance(startPhase);
        phaser.awaitAdvance(finishPhase);
    }

    public int getHorsePlace(Horse horse){
        Integer place = results.get(horse);
        if(place == null)
            return 0;
        return place;
    }

}
