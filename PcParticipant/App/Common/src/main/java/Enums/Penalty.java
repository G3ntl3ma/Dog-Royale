package Enums;

public enum Penalty {
    excludeFromRound("Runde aussetzen"),
    kickFromGame("Rauswurf aus Spiel");
    final String value;
    Penalty(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
