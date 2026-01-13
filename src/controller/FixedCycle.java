package controller;

import model.Intersection;
import model.Road;
import model.TrafficLight;
import java.util.ArrayList;
import java.util.List;

public class FixedCycle implements Strategy {
    private final int GREEN_DURATION = 20;

    @Override
    public void update(Intersection intersection) {
        List<Road> roadList = new ArrayList<>(intersection.getRoads());

        Road road1 = roadList.get(0);
        Road road2 = roadList.get(1);
        TrafficLight trafficLight1 = road1.getTrafficLights().getFirst();
        TrafficLight trafficLight2 = road2.getTrafficLights().getFirst();

        //Muda para amarelo se o estado for verde e tiver atingido o tempo máximo que este fica neste estado
        if (trafficLight1.isGreen() && trafficLight1.getTime() >= GREEN_DURATION) {
            switchState(road1, "YELLOW");
        }
        //Muda para amarelo se o estado for verde e tiver atingido o tempo máximo que este fica neste estado
        else if (trafficLight1.isYellow() && trafficLight1.getTime() >= YELLOW_DURATION) {
            switchState(road1, "RED");
            switchState(road2, "GREEN");
        }
        else if (trafficLight1.isRed()) {
            //Mete os semáforos dos segundo road no estado amarelo
            if (trafficLight2.isGreen() && trafficLight2.getTime() >= GREEN_DURATION) {
                switchState(road2, "YELLOW");
            }
            //
            else if (trafficLight2.isYellow() && trafficLight2.getTime() >= YELLOW_DURATION) {
                switchState(road2, "RED");
                switchState(road1, "GREEN");
            }
        }
    }

    //Método para mudar o estado os semáforos de uma road
    private void switchState(Road road, String color) {
        List<TrafficLight> lights = road.getTrafficLights();

        for (TrafficLight trafficLight : lights) {
            switch (color) {
                case "RED" -> trafficLight.setRed();
                case "YELLOW" -> trafficLight.setYellow();
                case "GREEN" -> trafficLight.setGreen();
            }
        }
    }
}