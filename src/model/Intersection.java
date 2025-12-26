package model;

import controller.Strategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersection {
    private final Map<Road, List<TrafficLight>> roads = new HashMap<>();
    private final Map<Road, Sensor> sensor = new HashMap<>();
    private final Strategy strategy;

    public Intersection(Strategy strategy) {
        this.strategy = strategy;
    }

    public void addRoad(Road road, ArrayList<TrafficLight> trafficLightsList) {
        roads.put(road, trafficLightsList);
        sensor.put(road, new Sensor(road));
    }

    public Map<Road, List<TrafficLight>> getRoads() {
        return roads;
    }

    public List<TrafficLight> getTrafficLights(Road road) {
        return roads.get(road);
    }

    public Sensor getSensor(Road road) {
        return sensor.get(road);
    }

    public void update(double deltatime){
        //Atualiza o timer dos semáforos dos roads
        for (List<TrafficLight> trafficLights : roads.values()) {
            for (TrafficLight trafficLight : trafficLights) {
                trafficLight.updateTempo(deltatime);
            }
        }
        strategy.apply(this);
    }

    //Devolve ao estado inicial dos semáforos e dos roads
    public void reset() {
        for (List<TrafficLight> lights : roads.values()) {
            for (TrafficLight tl : lights) {
                tl.reset();
            }
        }

        for (Road road : roads.keySet()) {
            road.clearRoad();
        }
    }
}