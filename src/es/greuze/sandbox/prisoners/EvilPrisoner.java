package es.greuze.sandbox.prisoners;

public class EvilPrisoner extends Prisoner {

    @Override
    public String giveAnswer() {
        return "BETRAY";
    }

    @Override
    public void notifyPoliceResponse(String response) {
        System.out.println("EvilPrisoner got response '" + response + "' from Police");
    }

    public static void main(String[] args) {
        EvilPrisoner prisoner = new EvilPrisoner();
        prisoner.initialize(5001);
        prisoner.startContest();
    }
}
