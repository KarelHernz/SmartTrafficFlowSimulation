package model.state;

import controller.State;
import model.TrafficLight;

public class Green implements State {
    private final double TEMPO = 8.0;

    @Override
    public void enter(TrafficLight trafficLight) {
        trafficLight.setCor(TrafficLight.Cor.GREEN);
        trafficLight.resetTempo();
    }

    @Override
    public void update(TrafficLight trafficLight, double deltaTime) {
        trafficLight.addTempo(deltaTime);
        if (trafficLight.getTempo() >= TEMPO) {
            trafficLight.setState(new Yellow());
        }
    }

    @Override
    public TrafficLight.Cor getCor() {
        return TrafficLight.Cor.GREEN;
    }
}