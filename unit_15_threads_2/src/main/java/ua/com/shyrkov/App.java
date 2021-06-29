package ua.com.shyrkov;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Race race = new Race(1000);
        Horse[] horses = {
                new Horse("Number 1"),
                new Horse("Number 2"),
                new Horse("Number 3"),
                new Horse("Number 4"),
                new Horse("Number 5")};

        for (Horse horse : horses) {
            horse.addHorseToRace(race);
        }
        final int horsesAmount = horses.length;
        System.out.println("Choose horse to bet on");
        for (int i = 0; i < horsesAmount; i++) {
            System.out.println((i+1)+": "+horses[i].getName());
        }
        Scanner scanner = new Scanner(System.in);
        int betNumber;
        while ((betNumber = scanner.nextInt())>horsesAmount || betNumber<=0){
            System.out.println("There is no horse with such number. Try again!");
        }
        final Horse horse = horses[betNumber-1];
        System.out.println("You have chosen "+horse);
        race.startRace();
        final int horsePlace = race.getHorsePlace(horse);
        System.out.println("Your horse is finished on "+horsePlace+" place");
    }
}
