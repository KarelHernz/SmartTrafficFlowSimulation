package model.state;

import controller.State;
import model.TrafficLight;

public class Red implements State {
    @Override
    public void enter(TrafficLight trafficLight) {
        trafficLight.resetTime();
    }

    @Override
    public void update(TrafficLight trafficLight) {
        trafficLight.setGreen();
    }
}