package model.state;

import controller.State;
import model.TrafficLight;

public class Green implements State {
    private final double tempo = 8.0;

    @Override
    public void enter(TrafficLight trafficLight) {
        trafficLight.setCor(trafficLight.Cor.GREEN);
        trafficLight.resetTempo();
    }

    @Override
    public void exit(TrafficLight trafficLight) {}

    @Override
    public void update(TrafficLight trafficLight, double deltaTime) {
        trafficLight.incrementTempo(deltaTime);
        if (trafficLight.getTempo() >= tempo) {
            trafficLight.setState(new Yellow());
        }
    }

    @Override
    public TrafficLight.Cor getColor() {
        return TrafficLight.Cor.GREEN;
    }
}