package model;

import controller.Strategy;
import java.util.ArrayList;
import java.util.List;

public class Intersection {
    private final List<Road> roadList;
    private Strategy strategy;

    public Intersection(Strategy strategy) {
        this.strategy = strategy;
        roadList = new ArrayList<>();
    }

    public void addRoad(Road road) {
        this.roadList.add(road);
    }

    public List<Road> getRoads() {
        return roadList;
    }

    public void changeStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    //Atualiza o timer dos semáforos dos roads
    public void update(){
        for(Road road: roadList){
            for (TrafficLight trafficLight : road.getTrafficLights()) {
                trafficLight.incrementTime();
            }
        }
        strategy.update(this);
    }

    //Devolve ao estado inicial dos semáforos e dos roads
    public void reset() {
        for(Road road: roadList){
            for (TrafficLight lights: road.getTrafficLights()) {
                lights.reset();
            }
        }

        for (Road road : roadList) {
            road.clear();
        }
    }
}