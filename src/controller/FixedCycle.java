package controller;

import model.Intersection;
import model.Road;
import model.TrafficLight;
import model.state.Green;
import model.state.Red;

import java.util.ArrayList;
import java.util.List;

public class FixedCycle implements Strategy {
    private int currentIndex = 0;

    @Override
    public void apply(Intersection intersection) {
        List<Road> roadList = new ArrayList<>(intersection.getRoads().keySet());
        Road currentRoad = roadList.get(currentIndex);
        TrafficLight trafficLightList = intersection.getTrafficLights(currentRoad).getFirst();

        if (trafficLightList.getCor() == TrafficLight.Cor.GREEN || trafficLightList.getCor() == TrafficLight.Cor.YELLOW)
            return;

        currentIndex++;
        if (currentIndex >= roadList.size()) {
            currentIndex = 0;
        }
        currentRoad = roadList.get(currentIndex);

        //define os estados dos roads no momento de iniciar o ciclo
        for (Road road : roadList) {
            List<TrafficLight> lights = intersection.getTrafficLights(road);
            for (TrafficLight trafficLight : lights) {
                trafficLight.setState(road == currentRoad ? new Green() : new Red());
            }
        }
    }
}