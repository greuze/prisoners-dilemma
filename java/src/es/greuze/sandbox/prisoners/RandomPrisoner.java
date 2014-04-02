package es.greuze.sandbox.prisoners;

import java.util.Random;

public class RandomPrisoner extends Prisoner {

    private Random random = new Random();

    @Override
    public String giveAnswer() {
        if (random.nextBoolean()) {
            return "BETRAY";
        } else {
            return "SILENT";
        }
    }

    @Override
    public void notifyPoliceResponse(String response) {
        System.out.println("RandomPrisoner got response '" + response + "' from Police");
    }

    public static void main(String[] args) {
        RandomPrisoner prisoner = new RandomPrisoner();
        prisoner.initialize(5002);
        prisoner.startContest();
    }
}
