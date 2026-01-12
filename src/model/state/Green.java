package model.state;

import controller.State;
import model.TrafficLight;

public class Green implements State {
    @Override
    public void enter(TrafficLight trafficLight) {
        trafficLight.resetTime();
    }

    @Override
    public void update(TrafficLight trafficLight) {
        trafficLight.setYellow();
    }
}