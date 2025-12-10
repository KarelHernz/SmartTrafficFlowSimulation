package model;

import controller.Strategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersection {
    private Map<Road, List<TrafficLight>> roads = new HashMap<>();
    private Strategy strategy;

    public Intersection(Strategy strategy) {
        this.strategy = strategy;
    }

    public void addRoad(Road road, TrafficLight trafficLights1, TrafficLight trafficLights2) {
        var trafficLightsList = new ArrayList<TrafficLight>();
        trafficLightsList.add(trafficLights1);
        trafficLightsList.add(trafficLights2);
        roads.put(road, trafficLightsList);
    }

    public Map<Road, List<TrafficLight>> getRoads() {
        return roads;
    }

    public List<TrafficLight> getTrafficLights(Road road) {
        return roads.get(road);
    }

    public void update(double deltatime){
        //Atualiza o timer dos semáforos dos roads
        for (List<TrafficLight> trafficLights : roads.values()) {
            for (TrafficLight trafficLight : trafficLights) {
                trafficLight.updateTempo(deltatime);
            }
        }
    }
}