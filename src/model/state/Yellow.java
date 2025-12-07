package model.state;

import controller.State;
import model.TrafficLight;

public class Yellow implements State {
    private final double tempo = 4.0;

    @Override
    public void enter(TrafficLight trafficLight) {
        trafficLight.setCor(TrafficLight.Cor.YELLOW);
        trafficLight.resetTempo();
    }

    @Override
    public void exit(TrafficLight trafficLight) {}

    @Override
    public void update(TrafficLight trafficLight, double deltaTime) {
        trafficLight.addTempo(deltaTime);
        if (trafficLight.getTempo() >= tempo) {
            trafficLight.setState(new Red());
        }
    }

    @Override
    public TrafficLight.Cor getCor() {
        return TrafficLight.Cor.YELLOW;
    }
}