package co.talagro.app;

public class StateTransitionManager {

    private static State currentState= State.LOAN_APPLICATION;

    public State getCurrentState() {
        return currentState;
        //TODO; persist these values
    }

    public void updateCurrentState(State state) {
        currentState = state;
        //TODO; persist these values
    }

}
