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
    private final Red RED = new Red();
    private final Yellow YELLOW = new Yellow();
    private final Green GREEN = new Green();

    private State actualState;
    private int time;

    public TrafficLight(State state, ImageView greenView, ImageView yellowView, ImageView redView) {
        this.initialState = getState(state);
        this.actualState = this.initialState;
        this.actualState.enter(this);
        this.lights.put(RED, redView);
        this.lights.put(YELLOW, yellowView);
        this.lights.put(GREEN, greenView);
        this.time = 0;
        updateVisibilities();
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

    public boolean isGreen(){
        return GREEN == actualState;
    }

    public boolean isYellow(){
        return YELLOW == actualState;
    }

    public boolean isRed(){
        return RED == actualState;
    }

    private void setState(State state) {
        actualState = state;
        actualState.enter(this);
        updateVisibilities();
        resetTime();
    }

    //Devolve um estado em base à instância do estado entrante
    private State getState(State state) {
        return (state instanceof Green) ? GREEN :
               (state instanceof Yellow) ? YELLOW : RED;
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