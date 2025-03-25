package ca.mcmaster.se2aa4.island.team18;

/**
 * Represents a decision in the form of a JSON command.
 * It stores the command as a string.
 */
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
