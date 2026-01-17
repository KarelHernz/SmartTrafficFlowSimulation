package model;

import controller.State;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.state.Green;
import model.state.Red;
import model.state.Yellow;
import java.util.HashMap;
import java.util.Objects;

public class TrafficLight {
    private final HashMap<State, Image> lights = new HashMap<>();
    private final ImageView light;
    private final Red RED = new Red();
    private final Yellow YELLOW = new Yellow();
    private final Green GREEN = new Green();

    private final State initialState;
    private State actualState;
    private int time;

    public TrafficLight(State state, ImageView trafficLight) {
        //Devolve um estado em base à instância do estado entrante
        this.initialState = (state instanceof Green) ? GREEN :
                            (state instanceof Yellow) ? YELLOW : RED;

        this.lights.put(RED, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/sinal_vermelho.png"))));
        this.lights.put(YELLOW, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/sinal_amarelo.png"))));
        this.lights.put(GREEN, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/sinal_verde.png"))));
        this.light = trafficLight;
        this.time = 0;

        this.actualState = this.initialState;
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
        updateVisibilities();
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
        actualState.update(this);
        light.setImage(this.lights.get(actualState));
    }

    public void reset(){
        actualState = initialState;
        updateVisibilities();
    }
}