package model;

import controller.State;
import javafx.scene.image.ImageView;
import model.state.Green;
import model.state.Red;
import model.state.Yellow;
import java.util.HashMap;

public class TrafficLight {
    private final State initialState;
    private final HashMap<State, ImageView> lights = new HashMap<>();
    private final Green GREEN = new Green();
    private final Yellow YELLOW = new Yellow();
    private final Red RED = new Red();

    private State actualState;
    private int time;

    public TrafficLight(State state, ImageView greenView, ImageView yellowView, ImageView redView) {
        this.initialState = getState(state);
        this.actualState = this.initialState;
        this.actualState.enter(this);
        this.lights.put(GREEN, greenView);
        this.lights.put(YELLOW, yellowView);
        this.lights.put(RED, redView);
        this.time = 0;
        updateVisibilities();
    }

    public State getState() {
        return actualState;
    }

    public void setGreen(){
        setState(GREEN);
    }

    public void setYellow(){
        setState(YELLOW);
    }

    public void setRed(){
        setState(RED);
    }

    private void setState(State state) {
        actualState = state;
        actualState.enter(this);
        updateVisibilities();
        resetTime();
    }

    private State getState(State state) {
        return switch (state) {
            case Green green -> GREEN;
            case Yellow yellow -> YELLOW;
            case Red red -> RED;
            case null, default -> RED;
        };
    }

    public void resetTime() {
        this.time = 0;
    }

    public double getTime() {
        return this.time;
    }

    public void incrementTime() {
        this.time++;
    }

    //Muda a visibilidade dos imagesView dos semáforos
    public void updateVisibilities(){
        for (State st : lights.keySet()) {
            ImageView light = lights.get(st);
            if (light != null) {
                light.setVisible(st == this.actualState);
            }
        }
    }

    public void reset(){
        resetTime();
        actualState = initialState;
        actualState.enter(this);
        updateVisibilities();
    }
}