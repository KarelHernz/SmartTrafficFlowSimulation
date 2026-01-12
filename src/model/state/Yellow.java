package model.state;

import controller.State;
import model.TrafficLight;

public class Yellow implements State {
    @Override
    public void enter(TrafficLight trafficLight) {
        trafficLight.resetTempo();
    }

    @Override
    public void update(TrafficLight trafficLight, double deltaTime) {
        trafficLight.addTempo(deltaTime);
        if (trafficLight.getTempo() >= TEMPO/2) {
            trafficLight.setRed();
        }
    }
}