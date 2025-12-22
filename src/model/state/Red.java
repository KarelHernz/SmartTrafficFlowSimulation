package model.state;

import controller.State;
import model.TrafficLight;

public class Red implements State {
    private final double TEMPO = 8.0;

    @Override
    public void enter(TrafficLight trafficLight) {
        trafficLight.setCor(TrafficLight.Cor.RED);
        trafficLight.resetTempo();
    }

    @Override
    public void update(TrafficLight trafficLight, double deltaTime) {
        trafficLight.addTempo(deltaTime);
        if (trafficLight.getTempo() >= TEMPO) {
            trafficLight.setState(new Green());
        }
    }

    @Override
    public TrafficLight.Cor getCor() {
        return TrafficLight.Cor.RED;
    }
}