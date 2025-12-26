package controller;

import model.Intersection;
import model.Road;
import model.TrafficLight;
import model.state.Red;

import java.util.ArrayList;
import java.util.List;

public class FixedCycle implements Strategy {
    @Override
    public void apply(Intersection intersection) {
        List<Road> roadList = new ArrayList<>(intersection.getRoads().keySet());
        Road road1 = roadList.get(0);
        Road road2 = roadList.get(1);

        //Procura qual dos dois é o road que está vermelho e verde
        TrafficLight trafficLight1 = intersection.getTrafficLights(road1).getFirst();
        Road redRoad = trafficLight1.getState() instanceof Red ? road2 : road1;
        Road greenRoad = redRoad == road1 ? road2 : road1;

        //Faz a mudança dos estados dos semáforos
        for (TrafficLight trafficLight : intersection.getTrafficLights(greenRoad)) {
            trafficLight.setRed();
        }

        for (TrafficLight trafficLight : intersection.getTrafficLights(redRoad)) {
            trafficLight.setGreen();
        }
    }
}