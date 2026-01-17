package model.state;

import controller.State;
import model.TrafficLight;

public class Green implements State {
    @Override
    public void update(TrafficLight trafficLight) {
        trafficLight.resetTime();
    }
}