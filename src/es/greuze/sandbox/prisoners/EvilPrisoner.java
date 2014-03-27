package es.greuze.sandbox.prisoners;

public class EvilPrisoner extends Prisoner {

    @Override
    public String giveAnswer() {
        return "BETRAY";
    }

    public static void main(String[] args) throws Exception {
        EvilPrisoner prisoner = new EvilPrisoner();
        prisoner.initialize(5001);

        while (prisoner.mustContinue()) {
            String response = prisoner.sendAnswer();
            System.out.println("EvilPrisoner got response '" + response + "' from Police");
        }
    }
}
