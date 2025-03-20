package ca.mcmaster.se2aa4.island.team18;

public class Decision {
    private final String decision;

    public Decision(String jsonCommand) {
        this.decision = jsonCommand;
    }
    
    @Override
    public String toString() {
        return decision;
    }
}
