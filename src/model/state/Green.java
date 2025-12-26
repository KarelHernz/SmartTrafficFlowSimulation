package model.state;

import controller.State;
import model.TrafficLight;

public class Green implements State {
    @Override
    public void enter(TrafficLight trafficLight) {
        trafficLight.resetTempo();
    }

    @Override
    public void update(TrafficLight trafficLight, double deltaTime) {
        trafficLight.addTempo(deltaTime);
        if (trafficLight.getTempo() >= TEMPO) {
            trafficLight.setYellow();
        }
    }
}