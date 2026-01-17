package model.state;

import controller.State;
import model.TrafficLight;

public class Red implements State {
    @Override
    public void update(TrafficLight trafficLight) {
        trafficLight.resetTime();
    }
}