package model;

import controller.State;
import javafx.scene.image.ImageView;
import model.state.Green;
import model.state.Red;
import model.state.Yellow;
import java.util.HashMap;

public class TrafficLight {
    private final State initialState;
    private State actualState;
    private double tempo;

    private final HashMap<State, ImageView> lights = new HashMap<>();
    private final Green GREEN = new Green();
    private final Yellow YELLOW = new Yellow();
    private final Red RED = new Red();

    public TrafficLight(State state, ImageView greenView, ImageView yellowView, ImageView redView) {
        this.initialState = state;
        this.actualState = state;
        this.actualState.enter(this);
        this.lights.put(GREEN, greenView);
        this.lights.put(YELLOW, yellowView);
        this.lights.put(RED, redView);
        this.tempo = 0.0;
    }

    public State getState() {
        return actualState;
    }

    public void setGreen(){
        actualState = GREEN;
        actualState.enter(this);
        updateVisibilidade();
    }

    public void setYellow(){
        actualState = YELLOW;
        actualState.enter(this);
        updateVisibilidade();
    }

    public void setRed(){
        actualState = RED;
        actualState.enter(this);
        updateVisibilidade();
    }

    public void addTempo(double deltaTime) {
        this.tempo += deltaTime;
    }

    public void resetTempo() {this.tempo = 0.0;}

    public double getTempo() {return this.tempo;}

    public void updateTempo(double deltaTime) {
        addTempo(deltaTime);
        actualState.update(this, deltaTime);

        updateVisibilidade();
    }

    public void updateVisibilidade(){
        for (State st : lights.keySet()) {
            ImageView light = lights.get(st);
            if (light != null) {
                light.setVisible(st == this.actualState);
            }
        }
    }

    public void reset(){
        tempo = 0.0;
        actualState = initialState;
        actualState.enter(this);
        updateVisibilidade();
    }
}