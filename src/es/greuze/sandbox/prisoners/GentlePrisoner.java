package es.greuze.sandbox.prisoners;

public class GentlePrisoner extends Prisoner {

    @Override
    public String giveAnswer() {
        return "SILENT";
    }

    @Override
    public void notifyPoliceResponse(String response) {
        System.out.println("GentlePrisoner got response '" + response + "' from Police");
    }

    public static void main(String[] args) {
        GentlePrisoner prisoner = new GentlePrisoner();
        prisoner.initialize(5000);
        prisoner.startContest();
    }
}
