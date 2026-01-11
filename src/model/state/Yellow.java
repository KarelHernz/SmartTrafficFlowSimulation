package model.state;

import controller.State;
import model.TrafficLight;

public class Yellow implements State {
    @Override
    public void enter(TrafficLight trafficLight) {
        trafficLight.resetTime();
    }

    @Override
    public void update(TrafficLight trafficLight) {
        trafficLight.setRed();
    }
}